import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class CtrlPanelAnalyse {
	private ClickButton uploadButton;
    public CtrlPanelAnalyse() {
    	this.uploadButton=new ClickButton(new Rectangle(800,550,100,50),"Upload",new Color(0,255,0),20);
    }
    public void onClick(int mx,int my,int mb){
    	if(uploadButton.onClick(mx,my,mb)){
    		uploadFile();
    	}
    }
    
    public void uploadFile(){
    	JFileChooser fc=new JFileChooser();
    	fc.setFileFilter(new FileExtensionFilter(".pgn"));
    	int returnVal=fc.showOpenDialog(null);
    	if(returnVal==JFileChooser.APPROVE_OPTION){
    		System.out.println(fc.getSelectedFile().getName());
    	}
    }
    public void reDraw(Graphics dbg){
    	uploadButton.drawButton(dbg);
    }
    public boolean collidePoint(int cx,int cy, int px, int py, int wid, int len){
    	if(cx>=px && cx<=wid && cy>=py && cy<=len){
    		return true;
    	}
    	return false;
    }
}