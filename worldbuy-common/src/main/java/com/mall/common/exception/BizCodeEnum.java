package com.mall.common.exception;

/**
 * <p>Title: BizCodeEnum</p>
 * Description：
 * Error code and error message definition class
 * 1. The error code definition rule is that 5 is a number
 * 2. The first two digits represent business scenarios, and the last three digits represent error codes. For example: 100001. 10: General 001: System unknown exception
 * 3. After maintaining error codes, you need to maintain error descriptions and define them as enumerations
 * Error code list:
 * 10: Universal
 * 001: Parameter format check
 * 002: The frequency of SMS verification code is too high
 * 11: Commodity
 * 12: Order
 * 13: Shopping Cart
 * 14: Logistics
 * 15: User
 * 21: Inventory
 * date：2022/7/3
 */
public enum BizCodeEnum {
	UNKNOW_EXCEPTION(10000, "System unknown exception"),
	VAILD_EXCEPTION(10001, "Parameter format verification failed"),
	SMS_CODE_EXCEPTION(10002, "The frequency of getting the verification code is too high, try again later"),
	TO_MANY_REQUEST(10003, "Request too much traffic"),
	SMS_SEND_CODE_EXCEPTION(10403, "Failed to send SMS"),
	USER_EXIST_EXCEPTION(15001, "User already exists"),
	PHONE_EXIST_EXCEPTION(15002, "The phone number already exists"),
	LOGINACTT_PASSWORD_ERROR(15003, "Incorrect account or password"),
	SOCIALUSER_LOGIN_ERROR(15004, "Social account login failed"),
	NOT_STOCK_EXCEPTION(21000, "Insufficient product stock"),
	PRODUCT_UP_EXCEPTION(11000,"Abnormal product listing");

	private int code;

	private String msg;

	BizCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
