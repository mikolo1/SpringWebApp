<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/incl/taglibs.app"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file = "/WEB-INF/incl/stylelinks.app"%> 
<title><s:message code="profilEdit.pageName"/></title>
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<h2><s:message code="profilEdit.pageName"/></h2>
	
		<sf:form id="usersForm" action="updateprofile" modelAttribute="user" enctype="multipart/form-data" method="POST">
		
		<table width="500" border="0" cellpadding="4" cellspacing="1" align="center">

			<tr>
				<td width="130" align="right" ><s:message code="register.name"/></td>
				<td width="270" align="left"><sf:input path="name" size="28" class = "useredit" autofocus = "autofocus"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors path="name"/></font></td>
			</tr>

			<tr>
				<td width="130" align="right"><s:message code="register.lastName"/></td>
				<td width="270" align="left"><sf:input path="lastName" size="28" class = "useredit"/></td>
			</tr>

			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors path="lastName"/></font></td>
			</tr>

			<tr>
				<td width="130" align="right" ><s:message code="register.email"/></td>
				<td width="270" align="left"><sf:input path="email" size="28" class = "useredit"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors path="email"/></font></td>
			</tr>
		</table>
		<table style="margin-top: 20px;" width="500" border="0" cellpadding="4" cellspacing="1" align="center">	

			<tr>
				<td align="center">
					<input type="submit" value="<s:message code="button.save"/>" class="formbutton" style="background-color:#00ff00;"/>
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