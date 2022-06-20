package com.mall.product.exception;

import com.mall.common.exception.BizCodeEnum;
import com.mall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: MallExceptionControllerAdvice</p>
 * Description：集中处理所有异常
 * date：2020/6/1 21:19
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.mall.product.controller")
public class MallExceptionControllerAdvice {


	@ExceptionHandler(value = {WebExchangeBindException.class})
	public R handleVaildException(WebExchangeBindException e) {
		log.error("data format exception{}，exception type：{}", e.getMessage(), e.getClass());
		BindingResult bindingResult = e.getBindingResult();

		Map<String, String> errorMap = new HashMap<>();
		bindingResult.getFieldErrors().forEach((fieldError) -> {
			errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
		});
		return R.error(BizCodeEnum.VAILD_EXCEPTION.getCode(), BizCodeEnum.VAILD_EXCEPTION.getMsg()).put("data", errorMap);
	}

	@ExceptionHandler(value = Throwable.class)
	public R handleException(Throwable throwable) {

		log.error("exception：", throwable);
		return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(), BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
	}
}
