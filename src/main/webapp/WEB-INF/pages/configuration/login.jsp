<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page session="true"%>
<%@ include file="/WEB-INF/pages/includes/includes.jsp" %>
<div id="gkPopupLogin">
<div class="gkPopupWrap">
	<div id="loginForm">
		<h3>Sign In</h3>
		<div class="clear overflow">
			<form id="login-form" method="post" action="/KWTRDI/j_spring_security_check">
				<c:if test="${not empty param.authfailed}">
					<c:if test="${((fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'User is disabled', 'Username or Password are incorrect')) != '') && ( (fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', 'Username or Password are incorrect')) == 'User is disabled' )}">
						<script type="text/javascript" src="${PREFIX}/js/popup.js"></script>
					</c:if> 
					<c:if test="${((fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', 'Username or Password are incorrect')) != '') && ( (fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', 'Username or Password are incorrect')) != 'User is disabled' ) }">
						<span id="infomessage" class="errors">						
							${fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', 'Username or Password are incorrect')}					 
						</span>
					</c:if>
				</c:if>
				<fieldset class="userdata">
					<p id="form-login-username">
						<label for="j_username">User Name</label>
						<input type="text" size="24" class="inputbox" name="j_username" id="j_username">
					</p>
					<p id="form-login-password">
						<label for="j_password">Password</label>
						<input type="password" size="24" class="inputbox" name="j_password" id="j_password">
					</p>
					
					<div id="form-login-buttons">
						<input type="submit" value="Log in" class="button" name="Submit">
					</div>	
				</fieldset>
			</form>							
		</div>
	</div>	     
</div>
</div>
<!--  	
<div class="forgot">
	<a href="#" onclick="KWTRDI.ajaxChangePassword('/forgotpassword'); return false;">
			Forgot password? 
	</a>
</div>
<br />

<div id="popupForgot" style="width: auto; min-height: 0px; height: 120.4px;"></div>

-->


