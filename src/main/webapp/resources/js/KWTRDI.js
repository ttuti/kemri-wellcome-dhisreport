// KWTRDI.js
var timeout = 500;
var closetimer = 100;
var ddmenuitem = 0;
var globalRowId = 0;

var KWTRDI = {
	extension : '.php',
	
	ajaxLoad : function(url) {
		$j('#content').load(prefix + url + KWTRDI.extension);
	},	
	ajaxEdit : function(url, param) {
		$j('#content').load(
				prefix + url + KWTRDI.extension + "?id=" + param);
	},
	edit: function(id,reportId) {
		$j("#reportDefinition_edit" + id).hide();

        if ($j("#reportDefinition_save" + id).html() == "") {
            $j("#reportDefinition_save" + id).append('<a onclick="KWTRDI.save(' + id + ','+reportId+')" class="button">Save</a>');

            var strQuery = $j("#reportDefinition_query" + id).text();
            $j("#reportDefinition_query" + id).html('<textarea id="textarea_query' + id + '" cols="60" style="height:100px;">' + strQuery + '</textarea>');
        }
        else {
        	$j("#reportDefinition_save" + id).show();
            var strQuery = $j("#reportDefinition_query" + id).text();
            $j("#reportDefinition_query" + id).html('<textarea id="textarea_query' + id + '" cols="60" style="height:100px;">' + strQuery + '</textarea>');
        }
    },
    save: function(id,reportId) {
        var newQuery = $j("#textarea_query" + id).val();
        $j.ajax({
            type: "GET",
            url: prefix + "/reportdefinition/editDataValueTemplate"+KWTRDI.extension,
            data: ({
                dataValueTemplate_id: id,
                dataValueTemplate_query: newQuery
            }),
            success: function() {
            	$j('#content').load(
        				prefix + "/reportdefinition/editReportDefinition" + KWTRDI.extension + "?id="+reportId);
            },
            error: function(xhr, ajaxOptions, thrownError) {
                alert(thrownError);
            }
        });
    },
	ajaxEnableUser : function(url, param) {
		new Ajax.Request(prefix + url + KWTRDI.extension, {
			method : 'get',
			parameters : {
				'userId' : '' + param + ''
			}
		});
	},
	ajaxDisableUser : function(url, param) {
		new Ajax.Request(prefix + url + KWTRDI.extension, {
			method : 'get',
			parameters : {
				'userId' : '' + param + ''
			}
		});
	}
};