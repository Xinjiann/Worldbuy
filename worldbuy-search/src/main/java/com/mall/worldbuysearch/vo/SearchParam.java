package com.mall.worldbuysearch.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>Title: SearchParam</p>
 * Description: Encapsulates all keywords that may be passed on the page
 * catalog3Id=225&keyword=HUAWEI&sort=saleCount_asc&hasStock=0/1&brandId=25&brandId=30
 * date: 2022/7/22 22:56
 */
@Data
public class SearchParam {

	/**
	 * Full text match keywords
	 */
	private String keyword;

	/**
	 * three-level classification id
	 */
	private Long catalog3Id;

	private String sort;

	private Integer hasStock;

	/**
	 * The price range
	 */
	private String skuPrice;

	/**
	 * Brand id can be more than one choice
	 */
	private List<Long> brandId;

	/**
	 * filter by attribute
	 */
	private List<String> attrs;

	/**
	 * page number
	 */
	private Integer pageNum = 1;

	/**
	 * Native all query properties
	 */
	private String _queryString;
}