package com.mall.product.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.mall.common.constant.ProductConstant;
import com.mall.common.to.SkuReductionTO;
import com.mall.common.to.SpuBoundTO;
import com.mall.common.to.es.SkuEsModel;
import com.mall.common.to.es.SkuHasStockVo;
import com.mall.common.utils.R;
import com.mall.product.entity.AttrEntity;
import com.mall.product.entity.BrandEntity;
import com.mall.product.entity.CategoryEntity;
import com.mall.product.entity.ProductAttrValueEntity;
import com.mall.product.entity.SkuImagesEntity;
import com.mall.product.entity.SkuInfoEntity;
import com.mall.product.entity.SkuSaleAttrValueEntity;
import com.mall.product.entity.SpuInfoDescEntity;
import com.mall.product.feign.CouponFeignService;
//import com.mall.product.feign.SearchFeignService;
import com.mall.product.feign.SearchFeignService;
import com.mall.product.feign.WareFeignService;
import com.mall.product.service.AttrService;
import com.mall.product.service.BrandService;
import com.mall.product.service.CategoryService;
import com.mall.product.service.ProductAttrValueService;
import com.mall.product.service.SkuImagesService;
import com.mall.product.service.SkuInfoService;
import com.mall.product.service.SkuSaleAttrValueService;
import com.mall.product.service.SpuImagesService;
import com.mall.product.service.SpuInfoDescService;
import com.mall.product.vo.Attr;
import com.mall.product.vo.BaseAttrs;
import com.mall.product.vo.Bounds;
import com.mall.product.vo.Images;
import com.mall.product.vo.Skus;
import com.mall.product.vo.SpuSaveVo;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.Query;

import com.mall.product.dao.SpuInfoDao;
import com.mall.product.entity.SpuInfoEntity;
import com.mall.product.service.SpuInfoService;
import org.springframework.transaction.annotation.Transactional;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService attrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WareFeignService wareFeignService;

    /**
     * feign coupon
     */
    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
            new Query<SpuInfoEntity>().getPage(params),
            new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * save all data [33kb]
     */
    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {

        // 1.Save basic information of spu pms_sku_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        BeanUtils.copyProperties(vo, spuInfoEntity);
        this.saveBatchSpuInfo(spuInfoEntity);
        // 2.Save the representation picture of spu pms_spu_info_desc
        List<String> decript = vo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        // String join to separate them with commas
        spuInfoDescEntity.setDecript(String.join(",", decript));
        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);
        // 3.Save spu image set pms_sku_images

        // first get all imgs
        List<String> images = vo.getImages();
        // When saving the picture and save this is the picture of that spu
        spuImagesService.saveImages(spuInfoEntity.getId() ,images);
        // 4.Save the specification attribute of the spu pms_product_attr_value
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(attr -> {
            ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
            valueEntity.setAttrId(attr.getAttrId());
            // Maybe the page does not use the attribute name passed in. Find all attributes according to the attribute id and assign a value to the name
            AttrEntity attrEntity = attrService.getById(attr.getAttrId());
            valueEntity.setAttrName(attrEntity.getAttrName());
            valueEntity.setAttrValue(attr.getAttrValues());
            valueEntity.setQuickShow(attr.getShowDesc());
            valueEntity.setSpuId(spuInfoEntity.getId());

            return valueEntity;
        }).collect(Collectors.toList());
        attrValueService.saveProductAttr(collect);
        // 5.Save all sku information corresponding to the current spu
        Bounds bounds = vo.getBounds();
        SpuBoundTO spuBoundTO = new SpuBoundTO();
        BeanUtils.copyProperties(bounds, spuBoundTO);
        spuBoundTO.setSpuId(spuInfoEntity.getId());
        R r = couponFeignService.saveSpuBounds(spuBoundTO);
        if(r.getCode() != 0){
            log.error("Failed to save spu points information remotely");
        }
        // 1).spu's integral information sms_spu_bounds
        List<Skus> skus = vo.getSkus();
        if(skus != null && skus.size() > 0){
            // Find default images ahead of time
            skus.forEach(item -> {
                String dufaultImg = "";
                for (Images img : item.getImages()) {
                    if(img.getDefaultImg() == 1){
                        dufaultImg = img.getImgUrl();
                    }
                }
                // 2).Saving of basic information pms_sku_info
                // skuName 、price、skuTitle、skuSubtitle 这些属性需要手动保存
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                // Set the brand id of the spu
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(dufaultImg);
                skuInfoEntity.setSaleCount((long) (Math.random()*2888));
                skuInfoService.saveSkuInfo(skuInfoEntity);

                // 3). Save the image information of sku pms_sku_images
                // After the sku is saved, the self-incrementing primary key will come out and collect all the pictures
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity ->
                    // true for save, false for break
                    !StringUtils.isEmpty(entity.getImgUrl())
                ).collect(Collectors.toList());
                skuImagesService.saveBatch(imagesEntities);

                // 4).sku's sales attributes  pms_sku_sale_attr_value
                List<Attr> attr = item.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(a -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                // 5.) Sku's discounts, full discounts, membership prices and other information [cross-library]
                SkuReductionTO skuReductionTO = new SkuReductionTO();
                BeanUtils.copyProperties(item, skuReductionTO);
                skuReductionTO.setSkuId(skuId);
                if(skuReductionTO.getFullCount() > 0 || (skuReductionTO.getFullPrice().compareTo(new BigDecimal("0")) > 0)){
                    R r1 = couponFeignService.saveSkuReduction(skuReductionTO);
                    if(r1.getCode() != 0){
                        log.error("Failed to save sku discount information remotely");
                    }
                }
            });
        }
    }

    @Override
    public void saveBatchSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

    /**
     * spu roughly search
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();

        // Overlay fuzzy query according to the conditions brought by spu management
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and(w -> w.eq("id", key).or().like("spu_name",key));
        }

        String status = (String) params.get("status");
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("publish_status", status);
        }

        String brandId = (String) params.get("brandId");
        if(!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id", brandId);
        }

        String catelogId = (String) params.get("catelogId");
        if(!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id", catelogId);
        }

        IPage<SpuInfoEntity> page = this.page(
            new Query<SpuInfoEntity>().getPage(params),
            wrapper
        );
        return new PageUtils(page);
    }

    /**
     * @param spuId
     */
    @Override
    public void up(Long spuId) {
        // 1 Assemble data Find out all the sku information corresponding to the current spuId
        List<SkuInfoEntity> skus = skuInfoService.getSkusBySpuId(spuId);
        // Check if these sku are in stock
        List<Long> skuids = skus.stream().map(sku -> sku.getSkuId()).collect(Collectors.toList());
        // 2 Encapsulate the information of each sku

        // 3. Query all the specification properties of the current sku that can be used to retrieve
        // Get the ids of all spu products and then query which of these ids can be retrieved [currently 4, 5, 6, 11 in the database cannot be retrieved]
        List<ProductAttrValueEntity> baseAttrs = attrValueService.baseAttrListForSpu(spuId);

        List<Long> attrIds = baseAttrs.stream().map(attr -> attr.getAttrId()).collect(Collectors.toList());
        // avaliable ids
        Set<Long> isSet = new HashSet<>(attrService.selectSearchAttrIds(attrIds));

        // Filter unretrievable products based on product id Last mapping number to retrieve attributes
        List<SkuEsModel.Attrs> attrs = baseAttrs.stream().filter(item -> isSet.contains(item.getAttrId())).map(item -> {
            SkuEsModel.Attrs attr = new SkuEsModel.Attrs();
            BeanUtils.copyProperties(item, attr);
            return attr;
        }).collect(Collectors.toList());

        // skuId, whether there is stock
        Map<Long, Boolean> stockMap = null;
        try {
            // 3.1 Send remote call Inventory system to check if there is inventory
            R hasStock = wareFeignService.getSkuHasStock(skuids);
            // The constructor is protected, so it is written as an inner class object
            stockMap = hasStock.getData(new TypeReference<List<SkuHasStockVo>>(){}).stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, item -> item.getHasStock()));
            log.warn("success" + hasStock);
        } catch (Exception e) {
            log.error("error: {}",e);
        }

        Map<Long, Boolean> finalStockMap = stockMap;
        List<SkuEsModel> collect = skus.stream().map(sku -> {
            SkuEsModel esModel = new SkuEsModel();
            BeanUtils.copyProperties(sku, esModel);
            esModel.setSkuPrice(sku.getPrice());
            esModel.setSkuImg(sku.getSkuDefaultImg());
            // 4 set stock
            if(finalStockMap == null){
                esModel.setHasStock(true);
            }else {
                esModel.setHasStock(finalStockMap.get(sku.getSkuId()));
            }
            // TODO 1.rank 0
            esModel.setHotScore(0L);

            BrandEntity brandEntity = brandService.getById(esModel.getBrandId());

            // brandName、brandImg
            esModel.setBrandName(brandEntity.getName());
            esModel.setBrandImg(brandEntity.getLogo());

            // select category info
            CategoryEntity categoryEntity = categoryService.getById(esModel.getCatalogId());
            esModel.setCatalogName(categoryEntity.getName());

            // save attr
            esModel.setAttrs(attrs);
            return esModel;
        }).collect(Collectors.toList());

        // 5.send to es to save  mall-search
        R r = searchFeignService.productStatusUp(collect);
        if(r.getCode() == 0){
            // success
            baseMapper.updateSpuStatus(spuId, ProductConstant.StatusEnum.SPU_UP.getCode());
        }else{
            // Remote call failure
            // TODO interface idempotency Retry mechanism
        }
    }

    @Override
    public SpuInfoEntity getSpuInfoBySkuId(Long skuId) {

        return getById(skuInfoService.getById(skuId).getSpuId());
    }
}