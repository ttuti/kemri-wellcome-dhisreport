<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow2">
	<fieldset>
		<h3>Data Set: ${reportDefinition.name} <c:if test="${not empty reportDefinition.code}"> [ <span>${reportDefinition.code}</span> ]</c:if></h3>
		<table style="width:90%;margin:auto;table-layout:auto;">
			<c:forEach var="dataValueTemplate" varStatus="varStatus" items="${reportDefinition.dataValueTemplates}">
	    		<tr class='${varStatus.index % 2 == 0 ? "oddRow" : "evenRow" }'>
	            	<td>
	            		<input type="checkbox" value="dataValueTemplate${dataValueTemplate.id }" name="dataValueTemplateId">
                  	</td>
	               	<td>${dataValueTemplate.dataelement.name}</td>
                    <td>
                    	<pre class="brush: sql;gutter: false;toolbar: false;" id="reportDefinition_query${dataValueTemplate.id }">${dataValueTemplate.query}</pre>	                   	
                    </td>
                    <td>
                    	<a onclick="KWTRDI.edit(${dataValueTemplate.id },${reportDefinition.id})" id="reportDefinition_edit${dataValueTemplate.id }" class="button">
                    		Edit
                    	</a> 
                    	<span id="reportDefinition_save${dataValueTemplate.id }"></span>
	         		</td>
	        	</tr>	         
         	</c:forEach>
         </table>			
	</fieldset>
</div>
<script type="text/javascript">
     $j().ready(function(){
    	 SyntaxHighlighter.highlight();
     });
</script>