<sec:authorize access = "hasRole('ROLE_ADMIN')">
	<table width = "100%" border = "0" cellpading = "8" cellspacing = "4" class = "tableMenuBg" bgcolor = "#FFDBB2">
		<tr>
			<td align = "center" width="12%">
				<a href="/admin/users/1"><s:message code = "menu.users"/></a>
			</td>
			<td align = "center" width="12%">
				<!-- <a href="/admin/upload"><s:message code = "button.upload"/></a> -->
			</td>
		<sf:form id = "uploadForm" action="${pageContext.request.contextPath}/admin/usersload" method="POST" enctype="multipart/form-data">
			
			<td align = "right" width="40%">
				<s:message code = "admin.xmlLoad.info"/>				
			</td>
			
			<td align = "center">
					<input type = "file" name="filename" id="chooseFile">		
			</td>
			<td align = "center" width="10%" >
				<input type = "submit" class="formbutton" value = "<s:message code = "button.upload"/>" style="background-color:#00ff00;"/>
			</td>

		</sf:form>
			</td>
		</tr>
	</table>
</sec:authorize>