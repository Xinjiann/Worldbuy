package com.mall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mall.product.service.CategoryBrandRelationService;
import com.mall.product.vo.Catalog3Vo;
import com.mall.product.vo.Catelog2Vo;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;

import com.mall.product.dao.CategoryDao;
import com.mall.product.entity.CategoryEntity;
import com.mall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        return baseMapper.selectList(null);
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {

        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCateLogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        paths = findParentPath(catelogId, paths);
        // The collection is in order, the front end is displayed in reverse order, so use the collection tool class to reverse it
        Collections.reverse(paths);
        return paths.toArray(new Long[paths.size()]);
    }

    /**
     * Cascade update all data [The default partition name is the prefix of the cache] SpringCache: no lock
     *
     * @CacheEvict: cache invalidation mode --- as soon as the page is modified, then the two caches are cleared
     * key = "'getLevel1Categorys'" : remember to add single quotes [subparse string]
     * @Caching: perform multiple caching operations at the same time
     * @CacheEvict(value = {"category"}, allEntries = true) : delete all data in this partition
     * @CachePut: This query operation is written to the cache
     */
//	@Caching(evict = {
//			@CacheEvict(value = {"category"}, key = "'getLevel1Categorys'"),
//			@CacheEvict(value = {"category"}, key = "'getCatelogJson'")
//	})
    @CacheEvict(value = {"category"}, allEntries = true)
//	@CachePut
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    /**
     * @Cacheable: The result of the current method needs to be cached and specify the cache name
     * The cached value is serialized using jdk by default
     * Default ttl time -1
     * key: By default, the expression string will be parsed with ''
     * <p>
     * customize:
     * 1. Specify the key used to generate the cache
     * 2. Specify the cache data survival time [modified in the configuration file]
     * 3. Save the data in json format
     * <p>
     * sync = true: --- enable sync lock
     */
    @Cacheable(value = {"category"}, key = "#root.method.name", sync = true)
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("cat_level", 1));
        // Test whether null values can be cached
        // return null;
    }

    /**
     * Distributed lock
     *
     * @return
     */
    public Map<String, List<Catelog2Vo>> getCatelogJsonFromDBWithRedisLock() {
        // 1. Occupy a distributed lock, set this lock to be automatically deleted for 10 seconds [atomic operation]
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 30, TimeUnit.SECONDS);

        if (lock) {
            // 2. Set the expiration time and lock to successfully obtain the data release lock [The Lua script must delete the lock under the distribution, otherwise the data will not be returned due to business processing time, network delay, etc. The lock expires or expires during the return process. Delete someone else's lock]
            Map<String, List<Catelog2Vo>> data;
            try {
                data = getDataFromDB();
            } finally {
                // stringRedisTemplate.delete("lock");
                String lockValue = stringRedisTemplate.opsForValue().get("lock");

                // Delete must also be an atomic operation Lua script operation Returns 1 if successful, otherwise returns 0
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                // atomic delete lock
                stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList("lock"), uuid);
            }
            return data;
        } else {
            // retry locking
            try {
                // board two hundred milliseconds
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getCatelogJsonFromDBWithRedisLock();
        }
    }

    @Cacheable(value = "category", key = "#root.methodName")
    @Override
    public Map<String, List<Catelog2Vo>> getCatelogJson() {
        List<CategoryEntity> entityList = baseMapper.selectList(null);
        // Query all primary categories
        List<CategoryEntity> level1 = getCategoryEntities(entityList, 0L);
        Map<String, List<Catelog2Vo>> parent_cid = level1.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // Get each primary category and query their secondary category
            List<CategoryEntity> entities = getCategoryEntities(entityList, v.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (entities != null) {
                catelog2Vos = entities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), l2.getName(), l2.getCatId().toString(), null);
                    // Find the third-level classification of the current second-level classification
                    List<CategoryEntity> level3 = getCategoryEntities(entityList, l2.getCatId());
                    // In the case of three-level classification with data
                    if (level3 != null) {
                        List<Catalog3Vo> catalog3Vos = level3.stream().map(l3 -> new Catalog3Vo(l3.getCatId().toString(), l3.getName(), l2.getCatId().toString())).collect( Collectors.toList());
                        catelog2Vo.setCatalog3List(catalog3Vos);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        return parent_cid;
    }

    /**
     * redis no cache query database
     */
    private Map<String, List<Catelog2Vo>> getDataFromDB() {
        String catelogJSON = stringRedisTemplate.opsForValue().get("catelogJSON");
        if (!StringUtils.isEmpty(catelogJSON)) {
            return JSON.parseObject(catelogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });
        }
        // Optimization: make the query one time
        List<CategoryEntity> entityList = baseMapper.selectList(null);

        // Query all primary categories
        List<CategoryEntity> level1 = getCategoryEntities(entityList, 0L);
        Map<String, List<Catelog2Vo>> parent_cid = level1.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // Get each primary category and query their secondary category
            List<CategoryEntity> entities = getCategoryEntities(entityList, v.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (entities != null) {
                catelog2Vos = entities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), l2.getName(), l2.getCatId().toString(), null);
                    // Find the third-level classification of the current second-level classification
                    List<CategoryEntity> level3 = getCategoryEntities(entityList, l2.getCatId());
                    // In the case of three-level classification with data
                    if (level3 != null) {
                        List<Catalog3Vo> catalog3Vos = level3.stream().map(l3 -> new Catalog3Vo(l3.getCatId().toString(), l3.getName(), l2.getCatId().toString())).collect( Collectors.toList());
                        catelog2Vo.setCatalog3List(catalog3Vos);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        // Optimization: query the database and put it into the cache before the lock is over
        stringRedisTemplate.opsForValue().set("catelogJSON", JSON.toJSONString(parent_cid), 1, TimeUnit.DAYS);
        return parent_cid;
    }

    /**
     * redisson microservice cluster lock
     * How the data in the cache is consistent with the database
     */
    public Map<String, List<Catelog2Vo>> getCatelogJsonFromDBWithRedissonLock() {

        // As long as the lock name is the same, the lock is the same
        // Regarding the granularity of the lock, the specific cache is a certain data For example: product-11-lock
        RLock lock = redissonClient.getLock("CatelogJson-lock");
        lock.lock();

        Map<String, List<Catelog2Vo>> data;
        try {
            data = getDataFromDB();
        } finally {
            lock.unlock();
        }
        return data;
    }

    /**
     * redis has no data query DB [local lock solution]
     */
    public Map<String, List<Catelog2Vo>> getCatelogJsonFromDBWithLocalLock() {
        synchronized (this) {
            // 双重检查 是否有缓存
            return getDataFromDB();
        }
    }

    /**
     * All strings stored in the cache are JSON
     * TODO may generate off-heap memory overflow
     */
    public Map<String, List<Catelog2Vo>> getCatelogJson2() {

        /**
         * 1. Empty result cache to solve cache penetration
         * 2. Set the expiration time to solve the cache avalanche
         * 3. Lock to solve cache breakdown
         */
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        Map<String, List<Catelog2Vo>> catelogJson;
        // 缓存中没有数据
        String catelogJSON = operations.get("catelogJSON");
        if (StringUtils.isEmpty(catelogJSON)) {
            catelogJson = getCatelogJsonFromDBWithRedisLock();
        } else {
            catelogJson = JSON.parseObject(catelogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
            });
        }
        return catelogJson;
    }

    /**
     * All CategoryEntity of the first query and then go here to find according to parent_cid
     */
    private List<CategoryEntity> getCategoryEntities(List<CategoryEntity> entityList, Long parent_cid) {

        return entityList.stream().filter(item -> item.getParentCid() == parent_cid).collect(Collectors.toList());
    }

    /**
     * Collect all nodes recursively
     */
    private List<Long> findParentPath(Long catlogId, List<Long> paths) {
        // 1、收集当前节点id
        paths.add(catlogId);
        CategoryEntity byId = this.getById(catlogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }
}
