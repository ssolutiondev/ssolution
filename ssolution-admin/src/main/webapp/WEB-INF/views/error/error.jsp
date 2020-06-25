<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>

<script type="text/javascript">
    function moveLogin(){
        location.href = "/system/login/login";
    }
</script>

<div class="middle-box text-center animated fadeInDown savana errorBox">
    <h1 class="font-bold">${status}</h1>
    <div class="error-desc">
        <h3 class="font-bold">${error}</h3>
        <c:if test="${status eq '401'}">
            <a onclick='moveLogin();' class="btn btn-de btn-red m-t"><spring:message code="LAB.M04.LAB00030"/></a>
        </c:if>
        <c:if test="${status ne '401'}">
            <a onclick='history.go(-1); return false;' class="btn btn-de btn-red m-t"><spring:message code="LAB.M08.LAB00504"/></a>
        </c:if>

    </div>
</div>