<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<ul id="nav">
	<li><a href="<c:url value="/home.php"/>" title="Home"><img src="<c:url value="/resources/images/apple.png"/>" alt="Apple Logo" /></a></li>
	<li><a href="#" onClick="KWTRDI.ajaxLoad('/dhis/configure'); return false;">Configure DHISv2 Server</a></li>
	<li>
		<a href="#">Report Definitions</a>
		<ul>
			<li><a href="#" onClick="KWTRDI.ajaxLoad('/reportdefinition/upload'); return false;">Upload</a></li>
			<li><a href="<c:url value="/reportdefinition/export.php"/>">Export Templates</a></li>
		</ul>
	</li>
	<li>
		<a href="#" onClick="KWTRDI.ajaxLoad('/report/listreports'); return false;">Reports</a>
	</li>
	<li>
		<a href="#" onClick="KWTRDI.ajaxLoad('/report/listlocation'); return false;">Locations</a>
	</li>
	<li><a href="<c:url value="/j_spring_security_logout"/>">Log Out</a></li>	
</ul>

