<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/incl/taglibs.app"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file = "/WEB-INF/incl/stylelinks.app"%> 
<title><s:message code="profilEdit.pageName"/></title>
</head>
<body>
<script type="text/javascript">
	function leave() {
		window.location = '${pageContext.request.contextPath}/logout';
	}
	setTimeout("leave()", 3000);
</script>

</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<p align="center">
		<font face="sans-serif" size="5">
			<c:out value="${message}" />
		</font>
	</p>
</body>
</html>