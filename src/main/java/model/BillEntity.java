/*
 * Copyright (c) 2021/9/9 By Alireza Soltani Jazi
 */

package model;import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillEntity {
	private String billId;
	private String documentCode;
	private String companyCode;
	private String productCode;
	private String controllerFlag0;
	private String payId;
	private String amount;
	private String year;
	private String period;
	private String controllerFlag1;
	private String controllerFlag2;
}