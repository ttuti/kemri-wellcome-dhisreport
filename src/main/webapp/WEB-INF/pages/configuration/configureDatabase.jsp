<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<h3>Database Properties</h3>
		<form:form method="post" modelAttribute="dbProperties">
			<div id="errors" class="errors">
				<c:if test="${not empty error}">
					${error}
				</c:if>
			</div>
			<p>
				<label for="databaseUrl">Database Server (Name/IP - no protocol):</label>
				<form:input path="databaseUrl" size="24" required="required" />
			</p>
			<p>
				<label for="databaseName">Database Name:</label>
				<form:input path="databaseName" size="24" required="required" />
			</p>
			<p>
				<label for="username">Database Username:</label>
				<form:input path="username" size="24" required="required" />
			</p>
			<p>
				<label for="password">Database Password:</label>
				<form:input path="password" size="24" required="required" />
			</p>
			<div id="formButton">
				<input type="submit" value="Update" class="button">
			</div>
		</form:form>
	</fieldset>
</div>
<script type="text/javascript">
	$j().ready(function() {
		$j('#dbProperties').submit(function() {
			var account = $j(this).serializeObject();
			$j('#errors').html("Processing..........");
			$j.postJSON('${prefix}/dhis/dbsetup.php', account, function(data) {
				doaction(data);
			});
			return false;
		});
	});

	function doaction(data) {
		var message = data.u;
		$j('#errors').html(message);
		if (message == "Saved") {
			KWTRDI.ajaxLoad('/dhis/dbsetup');
		}
	}
</script>
