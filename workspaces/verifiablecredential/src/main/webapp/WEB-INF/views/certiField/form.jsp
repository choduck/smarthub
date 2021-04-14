<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp"  %>
<script type="text/javascript">
//검색 문자열
var maxCount = 1;	//현재 그룹 rownum

$(function(){
	if (Number("${fieldTotalCount}") > 0) maxCount = Number("${fieldTotalCount}");
	
	// 폼 설정
	commform = new $Form("certiField");
	commform.number("orderNo"+maxCount, "순서", {require : false});
	
	// 필드 추가
	$("#btn_fieldAdd").click(function(){
        fnGoFieldAdd();
    });
	
	// 저장
	$("#btn_regist").click(function(){
        fnGoRegist();
    });
	
	// 수정
    $("#btn_update").click(fnGoUpdate);
	
	// 목록
	$("#btn_list").click(function(){
        fnGoList();
    });
	
	// 숫자만 입력
	$("#orderNo"+maxCount).on("keyup",function(event){
		var thisObj = $(this);
		thisObj.css("imeMode","disabled");
		var value = thisObj.val().match(/[^0-9]/g);
		if(value!=null) {
			thisObj.val(thisObj.val().replace(/[^0-9]/g,''));
		}
	});
	
	// 영문만 입력
	$("#dataTp"+maxCount).on("keyup",function(event){
		var thisObj = $(this);
		thisObj.css("imeMode","disabled");
		var value = thisObj.val().match(/[^A-Za-z]/g);
		if(value!=null) {
			thisObj.val(thisObj.val().replace(/[^A-Za-z]/g,''));
		}
	});
	
	$("#desctn"+maxCount).css("imeMode","active");
});

function validate() {
	if ( $("#issuedPlaceCd").val() == "" ) {
		alert("발급처를 선택해 주시기 바랍니다.");
		$("#issuedPlaceCd").focus().select();
		return false;
	}

	if ( $("#certiCd").val() == "" ) {
		alert("자격명칭을 선택해 주시기 바랍니다.");
		$("#certiCd").focus().select();
		return false;
	}
	
	return true;
}

function fnGoFieldAdd() {
	if ( validate() ) {
		maxCount++;
		
		$.ajax({
			url:"/certiField/ajax/certiFieldForm.do",
			data:"maxCount="+ maxCount,
			type:"GET",
			dataType:"text",
		    cache:false,
		    success:function(data){
		    	$("#divCertiField").append(data);
		    	fnGoFieldNoCheck();
		    },
		    error:function(xhr, textStatus) {
				alert(textStatus + " : " + xhr.status + " 오류가 발생했습니다.");
			}
		    
		});
	}
}

function fnGoDivDel(currentLow) {
	//크롬에서 작동/ IE에서 에러발생
	//$("#div_row_"+currentLow).remove();
	
	var div = document.getElementById("div_row_"+currentLow);
	div.parentNode.removeChild(div);

	fnGoFieldNoCheck();
}

//그룹번호 다시 매김
function fnGoFieldNoCheck(){
	var cnt = 0;
	$("div[id^='div_row_']").each(function(){
		cnt++;
	});	
}

function fnGoList() {
	location.href = "/certiField/list.do";
}

function validate() {
	if ( $("#issuedPlaceCd").val() == "" ) {
		alert("발급처를 선택해 주시기 바랍니다.");
		$("#issuedPlaceCd").focus().select();
		return false;
	}

	if ( $("#certiCd").val() == "" ) {
		alert("자격명칭을 선택해 주시기 바랍니다.");
		$("#certiCd").focus().select();
		return false;
	}
	
	for (var i=1; i <= maxCount; i++) {
		if ( $("#orderNo"+i).val() == "" ) {
			alert("그룹"+i+"의 순서를 입력해 주시기 바랍니다. ");
			$("#orderNo"+i).focus().select();
			return false;
		}
		
		if ( $("#fieldNm"+i).val() == "" ) {
			alert("그룹"+i+"의 필드명(영문)을 입력해 주시기 바랍니다. ");
			$("#fieldNm"+i).focus().select();
			return false;
		}
		
		if ( $("#fieldKorNm"+i).val() == "" ) {
			alert("그룹"+i+"의 필드명(한글)을 입력해 주시기 바랍니다. ");
			$("#fieldNm"+i).focus().select();
			return false;
		}
		
		
		if ( $("#dataTp"+i).val() == "" ) {
			alert("그룹"+i+"의 데이터타입을 입력해 주시기 바랍니다. ");
			$("#dataTp"+i).focus().select();
			return false;
		}
		
		if ( $("#desctn"+i).val() == "" ) {
			alert("그룹"+i+"의 디스크립션을 입력해 주시기 바랍니다. ");
			$("#desctn"+i).focus().select();
			return false;
		}
	}

	return true;
}

// 저장
function fnGoRegist() {
	if ( validate() ) {
		if ( confirm("등록 하시겠습니까?") ) {
			commform.setAction("/certiField/insert.do");
			commform.setMethod("POST");
			commform.submit();
		}
	}
}

function fnGoUpdate() {
	if ( validate() ) {
		if ( confirm("수정 하시겠습니까?") ) {
			commform.setAction("/certiField/update.do");
			commform.setMethod("POST");
			commform.submit();
		}
	}
}

//삭제
function fnGoDelete(seqNo) {
	if(confirm("정말로 삭제하시겠습니까?")) {
		
		commform.setAction("/certiField/delete.do");
		commform.setMethod("GET");
		commform.submit();
	}
}
</script>

 <body>
 	<form:form commandName="certiField" name="frm">
 	<form:hidden path="seqNo" />
				 <%@ include file="../common/left_menu.jsp" %>
            </div>
            <div class="col-10 p-3">
                <div class="row">
                    <div class="col-6">
                        <h4 class="page-title">자격필드등록</h4>
                    </div>
                    <div class="col-6 text-right">
                        <!-- <button class="btn btn-sm btn-primary">신규등록</button> -->
                    </div>
                </div>
                <div class="row">
                    <div class="col-6">
                        <table class="table table-sm table-bordered table-striped">
                        	<tbody>
                           		<tr>
                                    <td class="bg-dark text-light text-center">발급처</td>
                                    <td>
                                        <form:select path="issuedPlaceCd" id="issuedPlaceCd">
                                            <form:option value="" selected="selected">선택</form:option>
                                            <c:forEach var="codeList" items="${cdIssuedPlaceCdList}">
												<form:option value="${codeList.cd}" > 
													<c:out value="${codeList.cdNm}"/>
												</form:option>
											</c:forEach>
                                        </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="bg-dark text-light text-center">자격명칭</td>
                                    <td>
                                        <form:select path="certiCd" id="certiCd">
                                            <form:option value="" selected="selected">선택</form:option>
                                            <c:forEach var="codeList" items="${cdCertiCdList}">
												<form:option value="${codeList.cd}" >
													<c:out value="${codeList.cdNm}"/>
												</form:option>
											</c:forEach>
                                        </form:select>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <div class="col-6">
                        <h4 class="page-title">상세정보</h4>
                    </div>
                    <div class="col-6 text-right">
                        <a href="#" onClick="javascript:fnGoFieldAdd();" class="btn btn-sm btn-info">필드추가</a>
                        
                        <c:choose>
							<c:when test="${param.seqNo == '0'}">
                				<a href="#" id="btn_regist" class="btn btn-sm btn-primary">저장</a>
		                	</c:when>
		            		<c:otherwise>
            					<a href="#" id="btn_update" class="btn btn-sm btn-primary">수정</a>
            				</c:otherwise>
						</c:choose>
                        
                        <a href="#" id="btn_list" class="btn btn-sm btn-secondary">목록</a>
                    </div>
                </div>
                <c:if test="${empty fieldList}">
                <div class="row" id="div_row_1">
                    <div class="col">
                        <table class="table table-sm table-bordered table-striped text-center">
                            <tbody>
                                <tr>
                                    <td class="bg-dark text-light align-middle">순서</td>
                                    <td>
                                        <input type="text" id="orderNo1" name="fieldList[0].orderNo" class="form-control" style="width:50px" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onChange="this.value=this.value.replace(/[^0-9]/g,'')">
                                    </td>
                                    <td rowspan="5" class="align-middle">
                                        <a href="#" onClick="javascript:fnGoDivDel('1');" class="btn btn-sm btn-danger"><i class="fa fa-trash-alt fa-fw"></i>삭제</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="bg-dark text-light align-middle">필드명(영문)</td>
                                    <td>
                                        <input type="text" id="fieldNm1" name="fieldList[0].fieldNm" class="form-control w-100" style="width:150px;" maxlength="50" onkeyup="this.value=this.value.replace(/[^A-Za-z]/g,'')" onChange="this.value=this.value.replace(/[^A-Za-z]/g,'')">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="bg-dark text-light align-middle">필드명(한글)</td>
                                    <td>
                                        <input type="text" id="fieldNm1" name="fieldList[0].fieldKorNm" class="form-control w-100" style="width:150px;" maxlength="50">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="bg-dark text-light align-middle">데이터 타입</td>
                                    <td>
                                        <input type="text" id="dataTp1" name="fieldList[0].dataTp" class="form-control" style="width:150px;" maxlength="10" onkeyup="this.value=this.value.replace(/[^A-Za-z]/g,'')" onChange="this.value=this.value.replace(/[^A-Za-z]/g,'')">
                                    </td>
                                </tr>
								<tr>
                                    <td class="bg-dark text-light align-middle">디스크립션</td>
                                    <td>
                                        <input type="text" id="desctn1" name="fieldList[0].desctn" class="form-control w-100">
                                    </td>
                                </tr>
							</tbody>
                        </table>
                    </div>
                </div>
                </c:if>
            <c:forEach var="field" items="${fieldList}" varStatus="status">
            <input type="hidden" name="seqNo" id="seqNo" value="${field.seqNo}" />
            	<div class="row" id="div_row_<c:out value='${status.index+1}' />">
                    <div class="col">
                        <table class="table table-sm table-bordered table-striped text-center">
                            <tbody>
                                <tr>
                                    <td class="bg-dark text-light align-middle">순서</td>
                                    <td>
                                        <input type="text" id="orderNo<c:out value='${status.index+1}' />" name="fieldList[<c:out value='${status.index}' />].orderNo" value="${field.orderNo}" class="form-control" style="width:50px" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onChange="this.value=this.value.replace(/[^0-9]/g,'')">
                                    </td>
                                    <td rowspan="5" class="align-middle">
                                        <a href="#" onClick="javascript:fnGoDivDel('<c:out value="${status.index+1}"/>');" class="btn btn-sm btn-danger"><i class="fa fa-trash-alt fa-fw"></i>삭제</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="bg-dark text-light align-middle">필드명(영문)</td>
                                    <td>
                                        <input type="text" id="fieldNm<c:out value='${status.index+1}' />" name="fieldList[<c:out value='${status.index}' />].fieldNm" value="${field.fieldNm}" class="form-control w-100" style="width:150px;" onkeyup="this.value=this.value.replace(/[^A-Za-z]/g,'')" onChange="this.value=this.value.replace(/[^A-Za-z]/g,'')">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="bg-dark text-light align-middle">필드명(한글)</td>
                                    <td>
                                        <input type="text" id="fieldKorNm<c:out value='${status.index+1}' />" name="fieldList[<c:out value='${status.index}' />].fieldKorNm" value="${field.fieldKorNm}" class="form-control w-100" style="width:150px;">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="bg-dark text-light align-middle">데이터 타입</td>
                                    <td>
                                        <input type="text" id="dataTp<c:out value='${status.index+1}' />" name="fieldList[<c:out value='${status.index}' />].dataTp" value="${field.dataTp}" class="form-control" style="width:150px;" maxlength="15" onkeyup="this.value=this.value.replace(/[^A-Za-z]/g,'')" onChange="this.value=this.value.replace(/[^A-Za-z]/g,'')">
                                    </td>
                                </tr>
								<tr>
                                    <td class="bg-dark text-light align-middle">디스크립션</td>
                                    <td>
                                        <input type="text" id="desctn<c:out value='${status.index+1}' />1" name="fieldList[<c:out value='${status.index}' />].desctn" value="${fn:escapeXml(field.desctn)}" class="form-control w-100">
                                    </td>
                                </tr>
							</tbody>
                        </table>
                    </div>
                </div>
            </c:forEach>
                <div id="divCertiField">
                
                </div>
            </div>
        </div>
    </div>
    </form:form>
</body>
 </html>
