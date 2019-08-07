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
		window.location = '${pageContext.request.contextPath}/';
	}
	setTimeout("leave()", 3000);
</script>

</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<p align="center">
		<font face="sans-serif" size="5">
			<s:message code = "acces.denided"/>
		</font>
	</p>
</body>
</html>