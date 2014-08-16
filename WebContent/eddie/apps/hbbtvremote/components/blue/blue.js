var Blue = function(options){
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
					$('#blue').css('display','inline');
	  				break;
				case 'hide':
					$('#blue').css('display','none');
	  				break;
	  			case 'newtop':
					$('#blue').css('top',content);
	  				break;
	  			case 'newleft':
					$('#blue').css('left',content);
	  				break;
	  		    case 'newwidth':
					$('#blue').css('width',content);
	  				break;
	  		    case 'newheight':
					$('#blue').css('height',content);
	  				break;
				default:
					console.log('unhandled msg in blue.js : '+command); 
			}
		}
	}
	

	return self;
}