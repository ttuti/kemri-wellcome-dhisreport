<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow2">
	<fieldset>
		<h3>${summary.reportName}</h3>
		<dl>
			<dt>Submit Status</dt>
			<dd>${summary.status}</dd>
			
			<dt>Description</dt>
			<dd>${summary.description}</dd>
			
			<dt>Statistics Count</dt>
			<dd>${count}</dd>
		</dl>
		<c:if test="${not empty summary.conflicts}">
			<c:forEach items="${summary.conflicts}" var="conflict">
				<dl>
					<dt>${conflict.object}</dt>
					<dd>${conflict.value}</dd>
				</dl>
			</c:forEach>
		</c:if>
		<div id="formButton">
			<button class="button" onclick="KWTRDI.ajaxLoad('/report/listreports');">
				Back
			</button>
		</div>
	</fieldset>
</div>