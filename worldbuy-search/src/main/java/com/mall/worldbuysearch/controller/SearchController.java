package com.mall.worldbuysearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: SearchController</p>
 * Description：
 * date：2020/6/12 21:46
 */
@Controller
public class SearchController {

//	@Autowired
//	private MallService masllService;

//	@GetMapping("/list.html")
//	public String listPage(SearchParam searchParam, Model model, HttpServletRequest request){
//
//		searchParam.set_queryString(request.getQueryString());
//		SearchResult result = masllService.search(searchParam);
//		model.addAttribute("result", result);
//		return "list";
//	}
}
