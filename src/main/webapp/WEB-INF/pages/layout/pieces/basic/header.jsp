<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<link href="<c:url value="/resources/css/jquery-ui-1.8.19.custom.css" />" rel="stylesheet" type="text/css" />
<link href="<c:url value="/resources/css/index.css"/>" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="<c:url value="/resources/images/icons/favicon.ico"/>" />
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", -1);
%>