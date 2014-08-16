/* 
* HelloworldApplication.java
* 
* Copyright (c) 2012 Noterik B.V.
* 
* This file is part of Lou, related to the Noterik Springfield project.
* It was created as a example of how to use the multiscreen toolkit
*
* Helloworld app is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Helloworld app is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Helloworld app.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.springfield.lou.application.types;
import java.util.Iterator;

import org.springfield.fs.Fs;
import org.springfield.fs.FsNode;
import org.springfield.hbbtvsupport.RemoteControl;
import org.springfield.lou.application.*;
import org.springfield.lou.screen.*;
import org.springfield.mojo.linkedtv.GAIN;

public class HbbtvremoteApplication extends Html5Application{
	
	private boolean gainenabled = false;
	private static String GAIN_ACCOUNT = "LINKEDTV-TEST";
	private static String videotime;
	private static GAIN gain = null;

	
 	public HbbtvremoteApplication(String id) {
		super(id); 
		if (gainenabled && gain==null) {
			gain = new GAIN(GAIN_ACCOUNT, id);
			if (gainenabled) gain.application_new();
		}
	}
	
    public void onNewScreen(Screen s) {
    	Capabilities cap = s.getCapabilities();
        loadStyleSheet(s, "generic");
        loadContent(s, "video");
        if (gainenabled) gain.screen_new(s.getId());
       
        int mode = s.getCapabilities().getDeviceMode();
        if (cap.getDeviceMode()==cap.MODE_HBBTV) {
        	s.setRole("mainscreen");
            s.setContent("video",getCEHtmlVideoTag());
    		this.componentmanager.getComponent("video").put("app", "play()");
            loadContent(s, "overlay");
            loadContent(s, "hbbtvmanager");
        } else {
        	String override = s.getParameter("role");
        	if (override!=null && override.equals("mainscreen")) {
            	s.setRole("mainscreen");
        		s.setContent("video",getHtml5VideoTag());
                loadContent(s, "overlay");
                loadContent(s, "hbbtvmanager");
        	} else {
            	System.out.println("controller");
            	s.setRole("controller"); 	
                loadContent(s, "controller");
        	}
        }
    }
    
    private String getHtml5VideoTag() {
		String body="<video id=\"video1\" autoplay controls preload=\"none\" data-setup=\"{}\">";
		String url = "http://stream9.noterik.com/progressive/stream9/domain/springfieldwebtv/user/admin/video/82/rawvideo/3/raw.mp4";
		body+="<source src=\""+url+"\" type=\"video/mp4\" /></video>";
		return body;
    }
    
    private String getCEHtmlVideoTag() {
		String url = "http://stream9.noterik.com/progressive/stream9/domain/springfieldwebtv/user/admin/video/82/rawvideo/3/raw.mp4";
		String body="<object type=\"video/mp4\" id=\"video1\" data=\""+url+"\" width=\"640\" height=\"480\"></object>";
		return body;
    }
    
    public void pause(Screen s) {
 		this.componentmanager.getComponent("video").put("app", "pause()");
        setContentAllScreensWithRole("mainscreen","overlay","PAUSE HIT AT "+videotime);
        if (gainenabled) gain.player_pause(s.getId(),"http://images1.noterik.com/euscreen/previewtool_screencast_beta.mp4", videotime);
    }
    
    public void play(Screen s) {
 		this.componentmanager.getComponent("video").put("app", "play()");
        setContentAllScreensWithRole("mainscreen","overlay","PLAY HIT AT "+videotime);
        if (gainenabled) gain.player_play(s.getId(),"http://images1.noterik.com/euscreen/previewtool_screencast_beta.mp4", videotime);
    }
    
    public void videostatus(Screen s,String content) {
        setContentAllScreensWithRole("controller","overlay","T="+content);
        videotime = content;
    }

    public void keypressed(Screen s,String content) {
    	try {
    		int keycode = Integer.parseInt(content);
    		switch (keycode) {
				case RemoteControl.REMOTEKEY_ENTER :
					hideObject(s);
				break;
				case RemoteControl.REMOTEKEY_RED :
					selectObject(s,"red");
					break;
				case RemoteControl.REMOTEKEY_GREEN :
					selectObject(s,"green");
					break;
				case RemoteControl.REMOTEKEY_YELLOW :
					selectObject(s,"yellow");
					break;
				case RemoteControl.REMOTEKEY_BLUE :
					selectObject(s,"blue");
					break;
				case RemoteControl.REMOTEKEY_RIGHT :
					moveObjectRight(s);
					break;
				case RemoteControl.REMOTEKEY_LEFT :
					moveObjectLeft(s);
					break;
				case RemoteControl.REMOTEKEY_UP :
					moveObjectUp(s);
					break;
				case RemoteControl.REMOTEKEY_DOWN :
					moveObjectDown(s);
					break;
				case RemoteControl.REMOTEKEY_1 :
				case RemoteControl.REMOTEKEY_2 :
				case RemoteControl.REMOTEKEY_3 :
				case RemoteControl.REMOTEKEY_4 :
				case RemoteControl.REMOTEKEY_5 :
				case RemoteControl.REMOTEKEY_6 :
				case RemoteControl.REMOTEKEY_7 :
				case RemoteControl.REMOTEKEY_8 :
				case RemoteControl.REMOTEKEY_9 :
					sizeObject(s,10-(RemoteControl.REMOTEKEY_9-keycode));
					break;
				case RemoteControl.REMOTEKEY_0 :
					moveObject(s);
					break;
    		}
    	} catch(Exception e) {
    		log("illigal keyPressed");
    	}
    }
    
    private void selectObject(Screen s,String color) {
    	s.setProperty("active", color);
    	
    	Object loaded = s.getProperty(color);
    	if (loaded==null) {
            loadContent(s,color);
    		this.componentmanager.getComponent(color).put("app", "show()");
    		s.setProperty(color,"true");
    		
    		// ugly but want simple way to set the pos for later
    		if (color.equals("red")) { s.setProperty("redtop",150); s.setProperty("redleft",150);
    		} else if (color.equals("green")) { s.setProperty("greentop",150); s.setProperty("greenleft",400);
    		} else if (color.equals("yellow")) { s.setProperty("yellowtop",300); s.setProperty("yellowleft",150);
    		} else if (color.equals("blue")) { s.setProperty("bluetop",300); s.setProperty("blueleft",400); }

    		}
    }
    
    private void hideObject(Screen s) {
    	Object o = s.getProperty("active");
    	if (o!=null) {
    		s.setProperty("active", null);	
    		String color = (String)o;
    		this.componentmanager.getComponent(color).put("app", "hide()");
    		s.setProperty(color,null);
    	}
    }
    
    private void moveObject(Screen s) {
    	Object o = s.getProperty("active");
    	if (o!=null) {	
    		String color = (String)o;
    		Object oncontroller = s.getProperty(color+"controller");
    		if (oncontroller==null) {
    			loadContentAllScreensWithRole("controller", color);
    			Iterator<String> it = screenmanager.getScreens().keySet().iterator();
    			while(it.hasNext()){
    				String next = (String) it.next();
    				Screen cur = screenmanager.get(next);
    				if (cur.getRole().equals("controller")) {
    					cur.putMsg(color,"app","show()");
    				} else {
    					cur.putMsg(color,"app","hide()");
    				}
    			}
    			s.setProperty(color+"controller","true");
    		} else {
    			removeContentAllScreens(color);
    			loadContent(s,color);
    			this.componentmanager.getComponent(color).put("app", "show()");
    			s.setProperty(color+"controller",null);	
    		}
    	}
    }
    
    private void sizeObject(Screen s,int size) {
    	log("resize "+size);
    	Object o = s.getProperty("active");
    	if (o!=null) {	
    		String color = (String)o;
    		int newwidth = size*32;
    		int newheight = size*24;
    		this.componentmanager.getComponent(color).put("app", "newwidth("+newwidth+"px)");
    		this.componentmanager.getComponent(color).put("app", "newheight("+newheight+"px)");
    	}
    }
    
    private void moveObjectRight(Screen s) {
    	Object o = s.getProperty("active");
    	if (o!=null) {
    		String color = (String)o;
    		int newleft = (Integer)s.getProperty(color+"left");
    		newleft += 40;
    		s.setProperty(color+"left",newleft);
    		this.componentmanager.getComponent(color).put("app", "newleft("+newleft+"px)");
    	}
    }
    
    private void moveObjectLeft(Screen s) {
    	Object o = s.getProperty("active");
    	if (o!=null) {
    		String color = (String)o;
    		int newleft = (Integer)s.getProperty(color+"left");
    		newleft -= 40;
    		s.setProperty(color+"left",newleft);
    		this.componentmanager.getComponent(color).put("app", "newleft("+newleft+"px)");
    	}
    }
    
    private void moveObjectUp(Screen s) {
    	Object o = s.getProperty("active");
    	if (o!=null) {
    		String color = (String)o;
    		int newtop = (Integer)s.getProperty(color+"top");
    		newtop -= 40;
    		s.setProperty(color+"top",newtop);
    		this.componentmanager.getComponent(color).put("app", "newtop("+newtop+"px)");
    	}
    }
    
    private void moveObjectDown(Screen s) {
    	Object o = s.getProperty("active");
    	if (o!=null) {
    		String color = (String)o;
    		int newtop = (Integer)s.getProperty(color+"top");
    		newtop += 40;
    		s.setProperty(color+"top",newtop);
    		this.componentmanager.getComponent(color).put("app", "newtop("+newtop+"px)");
    	}
    }
    
    


}
