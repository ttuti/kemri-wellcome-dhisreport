/**
 *Script : dimbox.js
 *
 *Purpose : Create a div with a lightbox effect
 *
 *Date Created : 27-05-2009
 *Author : Paul Kevin
 */
var dimBox = {
    settings: {
        //inner div content
        content: "",
		header : "<div style='width:100%;'><div style=' float:right; cursor:pointer;' onclick='dimBox.closeDimmer()'>CLOSE</div></div>"
    },
    
    //Function to load. Should be loaded as page loads
    loadDimBox: function(width){
        var dimPageDiv = document.createElement('div');
        var myWidth = 0, myHeight = 0;
        dimPageDiv.id = "light";
        dimPageDiv.className = "white_content";
        dimPageDiv.style.marginLeft = (100 - width)/2+"%";
        dimPageDiv.style.marginRight = (100 - width)/2+"%";
        dimPageDiv.style.width = width+"%";
		dimPageDiv.style.display = "none";
		dimPageDiv.style.position = "absolute";
		dimPageDiv.style.padding = "10px";
		dimPageDiv.style.border ="10px solid #064F9F";
		dimPageDiv.style.background = "#ffffff";
		dimPageDiv.style.zIndex="999";
        document.body.appendChild(dimPageDiv);
        dimPageDiv.innerHTML = dimBox.settings.header + "<br/>" + dimBox.settings.content;
        
        var dimfade = document.createElement('div');
        dimfade.id = "fade";
        dimfade.className = "black_overlay";
        if (typeof(window.innerWidth) == 'number') {
            //Non-IE
            myWidth = window.innerWidth;
            myHeight = window.innerHeight;
            
        }
        else 
            if (document.documentElement &&
            (document.documentElement.clientWidth ||
            document.documentElement.clientHeight)) {
                //IE 6+ in 'standards compliant mode'
                myWidth = document.documentElement.clientWidth;
                myHeight = document.documentElement.clientHeight;
            }
            else 
                if (document.body && (document.body.clientWidth || 
					document.body.clientHeight)) {
                    //IE 4 compatible
                    myWidth = document.body.clientWidth;
                    myHeight = document.body.clientHeight;
                }
        var browser=navigator.appName;
        if (browser == "Microsoft Internet Explorer") {
        	dimfade.style.height = myHeight + "px";
        }
        else{
        	dimfade.style.height =  "100%";
        }
        dimfade.style.width = "100%";
        dimPageDiv.style.top = (myHeight/6)+"px";
        document.body.appendChild(dimfade);
    },
    
    showDimmer: function(){
        document.getElementById('light').style.display = 'block';
        document.getElementById('fade').style.display = 'block';
    },
    
    closeDimmer: function(){
        document.getElementById('light').style.display = 'none';
        document.getElementById('fade').style.display = 'none';
		var dimPageDiv = document.getElementById('light');
		var innerDiv = document.getElementById('fade');
		document.body.removeChild(dimPageDiv);
		document.body.removeChild(innerDiv);
    }
};


var editBox = {
    settings: {
        //inner div content
        content: "",
		header : "<div style='width:100%;'><div style=' float:right; cursor:pointer;' onclick='editBox.closeDimmer()'>CLOSE</div></div>"
    },
    
    //Function to load. Should be loaded as page loads
    loadDimBox: function(width){
        var dimPageDiv = document.createElement('div');
        var myWidth = 0, myHeight = 0;
        dimPageDiv.id = "editlight";
        dimPageDiv.className = "edit_white_content";
        dimPageDiv.style.marginLeft = (100 - width)/2+"%";
        dimPageDiv.style.marginRight = (100 - width)/2+"%";
        dimPageDiv.style.width = width+"%";
		dimPageDiv.style.display = "none";
		dimPageDiv.style.position = "absolute";
		dimPageDiv.style.padding = "10px";
		dimPageDiv.style.border ="10px solid #064F9F";
		dimPageDiv.style.background = "#ffffff";
		dimPageDiv.style.zIndex="9999";
        document.body.appendChild(dimPageDiv);
        dimPageDiv.innerHTML = editBox.settings.header + "<br/>" + editBox.settings.content;
        
        var dimfade = document.createElement('div');
        dimfade.id = "editfade";
        dimfade.className = "edit_black_overlay";
        if (typeof(window.innerWidth) == 'number') {
            //Non-IE
            myWidth = window.innerWidth;
            myHeight = window.innerHeight;
            
        }
        else 
            if (document.documentElement &&
            (document.documentElement.clientWidth ||
            document.documentElement.clientHeight)) {
                //IE 6+ in 'standards compliant mode'
                myWidth = document.documentElement.clientWidth;
                myHeight = document.documentElement.clientHeight;
            }
            else 
                if (document.body && (document.body.clientWidth || 
					document.body.clientHeight)) {
                    //IE 4 compatible
                    myWidth = document.body.clientWidth;
                    myHeight = document.body.clientHeight;
                }
        var browser=navigator.appName;
        if (browser == "Microsoft Internet Explorer") {
        	dimfade.style.height = myHeight + "px";
        }
        else{
        	dimfade.style.height =  "100%";
        }
        dimfade.style.width = "100%";
        dimPageDiv.style.top = (myHeight/6)+"px";
        document.body.appendChild(dimfade);
    },
    
    showDimmer: function(){
        document.getElementById('editlight').style.display = 'block';
        document.getElementById('editfade').style.display = 'block';
    },
    
    closeDimmer: function(){
        document.getElementById('editlight').style.display = 'none';
        document.getElementById('editfade').style.display = 'none';
		var dimPageDiv = document.getElementById('editlight');
		var innerDiv = document.getElementById('editfade');
		document.body.removeChild(dimPageDiv);
		document.body.removeChild(innerDiv);
    }
};