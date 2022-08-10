package com.mall.thirdparty.controller;

import com.mall.common.exception.BizCodeEnum;
import com.mall.common.utils.R;
import com.mall.thirdparty.component.ThirdPartyMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>Title: SmsSendController</p>
 * Description：
 * date：2022/8/11 14:53
 */
@Controller
@RequestMapping("/sms")
public class SmsSendController {

	@Autowired
	private ThirdPartyMsg thirdPartyMsg;

	/**
	 * 提供给别的服务进行调用的
	 */
	@GetMapping("/sendcode")
	public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code){
		if(!"fail".equals(thirdPartyMsg.SendCode(phone, code).split("_")[0])){
			return R.ok();
		}
		return R.error(BizCodeEnum.SMS_SEND_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_SEND_CODE_EXCEPTION.getMsg());
	}
}
