var Hbbtvmanager = function(options){
	var self = {};
	var settings = {};
	var hbbtvobject = $('hbbtvobject');
	var hbbtvconfig = $('hbbtvconfig');
	
	$.extend(settings, options);
	
	eddie.log('hbbtv remote started');
	eddie.log('object='+hbbtvobject);
	
    
	document.addEventListener("keydown", keyPressed, false);

	return self;
}

function keyPressed(e) {
	eddie.log('keypressed='+e.keyCode);
	eddie.putLou('', 'keypressed('+e.keyCode+')');
}