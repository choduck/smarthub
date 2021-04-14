<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Wini</title>
        <meta charset="utf-8">
        <%-- 
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
         --%>
    </head>
    <body>
        <div id="contents-area" class="container-fluid wini-back" style="width:1223px;">
            <div class="row">
                <div class="col-12">
                    <button type="button" class="btn btn-wini-mobile" data-toggle="modal" data-target="#exampleModalCenter">모바일 신분증 연동</button>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header custom-modal-header">
                        <h5 class="modal-title" id="exampleModalCenterTitle">모바일 신분증 연동</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid custom-container p-5">
                            <div class="row">
                                <div class="col">
                                    <ul class="nav nav-tabs">
                                        <li class="nav-item step1 active">1.발급처 선택<i></i></li>
                                        <li class="nav-item step2">2.신분증/자격증명 선택<i></i></li>
                                        <li class="nav-item step3">3.연동 정보 선택<i></i></li>
                                        <li class="nav-item step4">4.QR코드 생성<i></i></li>
                                    </ul>

                                    <div class="tab-content">
                                        <div id="step1" class="tab-pane fadein active">
                                            <table class="table table-bordered table-in-modal">
                                                <thead>
                                                    <tr>
                                                        <th></th>
                                                        <th>번호</th>
                                                        <th>발급처 목록</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                            <div class="text-center">
                                                <button type="button" class="btn btn-previous" data-dismiss="modal">닫기</button>
                                                <button type="button" class="btn btn-next" onclick="onStep2();">다음</button>
                                            </div>
                                        </div>
                                        <div id="step2" class="tab-pane fade">
                                            <table class="table table-bordered table-in-modal">
                                                <thead>
                                                    <tr>
                                                        <th></th>
                                                        <th>번호</th>
                                                        <th>신분자격증명목록</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                            <div class="text-center">
                                                <button class="btn btn-previous" onclick="onStep1();">이전</button>
                                                <button class="btn btn-next"  onclick="onStep3();">다음</button>
                                            </div>
                                        </div>
                                        <div id="step3" class="tab-pane fade">
                                            <table class="table table-bordered table-in-modal">
                                                <thead>
                                                    <tr>
                                                        <th>
                                                            <input type="checkbox" id="step3AllCheck" onclick="step3Check(this);">
                                                        </th>
                                                        <th>번호</th>
                                                        <th>연동 정보 목록</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                            <div class="text-center">
                                                <button class="btn btn-previous"  onclick="disConnect();onStep2();">이전</button>
                                                <button class="btn btn-next" onclick="onStep4();">다음</button>
                                            </div>
                                        </div>
                                        <div id="step4" class="tab-pane fade">
                                            <div class="container">
                                                <div class="row justify-content-md-center">
                                                    <div id="qr" style="text-align: center;">
                                                        <img src="/resources/images/QR/qr-code.png">
                                                    </div>
                                                    <div class="col-12">
                                                        <p class="text-center p-3">QR코드를 고객님 촬영하시면 연동이 완료됩니다.</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="text-center">
                                                <button class="btn btn-previous"  onclick="onStep3();">이전</button>
                                                <button type="button" class="btn btn-previous" data-dismiss="modal" onclick="location.reload();">닫기</button>
                                                <button class="btn btn-next" data-toggle="tab" href="#step5" onclick="onStep5();">다음</button>
                                            </div>
                                        </div>
                                        <div id="step5" class="tab-pane fade">
                                            <div class="container">
                                                <div class="row">
                                                    <div class="col-12 text-center">
                                                        <img src="/resources/images/20160906191209779wvth.jpg" style="height:162px; border:3px solid #ddd;" class="mt-5 mb-3">
                                                        <h6 class="text-center p-3" style="font-size:18px;"><span class="text-primary">김우리 고객님</span>의 모바일 신분증 인증정보가<br>연동되었습니다.</h6>
                                                    </div>
                                                    <div class="col-12">
                                                        <table class="table table-bordered table-in-modal-2">
                                                            <tbody>
                                                                <tr>
                                                                    <th>구분</th>
                                                                    <td>
                                                                        주민등록증
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <th>한글이름</th>
                                                                    <td>
                                                                        김우리
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <th>주민등록번호</th>
                                                                    <td>
                                                                        900406-1742933
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <th>주소</th>
                                                                    <td>
                                                                        서울시 중구 소공로 51
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <th>발급일</th>
                                                                    <td>
                                                                        2018.01.02
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <th>발급처</th>
                                                                    <td>
                                                                        서울특별시 중구구청장
                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="text-center">
                                                <button type="button" class="btn btn-previous" data-dismiss="modal" onclick="location.reload();">닫기</button>
                                                <button type="button" class="btn btn-start-counsel" data-dismiss="modal" onclick="location.reload();">상담시작</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <form id="commonCode" name="commonCode" action="" method="POST" onsubmit="return false;" >
        	<input type="hidden" id="cd" name="cd" value="" />
        	<input type="hidden" id="uppCd" name="uppCd" value="" />
        	<input type="hidden" id="cdNm" name="cdNm" value="" />
        	<input type="hidden" id="level" name="level" value="" />
        	<input type="hidden" id="orderNo" name="orderNo" value="" />
        	<input type="hidden" id="useFl" name="useFl" value="" />
        	<input type="hidden" id="issuedPlaceCd" name="issuedPlaceCd" value="" />
        	<input type="hidden" id="certiCd" name="certiCd" value="" />
        	<input type="hidden" id="sid" value="<%=request.getSession().getAttribute("sid") %>" />
        </form> 
    </body>
 </html>
