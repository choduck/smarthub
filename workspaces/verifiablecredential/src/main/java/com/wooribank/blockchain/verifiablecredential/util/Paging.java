package com.wooribank.blockchain.verifiablecredential.util;

import java.io.Serializable;

public class Paging implements Serializable{
	private static final long serialVersionUID = 7072279631443427057L;
	
	private int currentPage;
	private int totalCount;
	private int totalPage;
	private int startPage;
	private int endPage;
	private int articleCount = 10;		// 목록 당 게시물 갯수
	private int pageCount = 10;		// 한 화면에 보여질 페이지 수
	private int rowNum;
	
	public int getCurrentPage() {
		if( currentPage == 0 ){
			return 1;
		}
		else{
			return currentPage;
		}
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		
		//-- 게시물 총 갯수가 등록되는 시점에 전체 페이지수를 등록
		if( totalCount < this.articleCount ){
			this.totalPage = 1;
		}
		else if( totalCount%this.articleCount==0 ){
			this.totalPage = totalCount/this.articleCount;
		}
		else{
			this.totalPage = totalCount/this.articleCount+1;
		}
		
		//-- 한 화면에 페이지 노출 갯수를 기준으로 startPage, endPage 설정
		if(totalPage<=pageCount){
			startPage = 1;
			endPage = totalPage;
		}
		else{
			startPage = (getCurrentPage()-1) / pageCount * pageCount + 1;
			endPage = startPage + pageCount -1;
			if(endPage>totalPage){
				endPage = totalPage;
			}
		}
		
		this.totalCount = totalCount;
		
		rowNum = this.totalCount - (getCurrentPage()-1)*this.articleCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	
	//-- SQL 에서 사용할 시작, 끝번호 계산
	public int getBeginArticleNum(){
		//return (this.getCurrentPage()-1) * this.articleCount + 1;  // oracle
		return (this.getCurrentPage()-1) * this.articleCount;  //mysql
	}
	public int getEndArticleNum(){
		return this.getCurrentPage() * this.articleCount;
	}
	
	//-- view에서  사용할 이전,다음,처음,마지막 페이지 번호
	public int getPrevPage(){
		if( this.getCurrentPage()==1 ){
			return -1;
		}
		else{
			//return (getCurrentPage()-pageCount-1) / pageCount * pageCount + 1;
			return getCurrentPage()-1;
		}
	}
	public int getNextPage(){
		if( this.getCurrentPage()==this.getTotalPage() ){
			return -2;
		}
		else{
			return getCurrentPage() + 1;
			/*int nextPage = (getCurrentPage()+pageCount-1) / pageCount * pageCount + 1;
			if( nextPage > totalPage ){
				nextPage = totalPage;
			}
			return nextPage;*/
		}
	}
	public int getFirstPage(){
		if( this.getCurrentPage()==1 ){
			return -1;
		}
		else{
			return 1;
		}
	}
	public int getLastPage(){
		if( this.getCurrentPage()==this.getTotalPage() ){
			return -2;
		}
		else{
			return this.getTotalPage();
		}
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
 
}
