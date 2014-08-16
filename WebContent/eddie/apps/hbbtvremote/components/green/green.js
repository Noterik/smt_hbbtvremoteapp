var Green = function(options){
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
					$('#green').css('display','inline');
	  				break;
				case 'hide':
					$('#green').css('display','none');
	  				break;
	  			case 'newtop':
					$('#green').css('top',content);
	  				break;
	  			case 'newleft':
					$('#green').css('left',content);
	  				break;
	  	  		case 'newwidth':
					$('#green').css('width',content);
	  				break;
	  		    case 'newheight':
					$('#green').css('height',content);
	  				break;
				default:
					console.log('unhandled msg in green.js : '+command); 
			}
		}
	}
	

	return self;
}