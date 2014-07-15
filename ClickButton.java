import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
public class ClickButton {
	private Rectangle box;
	private String text;
	private Color col;
	private int fontSize;
    public ClickButton(Rectangle box, String text,Color col,int fontSize) {
    	this.box=box;
    	this.text=text;
    	this.col=col;
    	this.fontSize=fontSize;
    }
    public void drawButton(Graphics dbg){
    	dbg.setColor(col);
    	dbg.fillRect((int)box.getX(),(int)box.getY(),(int)box.getWidth(),(int)box.getHeight());
    	dbg.setFont(new Font("Arial",Font.BOLD,fontSize));
    	dbg.setColor(new Color(0,0,0));
    	dbg.drawString(text,(int)box.getX()+5,(int)box.getY()+25);
    }
    public boolean onClick(int mx, int my, int mb){
    	if(collidePoint(mx,my, box.getX(),box.getY(),box.getWidth(),box.getHeight())){
    		return true;
    	}
    	return false;
    }
    
    public boolean collidePoint(int mx, int my, double px, double py, double width, double height){
    	if(mx>=px && mx <=px+width && my >=py && my<=py+height){
    		return true;
    	}
    	return false;
    }
}