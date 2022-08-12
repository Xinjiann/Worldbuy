package com.mall.worldbuy.cart.service;

import com.mall.worldbuy.cart.vo.Cart;
import com.mall.worldbuy.cart.vo.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <p>Title: CartService</p>
 * Description：
 * date：2022/8/14 22:17
 */
public interface CartService {

	/**
	 * Add item to cart
	 */
	CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

	/**
	 * Get a shopping item in the shopping cart
	 */
	CartItem getCartItem(Long skuId);

	/**
	 * Get the whole cart
	 */
	Cart getCart() throws ExecutionException, InterruptedException;

	/**
	 * Empty shopping cart
	 */
	void clearCart(String cartKey);

	/**
	 * Check the shopping item
	 */
	void checkItem(Long skuId, Integer check);

	/**
	 * Change the number of items in the cart
	 */
	void changeItemCount(Long skuId, Integer num);

	/**
	 * delete shopping item
	 */
	void deleteItem(Long skuId);

	/**
	 * Bill, please
	 */
	BigDecimal toTrade() throws ExecutionException, InterruptedException;

	List<CartItem> getUserCartItems();
}
