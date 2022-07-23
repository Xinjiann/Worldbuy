package com.mall.worldbuysearch.vo;

import lombok.Data;

@Data
public class AttrResponseVo {

	/**
	 * attribute id
	 */
	private Long attrId;
	/**
	 * attribute name
	 */
	private String attrName;
	/**
	 * Do you need to retrieve [0-not required, 1-required]
	 */
	private Integer searchType;
	/**
	 * Properties icon
	 */
	private String icon;
	/**
	 * list of optional values [separated by commas]
	 */
	private String valueSelect;
	/**
	 * attribute type [0-sales attribute, 1-basic attribute, 2-both sales attribute and basic attribute]
	 */
	private Integer attrType;
	/**
	 * enable state [0 - disabled, 1 - enabled]
	 */
	private Long enable;
	/**
	 * category
	 */
	private Long catelogId;
	/**
	 * Quick display [whether to display on the introduction; 0-no 1-yes], can still be adjusted in sku
	 */
	private Integer showDesc;

	private Long attrGroupId;

	private String catelogName;

	private String groupName;

	private Long[] catelogPath;
}