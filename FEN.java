import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
public class FEN {
	private Piece[][] position;
	private boolean activeColor;
	private boolean[] castling;
	private String enPassant;
	private int halfMove;
	private int fullMove;
	//private Piece[] whitePieces,blackPieces;
	//private int whiteCount,blackCount;
    public FEN(String fenCode) {
    	String[] info = fenCode.split(" ");
    	this.position=parsePosition(info[0]);
    	if(info[1].equals("w")){
    		this.activeColor=true;
    	}
    	else{
    		this.activeColor=false;
    	}
    	this.castling=new boolean[4];
    	for (int i=0;i<4;i++){
    		this.castling[i]=false;
    	}
    	for (int i=0;i<info[2].length();i++){
    		if(info[2].charAt(i)=='-'){
    			break;
    		}
    		else if(info[2].charAt(i)=='K'){
    			this.castling[0]=true;
    		}
    		else if(info[2].charAt(i)=='Q'){
    			this.castling[1]=true;
    		}
    		else if(info[2].charAt(i)=='k'){
    			this.castling[2]=true;
    		}
    		else if(info[2].charAt(i)=='q'){
    			this.castling[3]=true;
    		}
    	}
    	this.enPassant=info[3];
    	this.halfMove=Integer.parseInt(info[4]);
    	this.fullMove=Integer.parseInt(info[5]);
    	/*this.whitePieces=new Piece[16];
    	this.blackPieces=new Piece[16];
    	this.whiteCount=0;
    	this.blackCount=0;*/
    }
    public Piece[][] getPos(){
    	return position;
    }
    public boolean getColor(){
    	return activeColor;
    }
    public String getEnPassant(){
    	return enPassant;
    }
    public boolean[] getCastling(){
    	return castling;
    }
    public Piece[][] parsePosition(String pos){
    	String[] rows = pos.split("/");
    	Piece [][] board=new Piece[8][8];
    	int x=0,y=0;
    	for (int i=0;i<8;i++){
    		//System.out.println("Rows: "+rows[i]);
    		for (int j=0;j<rows[i].length();j++){
    			int num=(int)(rows[i].charAt(j));
    			//System.out.println(rows[i].charAt(j));
    			//System.out.println(x+" "+y);
    			if(num>=48 && num<=57){
    				for(int k=0;k<num-48;k++){
    					//System.out.println(x+" "+y);
    					board[x][y]=null;
    					y++;
    				}
    			}
    			else{
    				boolean color;
    				char p=rows[i].charAt(j);
    				if (p>=65 && p<=90){
    					color=true;
    				}
    				else{
    					color=false;
    				}
    				Piece piece=new Piece(rows[i].charAt(j),color,true);
    				board[x][y]=piece;
    				/*if(color && whiteCount<16){
    					whitePieces[whiteCount]=piece;
    					whiteCount++;
    				}
    				else if(!color && blackCount<16){
    					blackPieces[blackCount]=piece;
    					blackCount++;
    				}*/
    				y++;
    			}
    		}
    		x++;
    		y=0;
    	}
    	return board;
    }
    /*public Piece[] getWhitePieces(){
    	return whitePieces;
    }
    public Piece[] getBlackPieces(){
    	return blackPieces;
    }*/
}    