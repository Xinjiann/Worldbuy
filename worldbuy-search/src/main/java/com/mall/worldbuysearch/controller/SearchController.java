package com.mall.worldbuysearch.controller;

import com.mall.worldbuysearch.service.MallService;
import com.mall.worldbuysearch.vo.SearchParam;
import com.mall.worldbuysearch.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: SearchController</p>
 * Description：
 * date：2022/7/22 21:46
 */
@Controller
public class SearchController {

	@Autowired
	private MallService mallService;

	@GetMapping("/list.html")
	public String listPage(SearchParam searchParam, Model model, HttpServletRequest request){

		searchParam.set_queryString(request.getQueryString());
		SearchResult result = mallService.search(searchParam);
		model.addAttribute("result", result);
		return "list";
	}
}
