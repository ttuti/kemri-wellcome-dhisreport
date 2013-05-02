
					var time;
					var transparency = 0;
					var PA = new start();
					

					function start(){

						var background = document.getElementById("backgroundPopup").style.display="block";
						var background = document.getElementById("popupBlocked").style.display="block";
						document.getElementById("backgroundPopup").onclick= function(){close();};
						document.getElementById("popupBlockedClose").onclick= function(){close();};   
						time = setInterval('fadeIn()',100);

					}

					function fadeIn() {
						  //
						  //incrementamos el valor
						  transparency += 5; 
						  //si termino la trnsicion borramos el intervalo
						  transparency = (transparency == 100) ? clearInterval(time) : transparency;
						  //seteamos al div como objeto para poder usarlo
						  obj = document.getElementById('backgroundPopup');
						  //programamos cada navegador por separado
						  if (document.all){ 
						    //esto es para IE, como siempre hay q programarlo a parte
						    obj.style.filter = 'alpha(opacity='+transparency+')';
						  }else{
						    // Safari 1.2, posterior Firefox y Mozilla, CSS3
						    obj.style.opacity = transparency /100;
						    
						    // anteriores Mozilla y Firefox
						    obj.style.MozOpacity = transparency /100;
						    
						    // Safari anterior a 1.2, Konqueror
						    obj.style.KHTMLOpacity = transparency /100;  
						  } 
					}

					function close() {

						document.getElementById("backgroundPopup").style.display="none";
						document.getElementById("popupBlocked").style.display="none";
					}
