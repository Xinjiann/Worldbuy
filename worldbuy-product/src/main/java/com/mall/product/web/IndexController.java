package com.mall.product.web;


import com.mall.product.entity.CategoryEntity;
import com.mall.product.service.CategoryService;
import com.mall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>Title: IndexController</p>
 * Description：
 * date：2020/6/9 14:01
 */
@Controller
public class IndexController {

	@Autowired
	CategoryService categoryService;

	@RequestMapping({"/", "index", "/index.html"})
	public String indexPage(Model model) {
		// 获取一级分类所有缓存
		List<CategoryEntity> categorys = categoryService.getLevel1Categorys();
		model.addAttribute("categorys", categorys);
		return "index";
	}

	@ResponseBody
	@RequestMapping("index/catalog.json")
	public Map<String, List<Catelog2Vo>> getCatlogJson() {

		Map<String, List<Catelog2Vo>> map = categoryService.getCatelogJson();
		return map;
	}


}
