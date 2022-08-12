package com.mall.worldbuy.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Title: Cart</p>
 * Description：shopping cart
 * date：2022/8/14 21:10
 */
public class Cart {

	private List<CartItem> items;

	/**
	 * Quantity of item
	 */
	private Integer countNum;

	/**
	 * The number of types of goods
	 */
	private Integer countType;

	/**
	 * The total price of the entire shopping cart
	 */
	private BigDecimal totalAmount;

	/**
	 * Reduced price
	 */
	private BigDecimal reduce = new BigDecimal("0.00");


	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	/**
	 * Calculate the total amount of goods
	 * @return
	 */
	public Integer getCountNum() {
		int count = 0;
		if(this.items != null && this.items.size() > 0){
			for (CartItem item : this.items) {
				count += item.getCount();
			}
		}
		return count;
	}

	public Integer getCountType() {
		int count = 0;
		if(this.items != null && this.items.size() > 0){
			for (CartItem item : this.items) {
				count += 1;
			}
		}
		return count;
	}

	public BigDecimal getTotalAmount() {
		BigDecimal amount = new BigDecimal("0");
		if(this.items != null && this.items.size() > 0){
			for (CartItem item : this.items) {
				if(item.getCheck()){
					BigDecimal totalPrice = item.getTotalPrice();
					amount = amount.add(totalPrice);
				}
			}
		}
		return amount.subtract(this.getReduce());
	}

	public BigDecimal getReduce() {
		return reduce;
	}

	public void setReduce(BigDecimal reduce) {
		this.reduce = reduce;
	}
}
