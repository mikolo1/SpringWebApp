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
	<h2>
		<s:message code="profilEdit.pageName"/>
	</h2>
	<%@include file="/WEB-INF/incl/admmenu.app"%>
		<sf:form id="usersForm" action="${pageContext.request.contextPath}/admin/updateuser/${userToEdit.id}" modelAttribute="userToEdit" enctype="multipart/form-data" method="POST">
		
		<table width="500" border="0" cellpadding="4" cellspacing="1" align="center">

			<tr>
				<td width="130" align="right" ><s:message code="register.name"/></td>
				<td width="270" align="left"><sf:input path="name" size="28" disabled = "true" class = "profiledit"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors path="name"/></font></td>
			</tr>

			<tr>
				<td width="130" align="right"><s:message code="register.lastName"/></td>
				<td width="270" align="left"><sf:input path="lastName" size="28" disabled = "true" class = "profiledit"/></td>
			</tr>

			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors path="lastName" /></font></td>
			</tr>

			<tr>
				<td width="130" align="right" ><s:message code="register.email"/></td>
				<td width="270" align="left"><sf:input path="email" size="28" disabled = "true" class = "profiledit"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><font color="red"><sf:errors path="email"/></font></td>
			</tr>
			
			<c:set var = "adminAttribute" value = "true"/>
			<c:set var = "userAttribute" value = "false"/>
			<c:set var = "visibilityAttribute" value = "${userAttribute}"/>
			<c:if test="${userToEdit.nrRoli == 1}">
				<c:set var = "visibilityAttribute" value = "${adminAttribute}"/>
			</c:if>
			
			
			<tr>
				<td width="130" align="right" ><s:message code="profil.rola"/></td>
				<td width="270" align="left"><sf:select path="nrRoli" items = "${roleMap}" class = "useredit" disabled = "${visibilityAttribute}"/></td>
			</tr>
			
			<tr>
				<td width="130" align="right" ><s:message code="profil.isActive"/></td>
				<td width="270" height="36" align="left"><sf:select path="active" items = "${activityMap}" class = "useredit" disabled = "${visibilityAttribute}"/></td>
			</tr>
			
		</table>
		<c:if test="${userToEdit.nrRoli == 1}">
			<div class = "dontEditAdmin">
				<span >
					<s:message code = "user.isAdmin"/>
				</span>
			</div>
		</c:if>
		<table style="margin-top: 20px;" width="500" border="0" cellpadding="4" cellspacing="1" align="center">	
			<tr>				
				<c:choose>
					<c:when test="${userToEdit.nrRoli == 2}">
						<td align="center">
							<input type="submit" value="<s:message code="button.save"/>" class="formbutton" style="background-color:#00ff00;"/>
						</td>
					</c:when>
				</c:choose>
			
				<td align="center">
					<input type="button" value="<s:message code="button.cancel"/>" 
						onclick="window.location.href='${pageContext.request.contextPath}/admin/users/1'" class="formbutton" style="background-color:#ff6600;"/>
				</td>
			</tr>
		</table>
</sf:form>

</body>
</html>