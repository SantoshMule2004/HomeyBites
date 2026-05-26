//package com.homeybites.payloads;
//
//import java.util.List;
//
//import com.homeybites.entities.OrderInfo;
//
//public class OrderResponse {
//	private List<OrderInfo> allContent;
//	private int pageNumber;
//	private int pageSize;
//	private int totalPages;
//	private long totalElements;
//	private boolean lastPage;
//	
//	public OrderResponse() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public OrderResponse(List<OrderInfo> allContent, int pageNumber, int pageSize, long totalElements, int totalPages,
//			boolean lastPage) {
//		super();
//		this.allContent = allContent;
//		this.pageNumber = pageNumber;
//		this.pageSize = pageSize;
//		this.totalElements = totalElements;
//		this.totalPages = totalPages;
//		this.lastPage = lastPage;
//	}
//	
//	public int getTotalPages() {
//		return totalPages;
//	}
//	public void setTotalPages(int totalPages) {
//		this.totalPages = totalPages;
//	}
//	public List<OrderInfo> getAllContent() {
//		return allContent;
//	}
//	public void setAllContent(List<OrderInfo> allContent) {
//		this.allContent = allContent;
//	}
//	public int getPageNumber() {
//		return pageNumber;
//	}
//	public void setPageNumber(int pageNumber) {
//		this.pageNumber = pageNumber;
//	}
//	public int getPageSize() {
//		return pageSize;
//	}
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//	public long getTotalElements() {
//		return totalElements;
//	}
//	public void setTotalElements(long totalElements) {
//		this.totalElements = totalElements;
//	}
//	public boolean isLastPage() {
//		return lastPage;
//	}
//	public void setLastPage(boolean lastPage) {
//		this.lastPage = lastPage;
//	}
//}
