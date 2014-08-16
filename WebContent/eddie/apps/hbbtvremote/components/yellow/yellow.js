var Yellow = function(options){
	var self = {};
	var settings = {}
	
	$.extend(settings, options);
	
	self.putMsg = function(msg){
		try{
			var command = [msg.target[0].class];
		}catch(e){
			command = $(msg.currentTarget).attr('class').split(" ");
		}
		var content = msg.content;
		for(i=0;i<command.length;i++){
			switch(command[i]) { 
				case 'show':
					$('#yellow').css('display','inline');
	  				break;
				case 'hide':
					$('#yellow').css('display','none');
	  				break;
	  			case 'newtop':
					$('#yellow').css('top',content);
	  				break;
	  			case 'newleft':
					$('#yellow').css('left',content);
	  				break;
	  			case 'newwidth':
					$('#yellow').css('width',content);
	  				break;
	  		    case 'newheight':
					$('#yellow').css('height',content);
	  				break;
				default:
					console.log('unhandled msg in yellow.js : '+command); 
			}
		}
	}
	

	return self;
}