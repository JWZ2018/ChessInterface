import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
public class Instructions {

    public Instructions() {
    }
    public String[] onClick(int mx, int my, int mb){
    	String[]res=new String[2];
    	res[0]="board-Play";
    	res[1]="ctrlPanel-Play";
    	return res;
    }
    public void reDraw(Graphics dbg){
    	dbg.setColor(new Color(255,255,255));
    	dbg.fillRect(610,0,610,700);
    }
    
    
}