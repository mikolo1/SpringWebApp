<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/incl/taglibs.app"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file = "/WEB-INF/incl/stylelinks.app"%> 
<title><s:message code="error.errorPage" /></title>
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<div align="center" style="margin-top:20px;">
		<img src="/resources/images/stamperror.jpg">

		<h2>
			<s:message code="error.defaultErrorMessage" />
		</h2>
	</div>
</body>
</html>