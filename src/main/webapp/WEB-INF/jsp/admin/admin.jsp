<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/incl/taglibs.app"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file = "/WEB-INF/incl/stylelinks.app"%> 
<title><s:message code = "menu.adminPage"/></title>
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<h2>
		<b>
			<s:message code = "menu.adminPage"/>
		</b>	
	</h2>
	<%@include file="/WEB-INF/incl/admmenu.app"%>
		<p align="center">
		<c:out value="${message}"/>
	</p>
		
</body>
</html>