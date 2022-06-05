/*
 * Copyright (c) 2021/9/5 By Alireza Soltani Jazi
 */

import lombok.extern.slf4j.Slf4j;
import model.BillEntity;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CreateBill {
	/**
	 * A method to calculate controller flag - 1 char - 0 to 9 - we have 3 controller flag, 1 for BillId and 2 controller flag for PayID, at the end return BillId+PayID
	 * BillId: min 6 char - max 13 char
	 * PayId: min 6 char - max 13 char
	 * Amount without 1k rial - max 8 char
	 * Barcode: Document + Company + Product + CF0 + Amount + Year + Period + CF1 + CF2
	 *
	 * @param bill: DocumentCode billId param - max 8 char - min 1 - can delete the zero numbers -
	 *              CompanyCode  billId param - 3 char - with zero - 000 to 999
	 *              ProductCode  billId param - 1 char - 1 water 2 electricity 3 gas 4 fix line 5 mobile 6 municipality
	 *              Amount       payId param - the price of bill without 1k rial - max 8 char
	 *              Year         payId param - 1 char - 0 to 9
	 *              Period       payId param - 2 char - 00 to 99
	 * @return BillId+PayID
	 */
	public BillEntity createBill(BillEntity bill) {
		if (billDataValidation(bill)) {
			//Prevent data loss (zero number)
			StringBuilder billIdCombination = new StringBuilder();
			StringBuilder payIdCombination = new StringBuilder();
			//Append data
			billIdCombination.append(bill.getDocumentCode()).append(bill.getCompanyCode()).append(bill.getProductCode());
			payIdCombination.append(bill.getAmount()).append(bill.getYear()).append(bill.getPeriod());
			//Convert data to long for calculation
			long billIdData = Long.parseLong(billIdCombination.toString());
			long payIdData = Long.parseLong(payIdCombination.toString());
			//Get controller flag and append it
			StringBuilder controllerFlag = new StringBuilder();
			controllerFlag.append(calculateControllerFlag(billIdData));
			bill.setControllerFlag0(controllerFlag.toString());
			billIdCombination.append(controllerFlag);
			controllerFlag.setLength(0);
			controllerFlag.append(calculateControllerFlag(payIdData));
			bill.setControllerFlag1(controllerFlag.toString());
			payIdCombination.append(controllerFlag);
			bill.setBillId(billIdCombination.toString());
			logger.info("Bill ID is: {}", billIdCombination);
			controllerFlag.setLength(0);
			controllerFlag.append(calculateControllerFlag(Long.parseLong(billIdCombination.toString() + payIdCombination)));
			bill.setControllerFlag2(controllerFlag.toString());
			payIdCombination.append(controllerFlag);
			bill.setPayId(payIdCombination.toString());
			logger.info("Pay ID is: {}", payIdCombination);
		}
		return bill;
	}

	private boolean billDataValidation(BillEntity bill) {
		if (bill.getDocumentCode().length() < 1 || bill.getDocumentCode().length() > 8) {
			logger.error("Document code length ({}) is invalid!", bill.getDocumentCode().length());
			return false;
		} else if (Long.parseLong(bill.getCompanyCode()) < 0 || Long.parseLong(bill.getCompanyCode()) > 999) {
			logger.error("Company code range ({}) is invalid!", bill.getCompanyCode());
			return false;
		} else if (Long.parseLong(bill.getProductCode()) < 1 || Long.parseLong(bill.getProductCode()) > 6) {
			logger.error("Product code range ({}) is invalid!", bill.getProductCode());
			return false;
		} else if (Long.parseLong(bill.getPeriod()) < 0 || Long.parseLong(bill.getPeriod()) > 99) {
			logger.error("Period range ({}) is invalid!", bill.getPeriod());
			return false;
		} else if (Long.parseLong(bill.getAmount()) < 0 || bill.getAmount().length() > 8) {
			logger.error("Amount range ({}) is invalid!", bill.getAmount());
			return false;
		} else return true;
	}

	private int calculateControllerFlag(long data) {
		long number = data;
		List<Integer> list = new ArrayList<>();
		while (number > 0) {
			int tempData = (int) (number % 10);
			list.add(tempData);
			number /= 10;
		}
		int controllerMultiplier = 2;
		int sum = 0;
		for (Integer integer : list) {
			//Reset controller multiplier to start from 2 again
			if (controllerMultiplier > 7) controllerMultiplier = 2;
			//Multiply each number to 2-7
			sum += integer * controllerMultiplier;
			controllerMultiplier++;
		}
		int result = sum % 11;
		System.out.println(sum);
		int controllerFlag = 0;
		//If false => Controller flag is 0
		if (result != 0 || result != 1) {
			controllerFlag = 11 - result;
		}
		return controllerFlag;
	}
}