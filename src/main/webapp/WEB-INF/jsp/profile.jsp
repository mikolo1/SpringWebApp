<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/incl/taglibs.app"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file = "/WEB-INF/incl/stylelinks.app"%> 
<title><s:message code = "profil.userDane"/></title>
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>

		<h2>
			<s:message code = "profil.userDane"/>
		</h2>

	<div align = "center">
		<table width="500" border="0" cellpadding="4" cellspacing="1" align="center">
			<tr>
				<td width="130" align="right">
					<s:message code="register.email" />
				</td>
				<td width="270" align="left">
					<c:out value= "${user.email}"/>
				</td>
			</tr>
			<tr>
				<td width="130" align="right">
					<s:message code="register.name" />
				</td>
				<td width="270" align="left">
					<c:out value= "${user.name}"/>
				</td>
			</tr>
			<tr>
				<td width="130" align="right">
					<s:message code="register.lastName" />
				</td>
				<td width="270" align="left">
					<c:out value= "${user.lastName}"/>
				</td>
			</tr>
			<tr>
				<td width="130" align="right">
					<s:message code="profil.isActive" />
				</td>
				<td width="270" align="left">
					<font color = "${activeFontColor}">
						<s:message code="${active}"/>
					</font>
				</td>
			</tr>
			<tr>
				<td width="130" align="right">
					<s:message code="profil.rola" />
				</td>
				<td width="270" align="left">
					<font color = "${roleFontColor}">
						<s:message code="${role}"/>
					</font>
				</td>
			</tr>
		</table>
		<table style="margin-top: 20px;" width="500" border="0" cellpadding="4" cellspacing="1" align="center">
			<tr>
				<td align="center">
					<input type="button" value="<s:message code="button.edycjaProfilu"/>" 
							onclick="window.location.href='${pageContext.request.contextPath}/editprofile'" class="formbutton" style="background-color:#0ff;"/>
				</td>
				<td align = "center">
					<input type = "button" value = "<s:message code = "button.zmianaHasla"/>" onclick="window.location.href='${pageContext.request.contextPath}/editpassword'" class="formbutton" style="background-color:#0ff;"/>		
				</td>
			</tr>
		</table>
	</div>

</body>
</html>