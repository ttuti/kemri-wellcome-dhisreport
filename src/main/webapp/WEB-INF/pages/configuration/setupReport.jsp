<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<h3>${reportExecute.reportDefinitionName}</h3>
		<form:form method="post" modelAttribute="reportExecute">
			<form:hidden path="reportDefinationId" />
			<form:hidden path="reportDefinitionName" />	
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
				<button onClick="KWTRDI.ajaxLoad('/report/listreports')" id="back" class="button">Back</button>
			</div>
		</form:form>
	</fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j('#back').hide();
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
        var snippet = null;
        if(status=="ERROR")
        	snippet = "<div style='color:#ED0C1B'><span style='font-weight:bold;'>Status</span>: "+status+"</div><div style='color:#ED0C1B'><span style='font-weight:bold;'>Description</span>: "+desc+"</div>";
        else
        	snippet = "<div style='color:#3E78FD;'><span style='font-weight:bold;'>Status</span>: "+status+"</div><div style='color:#3E78FD;'><span style='font-weight:bold;'>Description</span>: "+desc+"</div>";	
        $j('#errors').html(snippet);
        $j('select[name=frequency]').attr("disabled", true);
        $j('select[name=location]').attr("disabled", true);
        $j('input[name=date]').attr("disabled", true);
        $j('#back').show();
    }    
</script>