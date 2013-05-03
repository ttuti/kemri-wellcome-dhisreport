<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<h3>Edit Facility</h3>
		<form:form method="post" modelAttribute="location">	
			<div id="errors" class="errors"></div>
			<form:hidden path="id" required="required" />
			<form:hidden path="uid"/>
			<p>
				<label for="name">Facility Name:</label>
				<form:input path="name" size="24" required="required" />
			</p>
			<p>
				<label for="code">Facility Code:</label>
				<form:input path="code" size="24" required="required" />
			</p>			
			<div id="formButton">
				<input type="submit" value="Update" class="button">
			</div>						
		</form:form>
	</fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j('#location').submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#errors').html("Processing..........");
                            $j.postJSON('${prefix}/report/editLocation.php',
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
        var message = data.u;
        $j('#errors').html(message);
        if (message == "Saved") 
        {
        	KWTRDI.ajaxLoad('/report/listlocation');
        }
    }    
</script>
