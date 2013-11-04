package com.sobey.cmdbuild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * Service的基类
 * 
 * 包含了常用的分页参数,所有业务的Service注入等.
 * 
 * 建议所有的业务service类继承此类.
 * 
 * @author Administrator
 * 
 */
public class BasicSevcie {

	@Autowired
	protected CommonService comm;

	/**
	 * 创建分页请求. 默认以id为DESC 倒序查询
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1
	 * @param pagzSize
	 *            每天大小,即每页有多少行.
	 * @return
	 */
	protected PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize, new Sort(Direction.DESC, "id"));
	}

	/**
	 * 创建分页请求. 以传入的sort排序
	 * 
	 * @param pageNumber
	 *            当前页数,最小为1
	 * @param pagzSize
	 *            每天大小,即每页有多少行.
	 * @return
	 */
	protected PageRequest buildPageRequest(int pageNumber, int pagzSize, Sort sort) {
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

}
