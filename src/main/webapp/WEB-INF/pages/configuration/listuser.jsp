<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>

<c:url value="${prefix}/dhis/allusers.php" var="userUrl"/>

<script type='text/javascript'>
var selectedId=null;
$j(function() {	
	$j("#usergrid").jqGrid({
		url:'${userUrl}',
		datatype:'json',
		mtype: 'GET',
		colNames:['','SURNAME','FIRST NAME','EMAIL','USERNAME','STATUS'],	
		colModel : [
			{
				name : 'id', index : 'id', formatter:function (cellvalue, options, rowObject) {
			        return '<input onclick="setUserSelected()" type="radio" class="radio_'+options.gid+'" name="radio_'+options.gid+'" value="' + cellvalue + '"  />';
			    },width:25,search:false			
			}, 
			{
				name : 'surname', index: 'surname', search:false
			},
			{
				name : 'firstname', index: 'firstname', search:false
			},
			{
				name : 'email', index: 'email'
			},
			{
				name : 'username', index: 'username'
			},
			{
				name : 'status', index: 'status', cellattr: function (rowId, val, rawObject, cm, rdata) {
					if(val=='1' || val=='Active' ){
						return 'style="color:#3ED715"';
					}else{
						return 'style="color:#DB0000"';
					}											
				}, formatter:function (cellvalue, options, rowObject) {
					if(cellvalue=='1'){
						return 'Active';
					}else{
						return 'Inactive';
					}
			    },
				stype:"select", searchoptions: {
					value:':;1:Active;0:Inactive'
				}, search:true, width:110
			}			
			],		
			postData : {},
			rowNum : 10,
			rowList : [ 5, 10, 20, 40 ],
			height: 'auto',
			autowidth : true,
			shrinkToFit: true,
			rownumbers : true,
			pager : '#userpager',
			sortname : 'id',
			viewrecords : true,
			gridview: true,
			sortorder : "desc",
			caption : "DHISv2 User Records",
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
				(selectedId==null) ? $j("#edit,#delete").attr("disabled", true) : $j("#edit,#delete").attr("disabled", false); 
			}
		});
	
		$j("#usergrid").jqGrid('navGrid', '#userpager', {
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
		
		$j("#usergrid").jqGrid('filterToolbar', {
			stringResult : true,
			searchOnEnter : true,
			defaultSearch : "cn"
		});		

	});
function setUserSelected(){
	selectedId = null;
	selectedId = $j('input[name=radio_usergrid]:checked').val();
	(selectedId==null) ? $j("#edit,#delete").attr("disabled", true) : $j("#edit,#delete").attr("disabled", false);          
}
</script>
	<div id="gridwrapper">
		<button id="add" class="button" onclick="KWTRDI.ajaxLoad('/dhis/adduser');" style="margin-left:25px;">
			Add
		</button>
		<button id="edit" disabled="disabled" class="button" onclick="KWTRDI.ajaxEdit('/dhis/edituser',selectedId);" style="margin-left:10px;">
			Edit
		</button>
		<button id="delete" disabled="disabled" class="button" onclick="KWTRDI.ajaxEdit('/dhis/deleteuser',selectedId);" style="margin-left:10px;">
			Enable/Disable
		</button>
		<br clear="all" />
		<div id='jqusergrid' style="width:95%;margin:auto;">
			<table id='usergrid' style="margin:auto;"></table>
			<div id='userpager'></div>
		</div>
	</div>