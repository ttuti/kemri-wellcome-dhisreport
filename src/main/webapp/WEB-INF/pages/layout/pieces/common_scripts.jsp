<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", -1);
response.setHeader("Expires", "Tues, 01 Jan 1980 00:00:00 GMT");
response.setHeader("Pragma","no-cache");
%>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.8.3.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/grid.locale-en.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.jqGrid.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/json.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui-1.10.2.custom.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.browser.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/scripts.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.fileupload.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.iframe-transport.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/shCore.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/shBrushSql.js"/>"></script>
<script type="text/javascript">
	var $j = jQuery.noConflict();
</script>
<script type="text/javascript" src="<c:url value="/resources/js/prototype.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/dimbox.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/utils.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/KWTRDI.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/shadedborder.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/tempDateFormat.js"/>"></script>
