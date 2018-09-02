package com.fccapital.pam;

import java.math.BigDecimal;

public class Constants {
	
	/**
	 * The Profit is calculated as multiplication of transaction amount by 0.025(transaction processing fee).
	 */
	public static final BigDecimal TRANSACTION_PROCESSING_FEE = new BigDecimal(0.025);
	
	/**
	 * Node profit distribution ratio – the node keeps X% and gives down the tree (100-X)%
	 */
	public static final BigDecimal PROFIT_DISTRIBUTION_RATIO = new BigDecimal(0.5);

}
