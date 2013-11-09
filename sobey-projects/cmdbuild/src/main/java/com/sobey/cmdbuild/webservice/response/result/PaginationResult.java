package com.sobey.cmdbuild.webservice.response.result;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

/**
 * 某个对象返回的通用分页PaginationResult.
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@XmlType(name = "PaginationResult", namespace = WsConstants.NS)
public class PaginationResult<T> extends WSResult {

	// -- PaginationResult基本属性 --//

	/**
	 * 当前页面的数量，最小值为1
	 */
	private int getNumber;

	/**
	 * 每页大小
	 */
	private int getSize;

	/**
	 * 总页数
	 */
	private int getTotalPages;

	/**
	 * 当前页面上的元素的数量
	 */
	private int getNumberOfElements;

	/**
	 * 元素的总量
	 */
	private int getTotalElements;

	/**
	 * 是否有上一页
	 */
	private boolean hasPreviousPage;

	/**
	 * 是否首页
	 */
	private boolean isFirstPage;

	/**
	 * 是否有下一页
	 */
	private boolean hasNextPage;

	/**
	 * 是否末页
	 */
	private boolean isLastPage;

	/**
	 * 对象集合
	 */
	private List<T> getContent;

	public PaginationResult() {
		super();
	}

	public PaginationResult(int getNumber, int getSize, int getTotalPages, int getNumberOfElements,
			int getTotalElements, boolean hasPreviousPage, boolean isFirstPage, boolean hasNextPage,
			boolean isLastPage, List<T> getContent) {
		super();
		this.getNumber = getNumber;
		this.getSize = getSize;
		this.getTotalPages = getTotalPages;
		this.getNumberOfElements = getNumberOfElements;
		this.getTotalElements = getTotalElements;
		this.hasPreviousPage = hasPreviousPage;
		this.isFirstPage = isFirstPage;
		this.hasNextPage = hasNextPage;
		this.isLastPage = isLastPage;
		this.getContent = getContent;
	}

	public int getGetNumber() {
		return getNumber;
	}

	public void setGetNumber(int getNumber) {
		this.getNumber = getNumber;
	}

	public int getGetSize() {
		return getSize;
	}

	public void setGetSize(int getSize) {
		this.getSize = getSize;
	}

	public int getGetTotalPages() {
		return getTotalPages;
	}

	public void setGetTotalPages(int getTotalPages) {
		this.getTotalPages = getTotalPages;
	}

	public int getGetNumberOfElements() {
		return getNumberOfElements;
	}

	public void setGetNumberOfElements(int getNumberOfElements) {
		this.getNumberOfElements = getNumberOfElements;
	}

	public int getGetTotalElements() {
		return getTotalElements;
	}

	public void setGetTotalElements(int getTotalElements) {
		this.getTotalElements = getTotalElements;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public List<T> getGetContent() {
		return getContent;
	}

	public void setGetContent(List<T> getContent) {
		this.getContent = getContent;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
