<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp"  %>
<c:set var="arryNum" value="${maxCount-1}" />

<div class="row" id="div_row_<c:out value='${maxCount}'/>">
	<div class="col">
		<table class="table table-sm table-bordered table-striped text-center">
			<tbody>
				<tr>
	                <td class="bg-dark text-light align-middle">순서</td>
	                <td>
	                	<input type="text" id="orderNo<c:out value='${maxCount}'/>" name="fieldList[<c:out value='${arryNum}' />].orderNo" class="form-control" style="width:50px" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onChange="this.value=this.value.replace(/[^0-9]/g,'')">
	                </td>
	                <td rowspan="5" class="align-middle">
	                     <a href="#" onClick="javascript:fnGoDivDel('<c:out value='${maxCount}'/>');" class="btn btn-sm btn-danger"><i class="fa fa-trash-alt fa-fw"></i>삭제</a>
	                </td>
	            </tr>
	            <tr>
	                <td class="bg-dark text-light align-middle">필드명(영문)</td>
	                <td>
	                     <input type="text" id="fieldNm<c:out value='${maxCount}'/>" name="fieldList[<c:out value='${arryNum}' />].fieldNm" class="form-control w-100" style="width:150px;" maxlength="50" onkeyup="this.value=this.value.replace(/[^A-Za-z]/g,'')" onChange="this.value=this.value.replace(/[^A-Za-z]/g,'')">
					</td>
	            </tr>
	            <tr>
	                <td class="bg-dark text-light align-middle">필드명(한글)</td>
	                <td>
	                     <input type="text" id="fieldKorNm<c:out value='${maxCount}'/>" name="fieldList[<c:out value='${arryNum}' />].fieldKorNm" class="form-control w-100" maxlength="50" style="width:150px;">
					</td>
	            </tr>
				<tr>
	                 <td class="bg-dark text-light align-middle">데이터 타입</td>
	                 <td>
	                     <input type="text" id="dataTp<c:out value='${maxCount}'/>" name=fieldList[<c:out value='${arryNum}' />].dataTp class="form-control" style="width:150px;" maxlength="10" onkeyup="this.value=this.value.replace(/[^A-Za-z]/g,'')" onChange="this.value=this.value.replace(/[^A-Za-z]/g,'')">
	                 </td>
	             </tr>
				<tr>
	            	<td class="bg-dark text-light align-middle">디스크립션</td>
	                <td>
						<input type="text" id="desctn<c:out value='${maxCount}'/>" name="fieldList[<c:out value='${arryNum}' />].desctn" class="form-control w-100" maxlength="200">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>