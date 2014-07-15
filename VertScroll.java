import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
public class VertScroll {
	private Rectangle out,view;
	private int imLen;
	private int barLen;
	private int barTop;
	private int diff;
	private boolean scrollStart;
    public VertScroll(Rectangle out, Rectangle view) {
    	this.out=out;
    	this.view=view;
    	this.imLen=(int)view.getHeight();
    	this.barLen=(int)((view.getHeight()/imLen)*(view.getHeight()));
    	this.barTop=(int)view.getY();
    	this.diff=0;
    	this.scrollStart=false;
    }
    public int getViewTop(){
    	if(imLen<=(int)view.getHeight()){
    		return (int)(view.getY())+30;
    	}
    	return (int)(view.getY()-(double)(barTop-view.getY())/view.getHeight()*imLen)+30;
    }
    public void setImLen(int val){

    	if(val<=(int)view.getHeight()){
    		imLen=(int)view.getHeight();
    	}
    	else{
    		imLen=val;
    	}
    	barLen=(int)((view.getHeight()/imLen)*(view.getHeight()));
    }
    public void drawBar(Graphics dbg){
    	dbg.setColor(new Color(255,0,0));
    	dbg.fillRect((int)view.getX(),(int)view.getY(),(int)view.getWidth(),(int)view.getHeight());
    	dbg.setColor(new Color(50,50,50));
    	dbg.fillRect((int)(view.getX()+view.getWidth()),(int)(view.getY()),20,(int)(view.getHeight()));
    	dbg.setColor(new Color(255,255,150));
    	dbg.fillRect((int)(view.getX()+view.getWidth()),barTop,20,barLen);
    }
    public boolean collidePoint(int mx, int my, int px, int py, int width, int height){
    	if(mx>=px && mx <=px+width && my >=py && my<=py+height){
    		return true;
    	}
    	return false;
    }
    public void onPress(int mx, int my, int mb){
    	if(collidePoint(mx,my,(int)(view.getX()+view.getWidth()),barTop,30,barLen)){
    		scrollStart=true;
    		diff=my-barTop;
    	}
    	if(scrollStart){
    		barTop=my-diff;
    		barTop=Math.min(barTop,(int)(view.getY()+view.getHeight())-barLen);
    		barTop=Math.max(barTop,(int)(view.getY()));
    	}
    }
    public void onRelease(int mx,int my, int mb){
    	if(scrollStart){
    		scrollStart=false;
    		barTop=my-diff;
    		barTop=Math.min(barTop,(int)(view.getY()+view.getHeight())-barLen);
    		barTop=Math.max(barTop,(int)(view.getY()));
    	}
    }
    public void incBarTop(double val){
    	if(imLen>view.getHeight()){
    		barTop+=(int)(val/imLen*view.getHeight());
    		barTop=Math.min(barTop,(int)(view.getY()+view.getHeight())-barLen);
    		barTop=Math.max(barTop,(int)(view.getY()));
    	}
    }
    public void drawPadding(Graphics dbg, Color col){
    	dbg.setColor(col);
    	dbg.fillRect((int)(view.getX()),(int)(out.getY()),(int)(view.getWidth()),(int)(view.getY()-out.getY()));
    	dbg.fillRect((int)view.getX(),(int)(view.getY()+view.getHeight()),(int)view.getWidth(),(int)(out.getY()+out.getHeight()-view.getY()-view.getHeight()));
    }
    
    
    
    
    
}