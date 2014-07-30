import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
public class Tabs {
	private ClickButton[]tabs;
	private int tx,ty,wid,len;
	private int active;
    public Tabs(String[]tabNames,int tx,int ty,int width,int length,int active) {
    	this.tx=tx;
    	this.ty=ty;
    	this.wid=(int)((double)width/tabNames.length);
    	this.len=length;
    	this.tabs=new ClickButton[tabNames.length];
    	for(int i=0;i<tabs.length;i++){
    		tabs[i]=new ClickButton(new Rectangle(tx+i*wid,ty,wid,len),tabNames[i],new Color(200,200,200),12);
    	}
    	this.active=active;
    }
    public void drawTabs(Graphics dbg){
    	for(int i=0;i<tabs.length;i++){
    		tabs[i].drawButton(dbg);
    	}
    	dbg.setColor(new Color(255,0,0));
    	for(int i=0;i<tabs.length;i++){
    		dbg.drawRect(tx+i*wid,ty,wid,len);
    	}
    	dbg.setColor(new Color(0,255,0));
    	dbg.drawRect(tx+wid*active,ty,wid,len);
    }
    public void onClick(int mx, int my, int mb){
    	for(int i=0;i<tabs.length;i++){
    		if(tabs[i].onClick(mx,my,mb)){
    			active=i;
    			break;
    		}
    	}
    }
    public int getActive(){
    	return active;
    }
    
}