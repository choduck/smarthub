<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="paging" uri="/WEB-INF/tlds/paging.tld"%>
<c:set var="today" value="<%=new java.util.Date()%>" />
<!DOCTYPE html>
<html>
 <head>
     <title>블럭체인 자격확인 관리자</title>
     <meta charset="utf-8">
     <link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/bootstrap.css">
     <link rel="stylesheet" type="text/css" href="/resources/css/common.css">
     <script type="text/javascript" src="/resources/js/jQuery-3.3.1.min.js"></script>
     <script type="text/javascript" src="/resources/bootstrap/js/bootstrap.js"></script>
     <script type="text/javascript" src="/resources/js/common.js"></script>
     <script type="text/javascript" src="/resources/js/jqt.util.js"></script>
	<script type="text/javascript" src="/resources/js/jqt.validate.js"></script>
	<script type="text/javascript" src="/resources/js/jqt.form.js"></script>
 </head>