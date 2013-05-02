<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>

<c:url value="${prefix}/report/allreports.php" var="reportUrl"/>

<script type='text/javascript'>
var selectedId=null;
$j(function() {
	
	$j("#reportgrid").jqGrid({
		url:'${reportUrl}',
		datatype:'json',
		mtype: 'GET',
		colNames:['','ID','REPORT NAME','CODE'],	
		colModel : [ 
			{
				name : 'id', index : 'id', formatter:function (cellvalue, options, rowObject) {
                    return '<input onclick="setReportSelected()" type="radio" class="radio_'+options.gid+'" name="radio_'+options.gid+'" value="' + cellvalue + '"  />';
                } ,width:25,search:false			
			},
			{
				name : 'id', index: 'id'			
			}, 
			{
				name : 'name', index: 'name'
			},
			{
				name : 'code', index: 'code'
			}
			],		
			postData : {},
			rowNum : 10,
			rowList : [ 5, 10, 20, 40 ],
			height: 'auto',
			autowidth : true,
			shrinkToFit: false,
			rownumbers : true,
			pager : '#reportpager',
			sortname : 'id',
			viewrecords : true,
			gridview: true,
			sortorder : "desc",
			caption : "DHISv2 Reports",
			emptyrecords : "Empty records",
			loadonce : false,
			loadComplete : function() {
			},
			jsonReader : {
				root : "rows",
				page : "page",
				total : "total",
				records : "records",
				repeatitems : false,
				cell : "cell",
				id : "id"
			},
			onSelectRow: function(id){ 
				selectedId = id; 
				(selectedId==null) ? $j("#edit_report,#delete_report,#execute_report").attr("disabled", true) : $j("#edit_report,#delete_report,#execute_report").attr("disabled", false);				
			}
		});
	
		$j("#reportgrid").jqGrid('navGrid', '#reportpager', {
			edit : false,
			add : false,
			del : false,
			search : true
		}, 
		{}, 
		{},
		{
			sopt:['eq', 'ne', 'lt', 'gt', 'cn', 'bw', 'ew'],
	           closeOnEscape: true,
	            multipleSearch: true,
	             closeAfterSearch: true
	    }, {});
		
		$j("#reportgrid").jqGrid('filterToolbar', {
			stringResult : true,
			searchOnEnter : true,
			defaultSearch : "cn"
		});		

	});	
	
	function setReportSelected(){
		selectedId = null;
		selectedId = $j('input[name=radio_reportgrid]:checked').val();
		(selectedId==null) ? $j("#edit_report,#delete_report,#execute_report").attr("disabled", true) : $j("#edit_report,#delete_report,#execute_report").attr("disabled", false);          
    }
</script>
	<div id="gridwrapper">
		<button id="edit_report" disabled="disabled" class="button" onclick="KWTRDI.ajaxEdit('/reportdefinition/editReportDefinition',selectedId);">
			Edit
		</button>
		<button id="delete_report" disabled="disabled" class="button" onclick="KWTRDI.ajaxEdit('/reportdefinition/deleteReportDefinition',selectedId);">
			Delete
		</button>
		<button id="execute_report" disabled="disabled" class="button" onclick="KWTRDI.ajaxEdit('/report/setupReport',selectedId);">
			Execute
		</button>
		<br clear="all" />
		<div id='jqreportgrid'>
			<table id='reportgrid'></table>
			<div id='reportpager'></div>
		</div>
	</div>