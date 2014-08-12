var Video = function(options){
	var self = {};
	var settings = {}
	
	$.extend(settings, options);
	
	setInterval('statusSender()', 1000);
	
	self.putMsg = function(msg){
		try{
			var command = [msg.target[0].class];
		}catch(e){
			command = $(msg.currentTarget).attr('class').split(" ");
		}
		var content = msg.content;
		for(i=0;i<command.length;i++){
			switch(command[i]) { 
				case 'play':
					document.getElementById('video1').play(1);
	  				break;
	  			case 'pause':
					document.getElementById('video1').play(0);
	  				break;
				default:
					console.log('unhandled msg in video.js : '+command); 
			}
		}
	}
	
	
	return self;
}

function statusSender() {
		var time = document.getElementById('video1').playPosition;
		eddie.putLou('', 'videostatus('+time+')');
	}