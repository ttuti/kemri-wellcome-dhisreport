<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<h3>Update User</h3>
		<form:form method="post" modelAttribute="user">
			<form:hidden path="id"/>	
			<div id="errors" class="errors"></div>
			<p>
				<label for="surname">Surname:</label>
				<form:input path="surname" size="24" required="required" />
			</p>
			<p>
				<label for="firstname">First Name:</label>
				<form:input path="firstname" size="24" required="required" />
			</p>
			<p>
				<label for="lastname">Last Name:</label>
				<form:input path="lastname" size="24" required="required" />
			</p>
			<p>
				<label for="email">Email:</label>
				<form:input path="email" type="email" size="24" required="required" />
			</p>
			<p>
				<label for="username">Username:</label>
				<form:input path="username" size="24" required="required" />
			</p>
			<p id="roleslbl">
				<label for="roles">User Roles:</label>
				<form:checkboxes path="roles" items="${roleList}" itemValue="roleName" itemLabel="roleName" />
			</p>
			<div style="border:1px solid #E7E7E7;padding:10px;margin-bottom:5px;">
				<h2>Fill only if changing password</h2>
				<p>
					<label for="password">Password:</label>
					<form:password path="password" size="24" />
				</p>
				<p>
					<label for="password2">Confirm Password:</label>
					<form:password path="password2" size="24" />
				</p>				
			</div>			
			<div id="formButton">
				<input type="submit" value="Edit" class="button">
				<button class="button" onClick="KWTRDI.ajaxLoad('/dhis/listuser')">Cancel</button>
			</div>						
		</form:form>
	</fieldset>
</div>
<script type="text/javascript">
    $j().ready(
            function() 
            {
            	<c:forEach var="role" items="${userRoles}">
	        		$j('input:checkbox[value=${role}]').attr('checked',true);
	        	</c:forEach>
	        	
	        	$j('#roleslbl label').each(function() {
            		var value = $j(this).html();
            		value = value.replace("ROLE_", "");
            		$j(this).html(value);
            	});
            	
	        	$j('#roleslbl input[type=checkbox]').each(function() {
            		var id = $j(this).attr('id');
            		$j('label[for="'+id+'"]').addClass("replace");
            	});
                $j('#user').submit(
                        function() 
                        {
                            var account = $j(this).serializeObject();
                            $j('#errors').html("Processing..........");
                            $j.postJSON('${prefix}/dhis/edituser.php',
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
        	KWTRDI.ajaxLoad('/dhis/listuser');
        }
    }    
</script>
