/*
 * Copyright (c) 2021/9/9 By Alireza Soltani Jazi
 */

package enums;import lombok.Getter;

@Getter
public enum ProductCodeEnum {

	WATER(1), ELECTRICITY(2), GAS(3), FIX_LINE(5), MOBILE(6);
	private int billType;

	ProductCodeEnum(int billType) {
		this.billType = billType;
	}
}