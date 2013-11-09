package com.sobey.cmdbuild.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

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
	 * 
	 * 由Page属性填充成满足webservice分页的PaginationResult对象
	 * 
	 * @param page
	 *            entity的Page对象
	 * @param dtos
	 *            entity的dto集合
	 * @return
	 */
	protected <T, V> PaginationResult<T> fillPaginationResult(Page<V> page, List<T> dtos) {

		PaginationResult<T> paginationResult = new PaginationResult<T>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getNumberOfElements(), page.hasPreviousPage(),
				page.isFirstPage(), page.hasNextPage(), page.isLastPage(), dtos);

		return paginationResult;

	}

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
