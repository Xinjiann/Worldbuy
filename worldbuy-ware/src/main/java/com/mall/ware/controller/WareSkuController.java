package com.mall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.mall.common.to.es.SkuHasStockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mall.ware.entity.WareSkuEntity;
import com.mall.ware.service.WareSkuService;
import com.mall.common.utils.PageUtils;
import com.mall.common.utils.R;



/**
 * 商品库存
 *
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 02:12:32
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    /**
     * Check if sku is in stock
     * Return the current id stock amount
     */
    @PostMapping("/hasStock")
    public R getSkuHasStock(@RequestBody List<Long> SkuIds){
        List<SkuHasStockVo> vos = wareSkuService.getSkuHasStock(SkuIds);
        return R.ok().setData(vos);
    }

    /**
     * list
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * info
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * save
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * update
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * delete
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
