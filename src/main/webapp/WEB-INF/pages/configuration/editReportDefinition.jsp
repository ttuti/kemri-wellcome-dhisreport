<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow2 code">
	<fieldset>
		<h3>Data Set: ${reportDefinition.name} <c:if test="${not empty reportDefinition.code}"> [ <span>${reportDefinition.code}</span> ]</c:if></h3>		
		<c:forEach var="dataValueTemplate" varStatus="varStatus" items="${reportDefinition.dataValueTemplates}">
    		<div class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" }'>
            	<div style="width:20%;float:left;padding-top:15px;">
            		${dataValueTemplate.dataelement.name}
                 </div>
               	<div style="width:70%;float:left;">                   
                   	<pre class="brush: sql;gutter: false;toolbar: false;" id="reportDefinition_query${dataValueTemplate.id }">${dataValueTemplate.query}</pre>	                   	
                </div>
                <div style="width:9%;float:left;padding-top:15px;">
                   	<a onclick="KWTRDI.edit(${dataValueTemplate.id },${reportDefinition.id})" id="reportDefinition_edit${dataValueTemplate.id }" class="button">
                   		Edit
                   	</a> 
                   	<span id="reportDefinition_save${dataValueTemplate.id }"></span>
         		</div>
        	</div>	         
        </c:forEach>			
	</fieldset>
</div>
<script type="text/javascript">
     $j().ready(function(){
    	 SyntaxHighlighter.highlight();
     });
</script>