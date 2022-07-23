package com.mall.worldbuysearch.service;

import com.mall.worldbuysearch.vo.SearchParam;
import com.mall.worldbuysearch.vo.SearchResult;


/**
 * <p>Title: MasllService</p>
 * Description：
 * date：2020/6/12 23:05
 */
public interface MallService {

	/**
	 * retrieve all parameters
	 */
	SearchResult search(SearchParam Param);
}
