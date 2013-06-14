<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<h3>${reportExecute.reportDefinitionName}</h3>
		<form:form method="post" modelAttribute="reportExecute">
			<form:hidden path="reportDefinationId" />	
			<div id="errors" class="errors"></div>
			<div class="paragraph">
				<label for="frequency">Report Frequency:</label>
				<div class="styled-select">
					<select name="frequency" required="required">
						<option value="Weekly">Weekly</option>
						<option value="Monthly">Monthly</option>
					</select>
				</div>
			</div>
			<div class="paragraph">
				<label for="location">Location:</label>
				<div class="styled-select">
				 <select name="location" required="required"> 
                    <c:forEach var="location" items="${locations}" >
                        <option value="${location.name}">${location.name}</option>
                    </c:forEach>
                </select>
                </div>
			</div>
			<div class="paragraph">
				<label for="date">Date:</label>
				<form:input path="date" required="required" />
			</div>
			<div id="formButton">
				<input type="submit" value="Post To DHIS" class="button">
			</div>
		</form:form>
	</fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
            	$j('#date').datepicker({
            		maxDate: '+0d',
            		dateFormat: 'yy-mm-dd',
            		constrainInput : true,
            		changeMonth: true,
            	    changeYear: true
            	});
                $j('#reportExecute').submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#errors').html("Processing..........");
                            $j.postJSON('${prefix}/report/setupReport.php',
                                        account, 
                                        function(data) 
                                        {
                                        	doaction(data);
                                    	});
                            return false;
                        });		
            });

    function doaction(data) 
    {
        var importSummary = data.importSummary;
        var status = importSummary.status;
        var desc = importSummary.description;
        var snippet = "<dl><dt>Status</dt><dd>"+status+"</dd><dt>Description</dt><dd>"+desc+"</dd></dl>";
        $j('#errors').html(snippet);
        setTimeout(function(){
        	KWTRDI.ajaxLoad('/report/listreports');
		},5000);
    }    
</script>