import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class InputBox {
	private String title;
	private Rectangle textBox;
	private String text;
	private boolean isActive;
	private int min,max;
	private String units;
	private int len;
    public InputBox(String title, Rectangle textBox,String text,boolean isActive,int min, int max, String units,int len) {
    	this.title=title;
    	this.textBox=textBox;
    	this.isActive=isActive;
    	this.text=text;
    	this.min=min;
    	this.max=max;
    	this.units=units;
    	this.len=len;
    }
    public void drawBox(Graphics dbg){
    	dbg.setColor(new Color(255,155,162));
    	dbg.fillRect((int)textBox.getX(),(int)textBox.getY(),(int)textBox.getWidth(),(int)textBox.getHeight());
    	dbg.setColor(new Color(0,0,0));
    	dbg.setFont(new Font("Arial",Font.BOLD,20));
    	dbg.drawString(title,(int)textBox.getX(),(int)textBox.getY()-10);
    	dbg.drawString(text, (int)textBox.getX()+5,(int)textBox.getY()+25);
    	dbg.drawString(units,(int)(textBox.getX()+textBox.getWidth())+5,(int)textBox.getY()+30);
    	if(isActive){
    		dbg.setColor(new Color(255,0,0));
    		dbg.drawRect((int)textBox.getX(),(int)textBox.getY(),(int)textBox.getWidth(),(int)textBox.getHeight());
    	}
    }
    public boolean collidePoint(int mx, int my, double px, double py, double width, double height){
    	if(mx>=px && mx <=px+width && my >=py && my<=py+height){
    		return true;
    	}
    	return false;
    }
    public boolean onClick(int mx, int my, int mb){
    	if(collidePoint(mx,my,textBox.getX(),textBox.getY(),textBox.getWidth(),textBox.getHeight())){
    		isActive=true;
    	}
    	return isActive;
    }
    public void onType(char key){
    	if(isActive){
    		if(key>=min && key<=max){
    			text+=key;
    		}
    	}
    }
    public void setActive(boolean val){
    	isActive=val;
    }
    public String getTitle(){
    	return title;
    }
    public boolean getActive(){
    	return isActive;
    }
    public void onKeyPress(int key){
    	if(isActive){
    		if(key==8 && text.length()>0){
	    		text=text.substring(0,text.length()-1);
	    	}
    	}
    	
    }
    public String getText(){
    	return text;
    }
}