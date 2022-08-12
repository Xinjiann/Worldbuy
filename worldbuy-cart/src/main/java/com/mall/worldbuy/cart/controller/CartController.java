package com.mall.worldbuy.cart.controller;

import com.mall.worldbuy.cart.service.CartService;
import com.mall.worldbuy.cart.vo.Cart;
import com.mall.worldbuy.cart.vo.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <p>Title: CartController</p>
 * Description：
 * date：2022/8/14 22:20
 */
@Slf4j
@Controller
public class CartController {

	private final String PATH = "redirect:http://cart.worldbuy.com/cart.html";

	@Autowired
	private CartService cartService;

	@ResponseBody
	@GetMapping("/currentUserCartItems")
	public List<CartItem> getCurrentUserCartItems(){

		return cartService.getUserCartItems();
	}

	@ResponseBody
	@GetMapping("toTrade")
	public String toTrade() throws ExecutionException, InterruptedException {
		BigDecimal price = cartService.toTrade();
		return "success,total：￥" + price.toString();
	}

	@GetMapping("/deleteItem")
	public String deleteItem(@RequestParam("skuId") Long skuId){
		cartService.deleteItem(skuId);
		return PATH;
	}

	@GetMapping("/countItem")
	public String countItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num){
		cartService.changeItemCount(skuId, num);
		return PATH;
	}

	@GetMapping("checkItem.html")
	public String checkItem(@RequestParam("skuId") Long skuId, @RequestParam("check") Integer check){
		cartService.checkItem(skuId, check);
		return PATH;
	}

	@GetMapping("/addToCartSuccess.html")
	public String addToCartSuccessPage(@RequestParam(value = "skuId",required = false) Object skuId, Model model){
		CartItem cartItem = null;
		// Then check the shopping cart
		if(skuId == null){
			model.addAttribute("item", null);
		}else{
			try {
				cartItem = cartService.getCartItem(Long.parseLong((String)skuId));
			} catch (NumberFormatException e) {
				log.warn("Malicious operation! Illegal characters came from the page.");
			}
			model.addAttribute("item", cartItem);
		}
		return "success";
	}

	/**
	 * Add item to cart
	 * RedirectAttributes: will automatically add data to the back of the url
	 */
	@GetMapping("/addToCart")
	public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes redirectAttributes) throws ExecutionException, InterruptedException {

		cartService.addToCart(skuId, num);
		redirectAttributes.addAttribute("skuId", skuId);
		// 重定向到成功页面
		return "redirect:http://cart.worldbuy.com/addToCartSuccess.html";
	}

	/**
	 * The browser has a cookie: user-key identifies the user and expires after one month
	 * Each visit will bring this user-key
	 * If you don't have a temporary user, help create one
	 */
	@GetMapping({"/","/cart.html"})
	public String carListPage(Model model) throws ExecutionException, InterruptedException {

		Cart cart = cartService.getCart();
		model.addAttribute("cart", cart);
		return "cartList";
	}
}
