<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp" %>
<script type="text/javascript">
//검색 문자열
$(function() {
	// 폼 설정
	commform = new $Form("certiList");
	
	// 등록
	$("#btn_regist").click(function(){
        fnGoRegist();
    });
});

// 등록
function fnGoRegist() {
	commform.setValue("seqNo", 0);
	commform.setAction("/certiField/form.do");
	commform.setMethod("GET");
	commform.submit();
}

// 수정
function fnGoModify(seqNo) {
	commform.setValue("seqNo", seqNo);
	commform.setAction("/certiField/form.do");
	commform.setMethod("GET");
	commform.submit();
}

//삭제
function fnGoDelete(seqNo) {
	if(confirm("삭제하시겠습니까?")) {
		commform.setValue("seqNo", seqNo);
		commform.setAction("/certiField/deleteCertiListField.do"  );
		commform.setMethod("POST");
		commform.submit();
	}
}

//조회
function fnGoList(page) {
	if(page==-1){
		alert("첫페이지입니다.");
		return;
	}
	else if(page==-2){
		alert("마지막페이지입니다.");
		return;
	}
	
	commform.setValue("currentPage",page);
	commform.setAction("/certiField/list.do");
	commform.setMethod("GET");
	commform.submit();
}
</script>

<body>
<form:form commandName="certiList" name="frm" method="post" >
<form:hidden path="currentPage" />
<form:hidden path="seqNo" />
	<%@ include file="../common/left_menu.jsp" %>
		<div id="contents-area" class="container-fluid">
         <div class="row">
             <div class="col-2 collapse d-md-flex bg-gray pt-3" id="sidebar">
                 <nav class="nav-left">
                     <ul class="nav flex-column flex-nowr">
                       <li class="nav-item">
                         <a class="nav-link active" href="/certiField/list.do">자격필드관리</a>
                       </li>
                       <li class="nav-item">
                         <a class="nav-link" href="/member/list.do">회원관리</a>
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
                    	<h4 class="page-title">자격필드관리</h4>
                    </div>
                    <div class="col-6 text-right">
                    	<a href="#" id="btn_regist" class="btn btn-sm btn-primary">신규등록</a>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <table class="table table-sm table-striped text-center">
                            <thead class="bg-dark text-light">
                                <tr>
                                    <th>NO</th>
									<th>발급처</th>
                                    <th>자격증명</th>
                                    <th>수정 &middot; 삭제</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:if test="${empty certiArrayList}">
									<tr><td colspan="4">등록된 데이터가 없습니다.</td></tr>
								</c:if>
                            	<c:forEach var="board" items="${certiArrayList}" varStatus="status">
								<tr>
									<td><c:out value="${board.seqNo}" /></td>
                                	<td><c:out value="${board.issuedPlaceNm}" /></td>
                                	<td><c:out value="${board.certiNm}" /></td>
                                	<td>
                                		<a href="#" class="btn btn-sm btn-info" onclick="fnGoModify('<c:out value="${board.seqNo}" />');">수정</a>
                                    	<a href="#" class="btn btn-sm btn-danger" onclick="fnGoDelete('<c:out value="${board.seqNo}" />');">삭제</a>
                                	</td>
                                </tr>
                                </c:forEach>
							</tbody>
                        </table>
                    </div>
                </div>
                <div class="row">
	                 <div class="col">
	                     <paging:paging pagingObj="${certiList}" />
	                 </div>
	             </div>
            </div>
        </div>
    </div>
</form:form>
</body>
 </html>