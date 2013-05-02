var checked=false;
var utils = {
		chkaBox : function(checkboxid){
	var thecheckbox= document.getElementById(checkboxid);
 	if (checked == false){
 		checked = true
    }
    else{
    	checked = false
    }
	thecheckbox.checked = checked;
},
	chkBox : function(formId){
		var isForm= document.getElementById(formId);
	 	if (checked == false){
           checked = true
        }
        else{
          checked = false
        }
		for (var i =0; i < isForm.elements.length; i++){
		 	isForm.elements[i].checked = checked;
		}
	},
	
	getChecked : function(formId){
		var checkedArray = new Array();
		var isForm= document.getElementById(formId);
		for (var i =0; i < isForm.elements.length; i++){
		 	if(isForm.elements[i].checked){
		 		if(isForm.elements[i].id != 'chkall'){
		 			checkedArray.push(isForm.elements[i].id);
		 		}
		 	}
		}
		return checkedArray.toString();
	},
	
	rand : function(){
		var randomnumber=Math.floor(Math.random()*11);
		return randomnumber;
	},
	
	time : function (){
            var hhmmss = new Date();
            var hh= hhmmss.getHours();
            var mm= hhmmss.getMinutes();
            var ss= hhmmss.getSeconds();
            var timeval = hh*10000+mm*100+ss;
            return timeval;
    }                                                                                   
}