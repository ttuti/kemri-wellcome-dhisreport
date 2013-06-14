<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<!DOCTYPE html>
<html lang="en-gb">
<head>
	<sec:authorize access="hasAnyRole('ROLE_USER')">
		<c:set var="usertype" value="user" />
	</sec:authorize>
	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
		<c:set var="usertype" value="admin" />
	</sec:authorize>
	<tiles:insertAttribute name="meta" />
	<title>
		<tiles:insertAttribute name="title" />
	</title>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="common-scripts" />
</head>
<script type="text/javascript">
//browser microsoft-msie
//version microsoft-8.0
var browser = $j.browser.name;
var version = $j.browser.version; 

</script>
<body class="nobg" data-loading-translation="Loading" data-content-width="2" data-blocks-animation="1" data-mobile-width="480" data-tablet-width="800">
	<script type="text/javascript">
		var prefix = "${prefix}";
		var context = "${context}";
	</script>
	<div id="mainWrapper">
		<!-- header logo etc -->
		
        <header>
        	<div id="header">
        		<img src="<c:url value="/resources/images/logo.png"/>" alt="KEMRI/Wellcome Trust Programme Logo" />
        	</div>        	       	
        </header>
         <!-- end header -->  
         <!-- navigation -->   
         <nav>
        	<tiles:insertAttribute name="menu" />
        </nav> 
        <!-- end navigation -->   
         <!-- start container -->
		<section style="margin-top:90px;">
			<!-- Start content -->
			<div id="content">
           		<tiles:insertAttribute name="content" />
           	</div>
			<!-- End content -->
		</section>
		<!-- end container -->	
		<!-- footer -->
	     <footer>
	     	<div id="footer">
	     		<a href="http://www.kemri-wellcome.org">&copy;2013 KEMRI/Wellcome Trust Programme</a>
	     	</div>
	     </footer>    
	     <!-- end footer --> 			
	</div>
	<script type="text/javascript">
    $j().ready(function(){
        KWTRDI.ajaxLoad('/summary');
        if('${usertype}'=='user'){
        	$j('#nav li a').each(function() {
		        $j(this).addClass("menuResize");
	    	});
        }else{
        	$j('#nav li a').each(function() {
	    		$j(this).removeClass("menuResize");
	    	});
        }
    });
    </script>		
</body>
</html>