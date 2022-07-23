package com.mall.worldbuysearch.vo;

import com.mall.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: SearchResponse</p>
 * Description: Contains all the information the page needs
 * date: 2022/7/22 23:29
 */
@Data
public class SearchResult {

	/**
	 * All product information queried
	 */
	private List<SkuEsModel> products;

	/**
	 * current page number
	 */
	private Integer pageNum;

	/**
	 * total
	 */
	private Long total;

	/**
	 *Total page number
	 */
	private Integer totalPages;

	/**
	 * The results of the current query, all involved brands
	 */
	private List<BrandVo> brands;

	/**
	 * The results of the current query, all the categories involved
	 */
	private List<CatalogVo> catalogs;

	/**
	 * Navigation page
	 */
	private List<Integer> pageNavs;

	/**
	 * The result of the current query all involves all properties
	 */
	private List<AttrVo> attrs;

// ================The above is all the information returned to the page================

	// breadcrumb data
	private List<NavVo> navs = new ArrayList<>();

	/**
	 * It is convenient to judge whether the current id is used or not
	 */
	private List<Long> attrIds = new ArrayList<>();

	@Data
	public static class NavVo{
		private String name;

		private String navValue;

		private String link;
	}

	@Data
	public static class BrandVo{

		private Long brandId;

		private String brandName;

		private String brandImg;
	}

	@Data
	public static class CatalogVo{

		private Long catalogId;

		private String catalogName;
	}

	@Data
	public static class AttrVo{

		private Long attrId;

		private String attrName;

		private List<String> attrValue;
	}
}
