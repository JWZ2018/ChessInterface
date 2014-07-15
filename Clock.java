import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class Clock {
	private long whiteTime,blackTime,whiteLastMove,blackLastMove;
	private long delay;
    public Clock(long whiteTime,long blackTime,long delay) {
    	this.whiteTime=whiteTime*60;
    	this.blackTime=blackTime*60;
    	this.delay=delay;
    }
    public void initialWhite(long last){
    	whiteLastMove=last;
    }
    public void initialBlack(long last){
    	blackLastMove=last;
    }
    public void setWhiteLastMove(long last){
    	whiteTime-=convertTime(last-blackLastMove);
    	whiteLastMove=last;
    	whiteTime+=delay;
    }
    public void setBlackLastMove(long last){
    	blackTime-=convertTime(last-whiteLastMove);
    	blackLastMove=last;
    	blackTime+=delay;
    }
    public void setWhiteTime(long last){
    	//System.out.println("W "+whiteTime);
    	whiteTime-=convertTime(last-blackLastMove);
    	//System.out.println("W "+whiteTime);
    }
    public void setBlackTime(long last){
    	//System.out.println("B "+blackTime);
    	blackTime-=convertTime(last-whiteLastMove);
    	//System.out.println("B "+blackTime);
    }
    public long convertTime(long elapse){
		//System.out.println(elapse);
    	long sec=(long)(elapse/1000000000.0);
    	return sec;
    }
    public String formatTime(long time){
    	return (time/60)+":"+(time%60);
    	
    }
    public String getWhiteTime(long time, boolean active){
    	String ans;
    	if(active){
    		ans=formatTime(whiteTime-convertTime(time-blackLastMove));
    		//System.out.println("W active"+ans);
    		return ans;
    	}
    	ans=formatTime(whiteTime);
    	//System.out.println("W inactive"+ans);
    	return ans;
    }
    public String getBlackTime(long time, boolean active){
    	String ans;
    	if(active){
    		ans=formatTime(blackTime-convertTime(time-whiteLastMove));
    		//System.out.println("B active"+ans);
    		return ans;
    	}
    	ans=formatTime(blackTime);
    	//System.out.println("B inactive"+ans);
    	return ans;
    }
    
    
    
    
}