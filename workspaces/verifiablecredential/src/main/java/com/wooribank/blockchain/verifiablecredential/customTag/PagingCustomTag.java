package com.wooribank.blockchain.verifiablecredential.customTag;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.wooribank.blockchain.verifiablecredential.util.Paging;

/**
 * @FileName  	: PagingCustomTag.java
 * @Description : 페이징 태그
 */
public class PagingCustomTag extends TagSupport{
	
	private static final long serialVersionUID = -1583715678320942107L;

	private Paging pagingObj;
	
    public Paging getPagingObj() {
		return pagingObj;
	}
	public void setPagingObj(Paging pagingObj) {
		this.pagingObj = pagingObj;
	}
	
	@Override
	public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

	@Override
	public int doEndTag() throws JspException {
        try{        	
        	StringBuffer tag = new StringBuffer();

        	tag.append("<nav aria-label=\"Page navigation example\">");
			tag.append("    <ul class=\"pagination pagination-border-none justify-content-end\">");
			
			tag.append("	     <li class=\"page-item\">");
			tag.append("		     <a href=\"#\" class=\"page-link\" aria-label=\"Previous\" onclick=\"fnGoList('").append(pagingObj.getPrevPage()).append("');return false;\" title=\"이전 페이지로 이동\"><span aria-hidden=\"true\">&laquo;</span></a>");
			tag.append("	     </li>");
			
        	for(int i = pagingObj.getStartPage(); i <= pagingObj.getEndPage(); i++){	
        		if(i == pagingObj.getCurrentPage()) {
        			tag.append("        <li class=\"page-item\"><a class=\"page-link active\" href=\"#\">").append(i).append("</a></li>");
        		}else{
        			tag.append("        <li class=\"page-item\"><a class=\"page-link\" href=\"#\" onclick=\"fnGoList('").append(i).append("');return false;\" title=\"").append(i).append("페이지로 이동\">").append( + i + "</a></li>");
        		}				
        	}			
        	
			tag.append("	     <li class=\"page-item\">");
			tag.append("		     <a href=\"#\" class=\"page-link\" aria-label=\"Next\" onclick=\"fnGoList('").append(pagingObj.getNextPage()).append("');return false;\" title=\"다음 페이지로 이동\"><span aria-hidden=\"true\">&raquo;</span></a>");
			tag.append("	     </li>");
			tag.append("    </ul>");        	
			tag.append("</nav>");        	

			pageContext.getOut().write( tag.toString() );    
            
        } catch ( Exception e ) {
            throw new JspException("IO Error : " + e.getMessage());
        }
        return EVAL_PAGE;
    }
	
}
