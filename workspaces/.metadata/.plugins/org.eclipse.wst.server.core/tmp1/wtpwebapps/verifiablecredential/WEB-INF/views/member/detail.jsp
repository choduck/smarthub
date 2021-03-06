<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp" %>
<script type="text/javascript">
$(function(){
	commform = new $Form("member");
	
	commform.require("mbNm", "회원이름", {message:"회원이름을 입력해 주시기 바랍니다."	});
	commform.require("residNo", "주민등록번호", {message:"주민등록번호를 입력해 주시기 바랍니다."	});
	commform.require("mobileNo", "휴대전화번호", {message:"휴대전화번호를 입력해 주시기 바랍니다."	});
	commform.require("joinDate", "가입일", {message:"가입일을 입력해 주시기 바랍니다."	});

	// 저장
	$("#btn_memRegist").click(fnGoMemRegist);
	
	// 수정
    $("#btn_memUpdate").click(fnGoMemUpdate);
	
	 // 목록
    $("#btn_list").click(fnGoList);
	 
 	// 자격증 정보 저장
	$("#btn_certiFieldRegist").click(fnGoCertiFieldRegist);
 	
	// 자격증명칭 추가
	$("#btn_certiFieldAdd").click(fnGoCertiFieldAdd);
});
	
// 저장
function fnGoMemRegist() {
	if ( commform.validate() ) {
		if ( confirm("회원 정보를 등록 하시겠습니까?") ) {
			var dataString = "mbNm="+$("#mbNm").val()+"&residNo="+$("#residNo").val().replace(/\-/g,"")+"&mobileNo="+$("#mobileNo").val().replace(/\-/g,"")+"&joinDate="+$("#joinDate").val();
			$.ajax({
				url : "/member/insert.do",
				data : dataString,
				type : "POST",
				dataType : "text",
			    cache : false,
			    processData : false, 
			    success : function(data){
			    	var obj = JSON.parse(data);
					var resultCode = obj.resultCode;

			    	if (resultCode == "success") {
						alert("회원 정보가 등록 되었습니다.");
						
						$("#seqNo").val(obj.seqNo);
					} else {
						alert(obj.resultMsg);
					}
			    },
			    error: function(request, status, error){
					alert(error);
				}
	        });
		}
	}
}

// 수정
function fnGoMemUpdate() {
	if ( commform.validate() ) {
		if ( confirm("회원 정보를 수정 하시겠습니까?") ) {
			var dataString = "mbNm="+$("#mbNm").val()+"&residNo="+$("#residNo").val().replace(/\-/g,"")+"&mobileNo="+$("#mobileNo").val().replace(/\-/g,"")+"&joinDate="+$("#joinDate").val();
			$.ajax({
				url : "/member/update.do",
				data : dataString,
				type : "POST",
				dataType : "text",
			    cache : false,
			    processData : false, 
			    success : function(data){
			    	var obj = JSON.parse(data);
					var resultCode = obj.resultCode;

			    	if (resultCode == "success") {
						alert("회원 정보가 수정 되었습니다.");
					} else {
						alert(obj.resultMsg);
					}
			    },
			    error: function(request, status, error){
					alert(error);
				}
	        });
		}
	}
}

function validate() {
	if ( $("#seqNo").val() == "0" ) {
		alert("회원정보를 먼저 등록해 주시기 바랍니다.");
		$("#mbNm").focus().select();
		return false;
	}
	
	if( $("#"+$("#issuedPlaceCd").val()+"_"+$("#certiCd").val()).length > 0) {
		alert("자격증 정보가 이미 추가 되었습니다.");
		return false
	}
	
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

// 자격명칭 추가
function fnGoCertiFieldAdd() {
	if ( validate() ) {
		var dataString = "issuedPlaceCd="+commform.getValue("issuedPlaceCd")+"&certiCd="+$("#certiCd").val();
		
		$.ajax({
			url : "/member/certiFieldList.do",
			data : dataString,
			type : "POST",
			dataType : "text",
		    success : function(data){
		    	console.log(data)
		    	
		    	var obj = JSON.parse(data);
				
		    	if (obj.resultCode == "success") {
		    		if (obj.rowCount >0) {
		    			fnGoMakeCertiField(obj);
		    		} else {
		    			alert("등록된 자격증 정보가 없습니다.");
		    			return;
		    		}
				} else {
					alert(obj.resultMsg);
				}
		    },
		    error: function(request, status, error){ 
				alert(error);
			}
        });
	}
}

function fnGoDivDel(currentId) {
	$("#"+currentId).remove();
}

function fnGoMakeCertiField(obj) {
	var inHtml = "";	//추가 TAG
	
	inHtml +="\n<div id=\""+obj.fieldList[0].issuedPlaceCd+"_"+obj.fieldList[0].certiCd+"\">";
	inHtml +="\n    <div class=\"row\">";
	inHtml +="\n        <div class=\"col-6\">";
	inHtml +="\n            <h4 class=\"page-title\">"+obj.fieldList[0].certiNm+" 정보입력</h4>";
	inHtml +="\n        </div>";
	inHtml +="\n        <div class=\"col-6 text-right\">";
	inHtml +="\n            <a href=\"#\" id=\"\" onClick=\"javascript:fnGoDivDel('"+obj.fieldList[0].issuedPlaceCd+"_"+obj.fieldList[0].certiCd+"');\" class=\"btn btn-sm btn-danger\"><i class=\"fa fa-trash-alt fa-fw\"></i>삭제</a>";
	inHtml +="\n        </div>";
	inHtml +="\n    </div>";
	inHtml +="\n     <div class=\"row\">";
	inHtml +="\n         <div class=\"col\">";
	inHtml +="\n            <table class=\"table table-sm table-bordered table-striped text-center\">";
	inHtml +="\n                <thead class=\"bg-dark text-light\">";
	inHtml +="\n                    <tr>";
	inHtml +="\n                        <th>필드명</th>";
	inHtml +="\n                        <th>내용</th>";
	inHtml +="\n                    </tr>";
	inHtml +="\n                </thead>";
	inHtml +="\n                <tbody>";
	
	for (var i=0; i<Number(obj.rowCount); i++) {
		inHtml +="\n                <tr>";
		inHtml +="\n                    <td>"+obj.fieldList[i].fieldKorNm+"</td>";
		inHtml +="\n                    <td>";
		inHtml +="\n                        <input type=\"hidden\" id=\"rowCount\" name=\"rowCount\" value="+ Number(obj.rowCount) +">";
		inHtml +="\n                        <input type=\"hidden\" id=\"residNo\" name=\"arryResidNo\" value="+ $("#residNo").val() +">";
		inHtml +="\n                        <input type=\"hidden\" id=\"issuedPlaceCd\" name=\"arryIssuedPlaceCd\" value="+ $("#issuedPlaceCd").val() +">";
		inHtml +="\n                        <input type=\"hidden\" id=\"certiCd\" name=\"arryCertiCd\" value="+ $("#certiCd").val() +">";
		inHtml +="\n                        <input type=\"hidden\" id=\"arryFeldNm"+i+"\" name=\"arryFieldNm\" value="+ obj.fieldList[i].fieldNm +">";
		inHtml +="\n                        <input type=\"hidden\" id=\"arryFieldKorNm"+i+"\" name=\"arryFieldKorNm\" value=" + obj.fieldList[i].fieldKorNm +">";
		inHtml +="\n                        <input type=\"text\" id=\"arryFieldCnt"+i+"\" name=\"arryFieldCnt\" class=\"form-control\">";
		inHtml +="\n                    </td>";
		inHtml +="\n                </tr>";
	}
	inHtml +="\n                </tbody>";
	inHtml +="\n            </table>";
	inHtml +="\n        </div>";
	inHtml +="\n    </div>";
	inHtml +="\n</div>";
	
	$("#div_field").append(inHtml);
}

function saveValidate() {
	
	if ( $("#seqNo").val() == "0" ) {
		alert("회원정보를 먼저 등록해 주시기 바랍니다.");
		$("#mbNo").focus().select();
		return false;
	}
	
	return true;
}

// 자격증 정보 저장
function fnGoCertiFieldRegist() {
	if ( saveValidate() ) {
		if ( confirm("저장 하시겠습니까?") ) {
			commform.setAction("/member/insertCertiField.do");
			commform.setMethod("POST");
			commform.submit();
		}
	}
}

// 삭제
function fnGoDelete(mbNo, certiCd) {
	if(confirm("삭제하시겠습니까?")) {
		commform.setValue("seqNo", seqNo);
		commform.setValue("certiCd", certiCd);
		commform.setAction("/member/deleteMemberCertiField.do");
		commform.setMethod("POST");
		commform.submit();
	}
}

// 삭제
function fnGoMemCeFieldDel(issuedPlaceCd, certiCd) {
	if ( confirm("삭제하시겠습니까?") ) {
		var dataString = "residNo="+commform.getValue("residNo")+"&issuedPlaceCd="+issuedPlaceCd+"&certiCd="+certiCd;
		$.ajax({
			url : "/member/deletememberCertiField.do",
			data : dataString,
			type : "POST",
			dataType : "text",
		    success : function(data){
		    	console.log(data)
		    	
		    	var obj = JSON.parse(data);
				var resultCode = obj.resultCode;
				
		    	if (resultCode == "success") {
		    		$("#"+issuedPlaceCd+"_"+certiCd).remove();
				} else {
					alert(obj.resultMsg);
				}
		    },
		    error: function(request, status, error){ 
				alert(error);
			}
        });
	}
}

function fnGoList() {
	location.href="/member/list.do"
}
</script>

<body>
<form:form commandName="member" name="frm">

<form:hidden path="seqNo" id="seqNo" />
<body>
     <%@ include file="/WEB-INF/views/common/left_menu.jsp" %>
             <div class="col-10 p-3">
                 <div class="row">
                     <div class="col-6">
                         <h4 class="page-title">회원정보</h4>
                     </div>
<!-- 
                     <div class="col-6 text-right">
                     	<c:choose>
							<c:when test="${param.seqNo == '0'}"> 
		                		<a href="#" id="btn_memRegist" class="btn btn-sm btn-primary">저장</a>
		                	</c:when>
		            		<c:otherwise>
		            			<a href="#" id="btn_memUpdate" class="btn btn-sm btn-info">수정</a>
		            		</c:otherwise>
						</c:choose>
                     </div>
-->
                 </div>
                 <div class="row mb-5">
                     <div class="col">
                         <table class="table table-sm table-bordered table-striped">
                             <tbody>
                                 <tr>
                                     <td class="bg-dark text-light text-center">회원이름</td>
                                     <td>
                                         <form:input path="mbNm" id="mbNm" class="form-control w-100" disabled="true" /> 
                                     </td>
                                     <td class="bg-dark text-light text-center">주민등록번호</td>
                                     <td>
                                         <form:input path="residNo" id="residNo" class="form-control w-100" maxlength="13" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onChange="this.value=this.value.replace(/[^0-9]/g,'')" disabled="true" />  
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="bg-dark text-light text-center">휴대전화번호</td>
                                     <td>
                                         <form:input path="mobileNo" id="mobileNo" class="form-control w-100" maxlength="11" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onChange="this.value=this.value.replace(/[^0-9]/g,'')" disabled="true"/>
                                     </td>
                                     <td class="bg-dark text-light text-center">가입일</td>
                                     <td colspan="3">
                                         <form:input path="joinDate" id="joinDate" class="form-control" maxlength="8" style="width:150px;" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onChange="this.value=this.value.replace(/[^0-9]/g,'')" disabled="true"/>
                                     </td>  
                                 </tr>
                             </tbody>
                         </table>
                     </div>
                     
                 </div>

<!-- 
                 <div class="row">
                     <div class="col-6">
                         <h4 class="page-title">자격증명 정보</h4>
                     </div>
                     <div class="col-6 text-right">
              			<a href="#" id="btn_certiFieldRegist" class="btn btn-sm btn-primary">저장</a>
                     </div>
                 </div>
                 <div class="row mb-5">
                     <div class="col">
                         <table class="table table-sm table-bordered table-striped">
                             <tbody>
                                 <tr>
                                     <td class="bg-dark text-light text-center">발급처</td>
                                     <td>
                                         <form:input path="certiNm" id="certiNm" class="form-control w-100" /> 
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="bg-dark text-light text-center">자격명칭</td>
                                     <td>
                                         <form:input path="mobileNo" id="mobileNo" class="form-control w-100" />                                     </td>
                                 </tr>
                             </tbody>
                         </table>
                     </div>
                 </div>
  -->                
                 <div id="div_field"></div>
                 
                 <c:forEach var="memberCertiFieldGrp" items="${memberCertiFieldGrpList}" varStatus="status">
                 <div id='<c:out value="${memberCertiFieldGrp.issuedPlaceCd}_${memberCertiFieldGrp.certiCd}"/>'>
				 	<div class="row">
						<div class="col-6">
							<h4 class="page-title"><c:out value="${memberCertiFieldGrp.certiNm}"/> 정보입력</h4>
                    	</div>
<!-- 
                    	<div class="col-6 text-right">
                        	<a href="#" onClick="javascript:fnGoMemCeFieldDel('<c:out value="${memberCertiFieldGrp.issuedPlaceCd}"/>', '<c:out value="${memberCertiFieldGrp.certiCd}"/>');" class="btn btn-sm btn-danger"><i class="fa fa-trash-alt fa-fw"></i>삭제</a>
                    	</div>
-->
                 	</div>
                    <div class="row">
                        <div class="col">
                            <table class="table table-sm table-bordered table-striped text-center">
                                <thead class="bg-dark text-light">
                                    <tr>
                                        <th>필드명</th>
                                        <th>내용</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach var="memberCertiField" items="${memberCertiFieldList}" varStatus="idx">
                                	<c:if test="${memberCertiFieldGrp.certiCd == memberCertiField.certiCd}">
                                    <tr>
                                        <td><c:out value='${memberCertiField.fieldKorNm}' /></td>
                                        <td>
                                            <input type="hidden" name="arryResidNo" value="<c:out value='${memberCertiField.residNo}' />">
											<input type="hidden" name="arryIssuedPlaceCd" value="<c:out value='${memberCertiField.issuedPlaceCd}' />">
											<input type="hidden" name="arryCertiCd" value="<c:out value='${memberCertiField.certiCd}' />">
											<input type="hidden" name="arryFieldNm" value="<c:out value='${memberCertiField.fieldNm}' />">
											<input type="hidden" name="arryFieldKorNm" value="<c:out value='${memberCertiField.fieldKorNm}' />">
											<input type="text" name="arryFieldCnt" class="form-control" value="<c:out value='${memberCertiField.fieldCnt}' />" disabled="true">
                                        </td>
                                    </tr>
                                    </c:if>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                </c:forEach>
                    
                 <div class="row">
                     <div class="col p-4 text-center">
                         <a href="#" id="btn_list" class="btn btn-primary"><i class="fa fa-list fa-fw"></i>목록</a>
                     </div>
                 </div>
             </div>
         </div>
     </div>
</form:form>        
 </body>
 </html>