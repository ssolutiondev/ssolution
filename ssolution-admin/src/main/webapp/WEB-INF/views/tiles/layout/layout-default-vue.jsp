<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="/WEB-INF/tag/customTag.tld" prefix="customTag" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    
	<tiles:insertAttribute name="js" ignore="true" />
	<title><spring:message code="LAB.M15.LAB00014" /></title>
</head>
<script type="text/javascript">
	var lng = '<%= session.getAttribute( "sessionLanguage" ) %>';
	var menuNm = '<%= session.getAttribute( "menuNm" ) %>';
	document.title = menuNm;
</script>
<body>
	<tiles:insertAttribute name="body" />
</body>
</html>
