package com.mall.product.app;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mall.product.entity.CategoryEntity;
import com.mall.product.service.CategoryService;
import com.mall.common.utils.R;


/**
 * 商品三级分类
 *
 * @author lixinjian
 * @email xinjian.li1@outlook.com
 * @date 2022-02-08 01:09:17
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  /**
   * 列表
   */
  @RequestMapping("/list/tree")
  public R list() {
    List<CategoryEntity> categoryEntityList = categoryService.listWithTree();
    List<CategoryEntity> level1 = categoryEntityList.stream().filter(i -> i.getParentCid() == 0)
        .peek(i -> i.setChildren(getChildren(i, categoryEntityList)))
        .sorted(Comparator.comparingInt(i -> (i.getSort() == null ? 0 : i.getSort())))
        .collect(Collectors.toList());
    return R.ok().put("page", level1);
  }

  private List<CategoryEntity> getChildren(CategoryEntity root,
      List<CategoryEntity> categoryEntityList) {
    return categoryEntityList.stream()
        .filter(i -> Objects.equals(i.getParentCid(), root.getCatId()))
        .peek(i -> i.setChildren(getChildren(i, categoryEntityList)))
        .sorted(Comparator.comparingInt(i -> (i.getSort() == null ? 0 : i.getSort()))).collect(
            Collectors.toList());
  }


  /**
   * 信息
   */
  @RequestMapping("/info/{catId}")
  public R info(@PathVariable("catId") Long catId) {
    CategoryEntity category = categoryService.getById(catId);

    return R.ok().put("category", category);
  }

  /**
   * 保存
   */
  @RequestMapping("/save")
  public R save(@RequestBody CategoryEntity category) {
    categoryService.save(category);

    return R.ok();
  }

  /**
   * 修改
   */
  @RequestMapping("/update")
  public R update(@RequestBody CategoryEntity category) {
    categoryService.updateById(category);

    return R.ok();
  }

  /**
   * 删除
   */
  @RequestMapping("/delete")
  public R delete(@RequestBody Long[] catIds) {
//    categoryService.removeByIds(Arrays.asList(catIds));
    categoryService.removeMenuByIds(Arrays.asList(catIds));
    return R.ok();
  }

}
