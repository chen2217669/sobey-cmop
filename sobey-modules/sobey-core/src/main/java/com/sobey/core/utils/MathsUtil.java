package com.sobey.core.utils;

import java.math.BigDecimal;

/**
 * 提供四则运算.
 * 
 * @author Administrator
 * 
 */
public class MathsUtil {

	/**
	 * 默认除法运算精度(小数点后2位数)
	 */
	private static final int DEFAULT_SCALE = 2;

	/**
	 * 
	 * 加法运算.
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 减法运算.
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 乘法运算.只保留小数点后两位.
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).setScale(DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 除法运算.当发生除不尽的情况时,精度为2,以后的数字四舍五入.
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 除法运算.当发生除不尽的情况时,由scale参数指定精度,后面的数字四舍五入.
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位.
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
