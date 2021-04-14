<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp" %>
<script type="text/javascript">
//검색 문자열
$(function(){
	commform = new $Form("member");
	
	// 등록
	$("#btn_memberEntry").click(fnGoRegist);
});

// 등록
function fnGoRegist() {
	commform.setValue("seqNo", 0);
	commform.setAction("/member/form.do");
	commform.setMethod("GET");
	commform.submit();
}

// 수정
function fnGoModify(seqNo){
	commform.setValue("seqNo", seqNo);
	commform.setAction("/member/form.do");
	commform.setMethod("GET");
	commform.submit();
}

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
	commform.setValue("currentPage", page);
	commform.setAction("/member/list.do");
	commform.setMethod("GET");
	commform.submit();
}

// 폐기
function fnGoAban(seqNo){
	commform.setValue("seqNo", seqNo);
	commform.setAction("/member/deleteAbanMember.do");
	commform.setMethod("POST");
	commform.submit();
}
</script>

<body>
<form:form commandName="member" name="frm" method="post" >
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
                         <a class="nav-link active" href="/member/list.do">회원관리</a>
                       </li>
                       <li class="nav-item">
                         <a class="nav-link" href="/aban/list.do">자격폐기관리</a>
                       </li>
                     </ul>
                 </nav>
             </div>
			<div class="col-10 p-3">
                 <div class="row">
                     <div class="col-6">
                         <h4 class="page-title">회원관리</h4>
                     </div>
                     <div class="col-6 text-right">
                         <a href="#" id="btn_memberEntry" class="btn btn-sm btn-primary"><i class="fa fa-plus fa-fw"></i>회원등록</a>
                     </div>
                 </div>
                 <div class="row">
                     <div class="col">
                         <table class="table table-sm table-striped text-center">
                             <thead class="bg-dark text-light">
                                 <tr>
                                     <th>NO</th>
                                     <th>회원번호</th>
                                     <th>회원이름</th>
                                     <th>휴대폰 번호</th>
                                     <th>가입일</th>
                                     <th>관리 &middot; 폐기</th>
                                 </tr>
                             </thead>
                             <tbody>
                                <c:if test="${empty memberList}">
									<tr><td colspan="6">등록된 데이터가 없습니다.</td></tr>
								</c:if>
								<c:forEach var="board" items="${memberList}" varStatus="status">
	                                <tr>
	                                    <td><c:out value="${member.rowNum-status.index}" /></td>
	                                    <td><c:out value="${board.residNo}" />-*******</td>
	                                    <td><c:out value="${board.mbNm}" /></td>
	                                    <td><c:out value="${board.mobileNo}" /></td>
	                                    <td><c:out value="${board.joinDate}" /></td>
	                                    <td>
	                                        <a href="#" class="btn btn-sm btn-info" onClick="javascript:fnGoModify('<c:out value="${board.seqNo}" />');">수정</a>
	                                        <a href="#" class="btn btn-sm btn-danger" onClick="javascript:fnGoAban('<c:out value="${board.seqNo}" />');">폐기</a>
	                                    </td>
									</tr>
								</c:forEach>
                             </tbody>
                         </table>
                     </div>
                 </div>
                 <div class="row">
                     <div class="col">
                         <paging:paging pagingObj="${member}" />
                     </div>
                 </div>
             </div>
         </div>
     </div>
</form:form>     
 </body>
 </html>