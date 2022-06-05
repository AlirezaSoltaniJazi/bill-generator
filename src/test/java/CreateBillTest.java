/*
 * Copyright (c) 2021/9/8 By Alireza Soltani Jazi
 */

import enums.ProductCodeEnum;
import model.BillEntity;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CreateBillTest {
	@Test(testName = "Check create bill with valid data",
			description = "Check create a bill with valid data (all data are in normal range)",
			priority = 1)
	public void testCreateBill() {
		BillEntity bill = new BillEntity();
		bill.setDocumentCode("1696999");
		bill.setCompanyCode("132");
		bill.setProductCode(String.valueOf(ProductCodeEnum.MOBILE.getBillType()));
		bill.setAmount("1");
		bill.setYear("0");
		bill.setPeriod("00");
		CreateBill createBill = new CreateBill();
		BillEntity billEntity = createBill.createBill(bill);

		Assert.assertNotNull(billEntity.getBillId());
		Assert.assertNotNull(billEntity.getDocumentCode());
		Assert.assertNotNull(billEntity.getCompanyCode());
		Assert.assertNotNull(billEntity.getProductCode());
		Assert.assertNotNull(billEntity.getControllerFlag0());
		Assert.assertNotNull(billEntity.getPayId());
		Assert.assertNotNull(billEntity.getAmount());
		Assert.assertNotNull(billEntity.getYear());
		Assert.assertNotNull(billEntity.getPeriod());
		Assert.assertNotNull(billEntity.getControllerFlag1());
		Assert.assertNotNull(billEntity.getControllerFlag2());
		Assert.assertTrue(billEntity.getBillId().length() >= 6 && billEntity.getBillId().length() <= 13);
		Assert.assertTrue(billEntity.getPayId().length() >= 6 && billEntity.getPayId().length() <= 13);
	}

	@Test(testName = "Check unable to create bill with invalid document code",
			description = "Check unable to create bill if document code length was less that standard length",
			priority = 2)
	public void createBillDocumentCodeLessThanMin() {
		BillEntity bill = new BillEntity();
		bill.setDocumentCode("");
		bill.setCompanyCode("132");
		bill.setProductCode(String.valueOf(ProductCodeEnum.MOBILE.getBillType()));
		bill.setAmount("1");
		bill.setYear("0");
		bill.setPeriod("00");
		CreateBill createBill = new CreateBill();
		BillEntity billEntity = createBill.createBill(bill);

		Assert.assertNull(billEntity.getBillId());
		Assert.assertNotNull(billEntity.getDocumentCode());
		Assert.assertNotNull(billEntity.getCompanyCode());
		Assert.assertNotNull(billEntity.getProductCode());
		Assert.assertNull(billEntity.getControllerFlag0());
		Assert.assertNull(billEntity.getPayId());
		Assert.assertNotNull(billEntity.getAmount());
		Assert.assertNotNull(billEntity.getYear());
		Assert.assertNotNull(billEntity.getPeriod());
		Assert.assertNull(billEntity.getControllerFlag1());
		Assert.assertNull(billEntity.getControllerFlag2());
	}
}