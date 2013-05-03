<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<h3>${reportExecute.reportDefinitionName}</h3>
		<form:form method="post" modelAttribute="reportExecute">
			<form:hidden path="reportDefinationId" />	
			<div id="errors" class="errors"></div>
			<p>
				<label for="frequency">Report Frequency:</label>
				<span class="styled-select">
					<select name="frequency" required="required">
						<option value="Weekly">Weekly</option>
						<option value="Monthly">Monthly</option>
					</select>
				</span>
			</p>
			<p>
				<label for="location">Location:</label>
				<span class="styled-select">
				 <select name="location" required="required"> 
                    <c:forEach var="location" items="${locations}" >
                        <option value="${location.name}">${location.name}</option>
                    </c:forEach>
                </select>
                </span>
			</p>
			<p>
				<label for="date">Date:</label>
				<form:input path="date" required="required" />
			</p>
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
        var importSummary = data.u.importSummary;
        var status = importSummary.status;
        var desc = importSummary.description;
        var count = importSummary.dataValueCount;
        var snippet = "<dl><dt>Status</dt><dd>"+status+"</dd><dt>Description</dt><dd>"+desc+"</dd><dt>Count</dt><dd>"+count+"</dd></dl>";
        setTimeout(function(){
        	$j('#errors').html(snippet);
			KWTRDI.ajaxLoad('/report/listlocation');
		},2000);
    }    
</script>