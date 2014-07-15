import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
public class Piece {
	private char name;
	private boolean color;
	private boolean inPlay;
	private int x,y;
    public Piece(char name, boolean color, boolean inPlay) {
    	this.name=name;
    	this.color=color;
    	this.inPlay=inPlay;
    	this.x=x;
    	this.y=y;
    }
    public char getName(){
    	return name;
    }
    public boolean getColor(){
    	return color;
    }
    public boolean inPlay(){
    	return inPlay;
    }
    /*public int getX(){
    	return x;
    } 
    public int getY(){
    	return y;
    }
    public void setX(int val){
    	x=val;
    }
    public void setY(int val){
    	y=val;
    }*/
    
    
}