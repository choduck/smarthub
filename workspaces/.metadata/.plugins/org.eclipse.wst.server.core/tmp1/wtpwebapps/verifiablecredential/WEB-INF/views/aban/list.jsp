<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp" %>
<script type="text/javascript">
//검색 문자열
var searchString = location.search.substring(1);
$(function(){
	commform = new $Form("aban");
});

// 조회
function fnGoList(page){
	if(page==-1){
		alert("첫페이지입니다.");
		return;
	}
	else if(page==-2){
		alert("마지막페이지입니다.");
		return;
	}
	commform.setValue("currentPage" ,page);
	commform.setAction("/aban/list.do");
	commform.setMethod("GET");
	commform.submit();
}
</script>

<body>
<form:form commandName="aban" name="frm" method="post" >
<form:hidden path="seqNo" />
	<%@ include file="/WEB-INF/views/common/left_menu.jsp" %>
		<div id="contents-area" class="container-fluid">
         <div class="row">
             <div class="col-2 collapse d-md-flex bg-gray pt-3" id="sidebar">
                 <nav class="nav-left">
                     <ul class="nav flex-column flex-nowr">
                       <li class="nav-item">
                         <a class="nav-link" href="/certiField/list.do">자격필드관리</a>
                       </li>
                       <li class="nav-item">
                         <a class="nav-link" href="/member/list.do">회원관리</a>
                       </li>
                       <li class="nav-item">
                         <a class="nav-link active" href="/aban/list.do">자격폐기관리</a>
                       </li>
                     </ul>
                 </nav>
             </div>
			<div class="col-10 p-3">
	             <div class="row">
	                 <div class="col-6">
	                     <h4 class="page-title">폐기목록</h4>
	                 </div>
	                 <div class="col-6 text-right">
	                     <!-- <button class="btn btn-sm btn-primary">회원등록</button> -->
	                 </div>
	             </div>
	             <div class="row">
	                 <div class="col">
	                     <table class="table table-sm table-bordered table-striped text-center">
	                         <thead class="bg-dark text-light">
	                             <tr>
	                                 <th>NO</th>
	                                 <th>이름</th>
	                                 <th>자격증명</th>
	                                 <th>CID</th>
	                             </tr>
	                         </thead>
	                         <tbody>
								<c:if test="${empty abanList}">
									<tr><td colspan="4">등록된 데이터가 없습니다.</td></tr>
								</c:if>
								<c:forEach var="board" items="${abanList}" varStatus="status">	                         		
	                             <tr>
	                                 <td><c:out value="${aban.rowNum-status.index}" /></td>
	                                 <td><c:out value="${board.mbNm}" /></td>
	                                 <td><c:out value="${board.certiNm}" /></td>
	                                 <td><c:out value="${board.abanDate}" /></td>
	                             </tr>
	                             </c:forEach>
	                         </tbody>
	                     </table>
	                 </div>
	             </div>
	             <div class="row">
	                 <div class="col">
	                     <paging:paging pagingObj="${aban}" />
	                 </div>
	             </div>
	         </div>
	     </div>
	 </div>
</form:form>     
 </body>
 </html>	 