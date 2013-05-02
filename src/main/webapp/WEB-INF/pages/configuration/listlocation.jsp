<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>

<c:url value="${prefix}/report/alllocations.php" var="locationUrl"/>

<script type='text/javascript'>
var selectedId=null;
$j(function() {
	
	$j("#locationgrid").jqGrid({
		url:'${locationUrl}',
		datatype:'json',
		mtype: 'GET',
		colNames:['','ID','LOCATION NAME','CODE'],	
		colModel : [ 
			{
				name : 'id', index : 'id', formatter:function (cellvalue, options, rowObject) {
                    return '<input onclick="setLocationSelected()" type="radio" class="radio_'+options.gid+'" name="radio_'+options.gid+'" value="' + cellvalue + '"  />';
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
			pager : '#locationpager',
			sortname : 'id',
			viewrecords : true,
			gridview: true,
			sortorder : "desc",
			caption : "DHISv2 Locations (Organizational Units)",
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
				(selectedId==null) ? $j("#edit_location,#delete_location").attr("disabled", true) : $j("#edit_location,#delete_location").attr("disabled", false);				
			}
		});
	
		$j("#locationgrid").jqGrid('navGrid', '#locationpager', {
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
		
		$j("#locationgrid").jqGrid('filterToolbar', {
			stringResult : true,
			searchOnEnter : true,
			defaultSearch : "cn"
		});		

	});	
	
	function setLocationSelected(){
		selectedId = null;
		selectedId = $j('input[name=radio_locationgrid]:checked').val();
		(selectedId==null) ? $j("#edit_location,#delete_location").attr("disabled", true) : $j("#edit_location,#delete_location").attr("disabled", false);          
    }
</script>
	<div id="gridwrapper">
		<button class="button" onclick="KWTRDI.ajaxLoad('/report/addLocation');">
			Add
		</button>
		<button id="edit_location" disabled="disabled" class="button" onclick="KWTRDI.ajaxEdit('/report/editLocation',selectedId);">
			Edit
		</button>
		<button id="delete_location" disabled="disabled" class="button" onclick="KWTRDI.ajaxEdit('/report/deleteLocation',selectedId);">
			Delete
		</button>
		<br clear="all" />
		<div id='jqlocationgrid'>
			<table id='locationgrid'></table>
			<div id='locationpager'></div>
		</div>
	</div>