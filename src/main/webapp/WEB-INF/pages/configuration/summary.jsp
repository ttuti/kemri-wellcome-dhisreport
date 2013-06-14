<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>

<c:url value="${prefix}/importsummary.php" var="summaryUrl"/>

<script type='text/javascript'>
var selectedId=null;
$j(function() {
	
	$j("#summarygrid").jqGrid({
		url:'${summaryUrl}',
		datatype:'json',
		mtype: 'GET',
		colNames:['','REPORT','DATE','STATUS','IMPORT COUNT','COMPLETE','CONFLICTS'],	
		colModel : [
			{
				name : 'id', index : 'id', formatter:function (cellvalue, options, rowObject) {
			        return '<input onclick="setSummarySelected()" type="radio" class="radio_'+options.gid+'" name="radio_'+options.gid+'" value="' + cellvalue + '"  />';
			    },width:25,search:false			
			}, 
			{
				name : 'report', index: 'report'
			},
			{
				name : 'date', index: 'date',width:110
			},
			{
				name : 'status', index: 'status', cellattr: function (rowId, val, rawObject, cm, rdata) {
					if(val=='SUCCESS'){
						return 'style="color:#3ED715"';
					}else{
						return 'style="color:#DB0000"';
					}						
				}, 
				stype:"select", searchoptions: {
					value:':;SUCCESS:Success;ERROR:Error'
				}, search:true
			},
			{
				name : 'count', index: 'count', search:false
			},
			{
				name : 'complete', index: 'complete', search:false , width:110
			},
			{
				name : 'conflicts', index: 'conflicts', search:false,width:80,
			}
			],		
			postData : {},
			rowNum : 10,
			rowList : [ 5, 10, 20, 40 ],
			height: 'auto',
			autowidth : true,
			shrinkToFit: true,
			rownumbers : true,
			pager : '#summarypager',
			sortname : 'id',
			viewrecords : true,
			gridview: true,
			sortorder : "desc",
			caption : "DHISv2 Import Summary",
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
				(selectedId==null) ? $j("#details").attr("disabled", true) : $j("#details").attr("disabled", false); 
			}
		});
	
		$j("#summarygrid").jqGrid('navGrid', '#summarypager', {
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
		
		$j("#summarygrid").jqGrid('filterToolbar', {
			stringResult : true,
			searchOnEnter : true,
			defaultSearch : "cn"
		});

		$j("#gs_date").datepicker({
		    showOn : 'focus',
		    maxDate: '+0d',
    		dateFormat: 'yy-mm-dd',
    		constrainInput : true,
    		changeMonth: true,
    	    changeYear: true
		});		

	});
function setSummarySelected(){
	selectedId = null;
	selectedId = $j('input[name=radio_summarygrid]:checked').val();
	(selectedId==null) ? $j("#details").attr("disabled", true) : $j("#details").attr("disabled", false);          
}
</script>
	<div id="gridwrapper">
		<button id="details" disabled="disabled" class="button" onclick="KWTRDI.ajaxEdit('/report/summarydetails',selectedId);" style="margin-left:25px;">
			Details
		</button>
		<br clear="all" />
		<div id='jqsummarygrid' style="width:95%;margin:auto;">
			<table id='summarygrid' style="margin:auto;"></table>
			<div id='summarypager'></div>
		</div>
	</div>