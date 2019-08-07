<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/incl/taglibs.app"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file = "/WEB-INF/incl/stylelinks.app"%> 
<title><s:message code= "logowanie.pageName"/></title>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<h2>
		<s:message code = "logowanie.pageName"/>
	</h2>

	<sf:form id = "form" action = "/login" method="POST">	<!-- spring security wie, że ma obsłużyć akcję "/login", to jest zdefiniowane w klasie SecurityConfig -->
	<s:message code="email.placeholder" var = "emailPlaceholder"/>
	<s:message code="password.placeholder" var = "passwordPlaceholder"/>	
		<table width = "500" border = "0" cellpadding = "4" cellspacing = "1" align = "center">
			<tr>
				<td colspan = "2" align = "center"><!-- obsługa błędów -->
				<!-- instrukcja warunkowa jstl, jesli wróci niepusty parametr błędu, to na czerwono wyświetli komunikat z messages -->
					<!--  proces logowanie przejmuje spring security -->
					<c:if test="${not empty param.error}">
						<font color="red"><s:message code = "error.logowanie"/></font>
					</c:if>
				</td>
			</tr>
			<tr>
				<td align ="right" width = "140">
					<s:message code="register.email"/>
				</td>
				<td align ="left">
					<input type = "text" name = "email" id = "email" size = "28" autofocus = "autofocus" placeholder = "${emailPlaceholder}" class = "useredit"/>
				</td>
			</tr>
			<tr>
				<td align ="right" width = "140">
					<s:message code="register.password"/>
				</td>
				<td align ="left">
					<input type = "password" name = "password" id = "password" placeholder = "${passwordPlaceholder}" size = "28" class = "useredit"/>
				</td>
			</tr>
		</table>
		
		<table style="margin-top: 20px;" width="500" border="0" cellpadding="4" cellspacing="1" align="center">	
			<tr>
				<td align = "center" colspan = "2" align = "center" >
					<input type ="submit" value = "zaloguj" class="formbutton" style="background-color:#00ff00;"/>
				</td>
			</tr>
		</table>
	</sf:form>
	
</head>
<body>

</body>
</html>