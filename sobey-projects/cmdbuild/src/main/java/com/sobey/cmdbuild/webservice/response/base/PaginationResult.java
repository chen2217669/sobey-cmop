package com.sobey.cmdbuild.webservice.response.base;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.webservice.WsConstants;

/**
 * webservice的分页
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@XmlType(name = "PaginationResult", namespace = WsConstants.NS)
public class PaginationResult<T> extends WSResult {

	// -- PaginationResult基本属性 --//
	private int getNumber;
	private int getSize;
	private int getTotalPages;
	private int getNumberOfElements;
	private int getTotalElements;
	private boolean hasPreviousPage;
	private boolean isFirstPage;
	private boolean hasNextPage;
	private boolean isLastPage;
	private List<T> t;

	public PaginationResult() {
		super();
	}

	public PaginationResult(int getNumber, int getSize, int getTotalPages, int getNumberOfElements,
			int getTotalElements, boolean hasPreviousPage, boolean isFirstPage, boolean hasNextPage,
			boolean isLastPage, List<T> t) {
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
		this.t = t;
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

	public List<T> getT() {
		return t;
	}

	public void setT(List<T> t) {
		this.t = t;
	}

}
