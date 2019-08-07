<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/incl/taglibs.app"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file = "/WEB-INF/incl/stylelinks.app"%> 
<title><s:message code="button.zmianaHasla"/></title>
</head>
<body>
<%@include file="/WEB-INF/incl/menu.app" %>

<h2>
	<s:message code="button.zmianaHasla"/>
</h2>

	<h3 align = "center">
		<c:out value="${message}"></c:out>
	</h3>

<sf:form id="usersForm" action="updatepass" modelAttribute="user" enctype="multipart/form-data" method="POST">
	<s:message code="oldPassword.placeholder" var = "oldPasswordPlaceholder"/>
	<s:message code="newPassword.placeholder" var = "newPasswordPlaceholder"/>
		<table width="500" border="0" cellpadding="4" cellspacing="1" align="center">
			
			<tr>
				<td width="130" align="right" ><s:message code="passwordChange.oldPassword"/></td>
				<td width="270" align="left"><sf:password path="oldPassword" autofocus = "autofocus" placeholder = "${oldPasswordPlaceholder}" size="28" class = "useredit"/></td>	
			</tr>
			
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors path="oldPassword"/></font></td>	
			</tr>
			
			<tr>
				<td width="130" align="right" ><s:message code="passwordChange.newPassword"/></td>
				<td width="270" align="left"><sf:password path="newPassword" placeholder = "${newPasswordPlaceholder}" size="28" class = "useredit"/></td>	
			</tr>
			
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors path="newPassword"/></font></td>	
			</tr>
		</table>
		<table style="margin-top: 20px;" width="500" border="0" cellpadding="4" cellspacing="1" align="center">	
			<tr>
				<td align="center">
					<input type="submit" value="<s:message code="button.zmianaHasla"/>" class="formbutton" style="background-color:#00ff00;"/>
				</td>
				<td align="center">	
					<input type="button" value="<s:message code="button.cancel"/>"
						onclick="window.location.href='${pageContext.request.contextPath}/profile'" class="formbutton" style="background-color:#ff6600;"/>
				</td>
			</tr>
		</table>
</sf:form>

</body>
</html>