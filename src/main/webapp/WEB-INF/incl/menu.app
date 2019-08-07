<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn"%>

<table width="100%" border="0" cellpadding="8" cellspacing="4" class="tableMenuBg" bgcolor="#99ff99">
	<tr>
		<td align="center" width="12%">
			<a href="/"><s:message code="menu.mainPage"/></a>
		</td>
		<td align="center" width="12%">	
	 		<sec:authorize access="hasRole('ROLE_ADMIN')">
				<a href="/admin"><s:message code="menu.adminPage"/></a>		
			</sec:authorize> 	
		</td>																															
		<td width = "52%" align="center">				
				<c:choose>
					<c:when test="${fn:length(user.name)>0}">
						<b>
							<span><s:message code ="user.welcome"/></span>
							<c:out value="${user.name}"></c:out>!
						</b>
					</c:when>
				</c:choose>					
		</td>			
		<sec:authorize access="hasRole('ANONYMOUS')">
			<td width = "4%">
			</td>
			<td width = "10%" align="center">
				<a href="/login"><s:message code="menu.login"/></a>
			</td>
			<td width = "10%" align="center">
				<a href="/register"><s:message code="menu.register"/></a>
			</td>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">	
			<td width = "4%">
			</td>
			<td width = "10%" align="center">
				<a href="/profile"><s:message code="menu.profile"/></a>
			</td>
			<td width = "10%" align="center">
				<a href="/logout"><s:message code="menu.logout"/></a>
			</td>	
		</sec:authorize>
	</tr>
</table>