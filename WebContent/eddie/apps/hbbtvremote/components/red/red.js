var Red = function(options){
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
					$('#red').css('display','inline');
	  				break;
				case 'hide':
					$('#red').css('display','none');
	  				break;
	  			case 'newtop':
					$('#red').css('top',content);
	  				break;
	  			case 'newleft':
					$('#red').css('left',content);
	  				break;
	  		    case 'newwidth':
					$('#red').css('width',content);
	  				break;
	  		    case 'newheight':
					$('#red').css('height',content);
	  				break;
				default:
					console.log('unhandled msg in red.js : '+command); 
			}
		}
	}
	

	return self;
}