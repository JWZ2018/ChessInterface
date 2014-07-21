import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class BoardAnalyse {
	private int top,bottom,left,right;
	private Image[] piecePics;
	private FEN pos;
    private Piece [][]b;
    private boolean activeColor;
    private CtrlPanelAnalyse ctrlPanelAnalyse;
    private String curMove;
    private int curMoveIndex;
    public BoardAnalyse() {
    	this.top=60;
    	this.bottom=660;
    	this.left=10;
    	this.right=610;
    	this.piecePics=new Image[12];
    	piecePics[0]=new ImageIcon("PieceGIF//black-king.gif").getImage();
    	piecePics[1]=new ImageIcon("PieceGIF//black-queen.gif").getImage();
    	piecePics[2]=new ImageIcon("PieceGIF//black-rook.gif").getImage();
    	piecePics[3]=new ImageIcon("PieceGIF//black-bishop.gif").getImage();
    	piecePics[4]=new ImageIcon("PieceGIF//black-knight.gif").getImage();
    	piecePics[5]=new ImageIcon("PieceGIF//black-pawn.gif").getImage();
    	piecePics[6]=new ImageIcon("PieceGIF//white-king.gif").getImage();
    	piecePics[7]=new ImageIcon("PieceGIF//white-queen.gif").getImage();
    	piecePics[8]=new ImageIcon("PieceGIF//white-rook.gif").getImage();
    	piecePics[9]=new ImageIcon("PieceGIF//white-bishop.gif").getImage();
    	piecePics[10]=new ImageIcon("PieceGIF//white-knight.gif").getImage();
    	piecePics[11]=new ImageIcon("PieceGIF//white-pawn.gif").getImage();
    	this.activeColor=true;
    	this.pos=new FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    	this.b=pos.getPos();
    	this.ctrlPanelAnalyse=new CtrlPanelAnalyse(b);
    	this.curMoveIndex=0;
    	this.curMove=ctrlPanelAnalyse.getCurMove();
    }
    public void drawBackground(Graphics dbg){
    	dbg.setColor(new Color(255,255,255));
    	dbg.fillRect(0,0,1220,630);
    	dbg.setColor(new Color(0,0,0));
    	dbg.drawRect(10,30,600,600);
    }
    
    public void drawBoard(Graphics dbg){
    	for(int i=left;i<right;i+=75){
    		for(int j=top;j<bottom;j+=75){
    			//System.out.println(i+" "+j);
    			if((i+j)%2==0){
    				dbg.setColor(new Color(255,255,153));
    				dbg.fillRect(i,j,i+75,j+75);
    			}
    			else{
    				dbg.setColor(new Color(153,76,0));
    				dbg.fillRect(i,j,i+75,j+75);
    			}
    			
    		}
    	}
    }
    public void drawPadding(Graphics dbg){
    	dbg.setColor(new Color(255,255,255));
    	dbg.fillRect(0,30,610,35);
    	dbg.fillRect(0,660,610,35);
    	dbg.fillRect(610,30,630,670);
    }
    public Image getPieceImage(Piece p){
    	switch (p.getName()){
    		case 'k':
    			return piecePics[0];
    		case 'q':
    			return piecePics[1];
    		case 'r':
    			return piecePics[2];
    		case 'b':
    			return piecePics[3];
    		case 'n':
    			return piecePics[4];
    		case 'p':
    			return piecePics[5];
    		case 'K':
    			return piecePics[6];
    		case 'Q':
    			return piecePics[7];
    		case 'R':
    			return piecePics[8];
    		case 'B':
    			return piecePics[9];
    		case 'N':
    			return piecePics[10];
    		case 'P':
    			return piecePics[11];
    		default:
    			return piecePics[12];
    			
    	}
    }
    public void setPiecesWhite(Graphics dbg,Piece[][]pos){
    	for(int i=0;i<8;i++){
    		for (int j=0;j<8;j++){
    			if(pos[i][j]!=null){
    				dbg.drawImage(getPieceImage(pos[i][j]),left+75*j,top+75*i,null);
    			}
    		}
    	}
    }
    public void setPiecesBlack(Graphics dbg,Piece[][]pos){
    	for(int i=0;i<8;i++){
    		for (int j=0;j<8;j++){
    			if(pos[i][j]!=null){
    				dbg.drawImage(getPieceImage(pos[i][j]),left+75*(7-j),top+75*(7-i),null);
    			}
    		}
    	}
    }
    public boolean collidePoint(int cx,int cy, int px, int py, int wid, int len){
    	if(cx>=px && cx<=wid && cy>=py && cy<=len){
    		return true;
    	}
    	return false;
    }
     public void reDraw(Graphics dbg){
    	drawBackground(dbg);
    	drawBoard(dbg);
    	drawPadding(dbg);
    	if(ctrlPanelAnalyse.getFlip()){
    		setPiecesWhite(dbg,b);
    	}
    	else{
    		setPiecesBlack(dbg,b);
    	}
    	ctrlPanelAnalyse.reDraw(dbg);
    	
     }
     public String[] onClick(int mx, int my, int mb){
     	String[]res=new String[2];
     	ctrlPanelAnalyse.onClick(mx,my,mb);
     	if(curMoveIndex!=ctrlPanelAnalyse.getCurMoveIndex()){
     		curMoveIndex=ctrlPanelAnalyse.getCurMoveIndex();
     		curMove=ctrlPanelAnalyse.getCurMove();
     		activeColor=ctrlPanelAnalyse.getColor();
     		b=parsePosition(curMove);
     	}
     	res[0]="board-Analyse";
     	res[1]="ctrlPanel-Analyse";
     	return res;
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
}