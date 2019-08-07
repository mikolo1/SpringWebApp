<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/incl/taglibs.app"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file = "/WEB-INF/incl/stylelinks.app"%> 
<title><s:message code="menu.register" /></title>
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>

	<h2>
		<s:message code="menu.register" />
	</h2>

	<p align="center">
		<c:out value="${message}"/>
	</p>
<!-- modelAttribute "user" taki sam jak nazwa atrybutu w kontrolerze -->
<!-- action="adduser" wywyołuje zmapowaną metodę z kontrolera po naciśnięciu buttona z typem submit -->
	<sf:form id="usersForm" action="adduser" modelAttribute="user" enctype="multipart/form-data" method="POST">
	<s:message code="name.placeholder" var = "namePlaceholder"/>
	<s:message code="lastName.placeholder" var = "lastNamePlaceholder"/>
	<s:message code="email.placeholder" var = "emailPlaceholder"/>
	<s:message code="password.placeholder" var = "passwordPlaceholder"/>
		<table width="500" border="0" cellpadding="4" cellspacing="1" align="center">
			<tr>
				<td width="130" align="right"><s:message code="register.name" /></td>
				<td width="270" align="left"><sf:input path="name" size="28" class = "useredit" autofocus = "autofocus" placeholder = "${namePlaceholder}"/></td>
				<!-- path musi odpowiadać nazwie pola w klasie odpowiedzialnej za encję, nie nazwie tabeli (w tym przypadku String name) -->
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="name"/></font></td>
			</tr>

			<tr>
				<td width="130" align="right"><s:message code="register.lastName" /></td>
				<td width="270" align="left"><sf:input path="lastName" size="28" class = "useredit" placeholder = "${lastNamePlaceholder}"/></td>
			</tr>

			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="lastName" /></font></td>
			</tr>

			<tr>
				<td width="130" align="right"><s:message code="register.email" /></td>
				<td width="270" align="left"><sf:input path="email" size="28" class = "useredit" placeholder = "${emailPlaceholder}"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="email" /></font></td>
			</tr>

			<tr>
				<td width="130" align="right"><s:message code="register.password" /></td>
				<td width="270" align="left"><sf:password path="password" size="28" class = "useredit" placeholder = "${passwordPlaceholder}"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors
							path="password" /></font></td>
			</tr>
		</table>
		
		<table style="margin-top: 20px;" width="500" border="0" cellpadding="4" cellspacing="1" align="center">	
			<tr>
				<td align="center">
					<input type="submit" value="<s:message code="button.register"/>"class="formbutton" style="background-color:#00ff00;"/>
				</td>	
				
				<td align="center">
					<input type="button" value="<s:message code="button.cancel"/>" class="formbutton"
					onclick="window.location.href='${pageContext.request.contextPath}/'" style="background-color:#ff6600;"/>
				</td>
			</tr>

		</table>
	</sf:form>
	
</body>
</html>