<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>

sdfsdf
<script type="text/javascript">
    function goMainPage(url) {
        var mainUrl = url + '?noSelectMenu=Y';
        goMenuPage(mainUrl);
    }
</script>
<div class="page-heading savana-page-heading">
    <h2>${menuNm}</h2>
   	
   	<div class="location pull-right">
   		<ol class="breadcrumb text-right">
            <li>
                <a href="javascript:goMainPage('${session_user.dashboardUrl}');">Home</a>
            </li>
            <c:forEach items="${naviMenuList}" var="mu" varStatus="status">
            	<c:if test="${!status.last}">
					<li>
	                	${mu.menuNm}
	            	</li>
				</c:if>
				<c:if test="${status.last}">
					<li class="active">
	                	<strong>${mu.menuNm}</strong>
	            	</li>
				</c:if>
			</c:forEach>
        </ol>
   	</div>       
   	<input type="hidden" id="headerCurMenuId" name="curMenuId" value="${selectedMenu.selectMenuId}" />
</div>