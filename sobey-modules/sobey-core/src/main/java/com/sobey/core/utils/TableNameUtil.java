package com.sobey.core.utils;

import javax.persistence.Table;

/**
 * 获取实体对应的表名
 */
public class TableNameUtil {

	/**
	 * 返回@Table中的name值，否则返回实体的名称
	 * 
	 * @param c
	 * @return String
	 */
	public static String getTableName(Class<?> c) {
		String name = c.getAnnotation(Table.class).name();

		if (name.equals(""))
			return c.getSimpleName();

		return name;
	}

}
