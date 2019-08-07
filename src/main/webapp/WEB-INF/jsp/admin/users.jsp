<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/incl/taglibs.app"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@include file="/WEB-INF/incl/stylelinks.app"%>
<title><s:message code="menu.users" /></title>
<script type="text/javascript">
	function changeTrBg(row){
		row.style.backgroundColor = "lightgray";
	}
	
	function defaultTrBg(row){
		row.style.backgroundColor = "#FFEAE9";
	}
	
	function startSearch(pParam){
		
		let textToSearch = document.getElementById('searchText').value;
		let oryginalTextToSearch = '${searchWord}'
		let page = parseInt(document.getElementById('cp').value)+parseInt(pParam);
		let searchLink = '${pageContext.request.contextPath}/admin/users/search/'+textToSearch+'/1';
		if(textToSearch.length < 2){
			document.getElementById('errorSearch').innerHTML = "<s:message code = "error.searchString.toShort"/>";
			return false
		} else {
			document.getElementById('errorSearch').innerHTML = "";
			if(textToSearch===oryginalTextToSearch){
			searchLink = '${pageContext.request.contextPath}/admin/users/search/'+textToSearch+'/'+page;}	//tworzę link
			else {
				searchLink = '${pageContext.request.contextPath}/admin/users/search/'+textToSearch+'/1';
			}
			window.location.href = searchLink;			//wywołuję stworzony link
		}
	}
</script>
</head>
<body>
	<%@include file="/WEB-INF/incl/menu.app"%>
	<h2>
		<s:message code="menu.adminPage" />
	</h2>
	<%@include file="/WEB-INF/incl/admmenu.app"%>
	<s:message code="searchtext.placeholder" var = "searchtextPlaceholder"/>
	<div class = userstable>
		<div class = "searcharea" align="right">
			<table>
				<tr>
					<td align = "center">
						<span id = "errorSearch" style = "color:red; padding-right:20px;"></span>
					</td>
					<td align = "right">
						<input type = "hidden" name = "cp" id = "cp" value = "${currentPage}"/>
						<input type = "search" id = "searchText" placeholder ="${searchtextPlaceholder}" autofocus = "autofocus" >
					</td>
					<td align = "right" style = "weight:50px;">
						<input type = "button" class="formbutton" value = "<s:message code = "button.search"/>" onclick = "startSearch(0);" style="background-color:#00ff00;"/>
				<!-- 		<div id = "errorSearch" style = "color:red;"></div> -->
					</td>
				</tr>
			</table>	
		</div>
		<table align="center" width="1000" class="tableUsers">
				<tr class="menuTableRow">
				<td width="50" align="center"><s:message code="admin.user.nr" /></td>
				<td width="50" align="center"><s:message code="admin.user.id" /></td>
				<td width="200" align="center"><s:message code="register.name" /></td>
				<td width="200" align="center"><s:message code="register.lastName" /></td>
				<td width="250" align="center"><s:message code="register.email" /></td>
				<td width="100" align="center"><s:message code="profil.isActive" /></td>
				<td width="150" align="center"><s:message code="profil.rola" /></td>
			</tr>
			<c:set var="counter" value="${userIndex}"></c:set>
			<c:forEach var="u" items="${userList}">
	
				<c:set var="aciveFont" value="normal"></c:set>
				<c:set var="activeColor" value="black"></c:set>
				<c:if test="${u.active == 0}">
					<c:set var="activeColor" value="red"></c:set>
					<c:set var="aciveFont" value="bold"></c:set>
				</c:if>
	
				<c:set var="adminFont" value="normal"></c:set>
				<c:set var="adminColor" value="black"></c:set>
				<c:if test="${u.nrRoli == 1}">
					<c:set var="adminColor" value="purple"></c:set>	
					<c:set var="adminFont" value="bold"></c:set>		
				</c:if>
	
				<tr class="userTableRow" onmouseover="changeTrBg(this)" onmouseout="defaultTrBg(this)">
					<td align="center"><c:out value="${counter}" /></td>
					<td align="center"><a href="edit/${u.id}"/><c:out value="${u.id}" /></td>
					<td align="center"><a href="edit/${u.id}"/><c:out value="${u.name}" /></td>
					<td align="center"><a href="edit/${u.id}"/><c:out value="${u.lastName}" /></td>
					<td align="center"><a href="edit/${u.id}"/><c:out value="${u.email}" /></td>
					<td align="center" style = "color:${activeColor}; font-weight:${aciveFont}"><c:choose>
							<c:when test="${u.active == 1}">
								<s:message code="word.yes" />
							</c:when>
							<c:otherwise>
								<s:message code="word.no" />
							</c:otherwise>
						</c:choose></td>
					<td align="center" style = "color:${adminColor}; font-weight:${adminFont}"><c:choose>
							<c:when test="${u.nrRoli == 1}">
								<s:message code="word.admin" />
							</c:when>
							<c:otherwise>
								<s:message code="word.user" />
							</c:otherwise>
						</c:choose></td>
				</tr>
				<c:set var="counter" value="${counter +1}"></c:set>
			</c:forEach>
		</table>
		
		<div class = "howManyPages">
				<s:message code = "info.page"/>
				<c:out value="${currentPage}"/>
				<s:message code = "info.from"/>
				<c:out value="${totalPages}"/>
		</div>
	
		<table style="margin-top: 20px;" width="500" border="0" cellpadding="4"	cellspacing="1" align="center">
			<tr>
				<td align="center">
					<c:choose>
						<c:when test="${currentPage > 1}">
							<input type="button" value="<s:message code="link.previous"/>"
								onclick="window.location.href='${pageContext.request.contextPath}/admin/users/${currentPage - 1}'"
								class="formbutton" style="background-color: #0ff;" />
						</c:when>
						<c:otherwise>
							<input type="button" disabled value="<s:message code="link.previous"/>" class="formbutton"/>
						</c:otherwise>
					</c:choose>
				</td>	
				<td align="center" >
					<c:choose>
						<c:when test="${currentPage < totalPages}">
							<input type="button" value="<s:message code = "link.next"/>"
								onclick="window.location.href='${pageContext.request.contextPath}/admin/users/${currentPage + 1}'"
								class="formbutton" style="background-color: #0ff;" />
						</c:when>
						<c:otherwise>
							<input type="button" disabled value="<s:message code = "link.next"/>" class="formbutton"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>