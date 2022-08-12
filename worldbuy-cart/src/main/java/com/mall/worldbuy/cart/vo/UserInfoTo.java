package com.mall.worldbuy.cart.vo;

import lombok.Data;
import lombok.ToString;

/**
 * <p>Title: UserInfoVo</p>
 * Description：
 * date：2022/8/14 22:34
 */
@ToString
@Data
public class UserInfoTo {

	/**
	 * Store the ID of the logged in user in the database
	 */
	private Long userId;

	/**
	 * store username
	 */
	private String username;

	/**
	 * Allocate a temporary user-key
	 */
	private String userKey;

	/**
	 * Determine whether it is a temporary user
	 */
	private boolean tempUser = false;
}
