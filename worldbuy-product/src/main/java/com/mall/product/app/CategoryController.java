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
 * Commodity three-level classification
 *
 * @author xinjian li
 * @email xinjian.li1@outlook.com
 * @date 2022-07-16 17:06:04
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
  @Autowired
  private CategoryService categoryService;

  /**
   * Find all categories and subcategories, and assemble them in a tree structure
   */
  @RequestMapping("/list/tree")
  public R list(){
    List<CategoryEntity> entities = categoryService.listWithTree();
// filter out all primary categories
    List<CategoryEntity> level1Menus = entities.stream().
            filter((categoryEntity) -> categoryEntity.getParentCid() == 0)
            .map((menu) -> {
              menu.setChildren(getChildrens(menu, entities));
              return menu;
            }).sorted((menu1, menu2) -> {
              return (menu1.getSort() == null? 0 : menu1.getSort()) - (menu2.getSort() == null? 0 : menu2.getSort());
            })
            .collect(Collectors.toList());
    return R.ok().put("data", level1Menus);
  }

  /**
   * Recursively find all submenus, sort midway through
   */
  private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all){
    List<CategoryEntity> children = all.stream().filter(categoryEntity ->
            categoryEntity.getParentCid() == root.getCatId()
    ).map(categoryEntity -> {
      categoryEntity.setChildren(getChildrens(categoryEntity, all));
      return categoryEntity;
    }).sorted((menu1,menu2) -> {
      return (menu1.getSort() == null? 0 : menu1.getSort()) - (menu2.getSort() == null? 0 : menu2.getSort());
    }).collect(Collectors.toList());
    return children;
  }


  /**
   * information
   */
  @RequestMapping("/info/{catId}")
  public R info(@PathVariable("catId") Long catId){
    CategoryEntity category = categoryService.getById(catId);

    return R.ok().put("data", category);
  }

  /**
   * save
   */
  @RequestMapping("/save")
  //@RequiresPermissions("product:category:save")
  public R save(@RequestBody CategoryEntity category){
    categoryService.save(category);
    return R.ok();
  }

  /**
   * Batch edit levels
   */
  @RequestMapping("/update/sort")
  public R updateSort(@RequestBody CategoryEntity[] category){
    categoryService.updateBatchById(Arrays.asList(category));
    return R.ok();
  }

  /**
   * Revise
   */
  @RequestMapping("/update")
  public R update(@RequestBody CategoryEntity category){
    categoryService.updateCascade(category);

    return R.ok();
  }

  /**
   * delete
   * Must send POST request
   */
  @RequestMapping("/delete")
  public R delete(@RequestBody Long[] catIds){
    categoryService.removeByIds(Arrays.asList(catIds));
// Check if the current node is referenced elsewhere
    categoryService.removeMenuByIds(Arrays.asList(catIds));
    return R.ok();
  }

}