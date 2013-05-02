<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<h3>DHISv2 Server</h3>
		<form:form method="post" modelAttribute="dhis2Server">	
			<div id="errors" class="errors"></div>
			<form:hidden path="id"/>
			<p>
				<label for="url">Server URL:</label>
				<form:input type="url" path="url" size="24" required="required" />
			</p>
			<p>
				<label for="username">Username:</label>
				<form:input path="username" size="24" required="required" />
			</p>
			<p>
				<label for="password">Password:</label>
				<form:input type="password" path="password" size="24" required="required" showPassword="true" />
			</p>
			<div id="formButton">
				<input type="submit" value="Configure" class="button">
			</div>						
		</form:form>
	</fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
                $j('#dhis2Server').submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#errors').html("Processing..........");
                            $j.postJSON('${prefix}/dhis/configure.php',
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
        	KWTRDI.ajaxLoad('/dhis/configure');
        }
    }    
</script>
