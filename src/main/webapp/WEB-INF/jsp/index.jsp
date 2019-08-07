<%@page import="mikolo.springWebApp.features.namedays.NameDaysApi"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/incl/taglibs.app"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file = "/WEB-INF/incl/stylelinks.app"%> 
<title><s:message code = "menu.mainPage"/></title>
</head>
<body>
<%@include file = "/WEB-INF/incl/menu.app"%>
	<h3 align = "center">
		<c:out value="${message}"></c:out>
	</h3>

    <jsp:useBean id="nda" class = "mikolo.springWebApp.features.namedays.NameDaysApi"></jsp:useBean>
    <c:set var = "namedays" value = "${nda.getDateAndNameDays()}" />
    <c:set var = "names" value = "${nda.getNameDays()}" />
    <div align ="center">
 		<c:out value="${namedays}"/> 
 		<c:forEach var = "u" items = "${names}">
 			<c:if test = "${fn:toLowerCase(u) eq fn:toLowerCase(user.name)}">
 				<p><b><c:out value="${user.name}"/>, czy masz dziś imieniny?</b></p>       		
      		</c:if>
 		</c:forEach>
 	</div>    
</body>
</html>