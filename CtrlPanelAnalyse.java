import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class CtrlPanelAnalyse {
	private ClickButton uploadButton;
	private ArrayList<String>whiteMoves,blackMoves;
	private String curFile;
	private MoveGetter getter;
	private Piece[][]board;
	private ArrayList<String>whiteFens;
	private ArrayList<String>blackFens;
	private String curMove;
	private int curMoveIndex;
	private boolean uploaded;
	private ClickButton forward,back;
	private boolean color;
	private boolean flip;
	private ClickButton flipButton;
    public CtrlPanelAnalyse(Piece[][] board) {
    	this.uploadButton=new ClickButton(new Rectangle(620,650,100,50),"Upload",new Color(0,255,0),20);
    	this.whiteMoves=new ArrayList<String>();
    	this.blackMoves=new ArrayList<String>();
    	this.curFile="";
    	this.getter=new MoveGetter();
    	this.board=board;
    	this.whiteFens=new ArrayList<String>();
    	this.blackFens=new ArrayList<String>();
    	this.curMove="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    	this.curMoveIndex=0;
    	this.uploaded=false;
    	this.forward=new ClickButton(new Rectangle(750,500,30,30),">",new Color(200,200,200),30);
    	this.back=new ClickButton(new Rectangle(650,500,30,30),"<",new Color(200,200,200),30);
    	this.color=true;
    	this.flip=true;
    	this.flipButton=new ClickButton(new Rectangle(700,550,70,30),"Flip",new Color(0,255,0),30);
    }
    public void onClick(int mx,int my,int mb){
    	if(uploadButton.onClick(mx,my,mb)){
    		uploadFile();
    	}
    	if(forward.onClick(mx,my,mb) && curMoveIndex<whiteFens.size()+blackFens.size()){
    		
    		if(color){
    			curMove=whiteFens.get(curMoveIndex/2);
    		}
    		else{
    			curMove=blackFens.get(curMoveIndex/2);
    		}
    		curMoveIndex++;
    		color=!color;
    	}
    	else if(back.onClick(mx,my,mb) && curMoveIndex>0){
    		curMoveIndex--;
    		color=!color;
    		if(color){
    			curMove=whiteFens.get(curMoveIndex/2);
    		}
    		else{
    			curMove=blackFens.get(curMoveIndex/2);
    		}
    	}
    	else if(flipButton.onClick(mx,my,mb)){
    		flip=!flip;
    	}
    }
    
    public void uploadFile(){
    	JFileChooser fc=new JFileChooser();
    	fc.setFileFilter(new FileExtensionFilter(".pgn"));
    	fc.setCurrentDirectory(new File("C://Computer Science Projects//ChessInterface//Games"));
    	int returnVal=fc.showOpenDialog(null);
    	if(returnVal==JFileChooser.APPROVE_OPTION){
    		parseFile(fc.getSelectedFile().getName(),fc.getCurrentDirectory().getName());
    	}
    }
    public void parseFile(String filename, String dir){
    	curFile=filename;
    	try{
    		BufferedReader f = new BufferedReader(new FileReader(dir+"\\"+filename));
    		String line=f.readLine();
    		while(line.substring(0,1).equals("[") || line.equals("")){
    			line=f.readLine();
    		}
    		StringTokenizer st=new StringTokenizer(line);
    		boolean color=true;
    		while(st.hasMoreTokens()){
    			String s=st.nextToken();
    			if(!isNumber(s.substring(0,1))){
    				if(color){
    					whiteMoves.add(s);
    				}
    				else{
    					blackMoves.add(s);
    				}
    				color=!color;
    			}
    		}
    		makePos();	
		}
		catch(IOException e){
    		System.out.println(e);
    	}
    }
    public Piece[][] twoMakeCopy(Piece[][] b){
    	Piece[][] copy=new Piece[8][8];
    	for(int i=0;i<8;i++){
    		for(int j=0;j<8;j++){
    			copy[i][j]=b[i][j];
    		}
    	}
    	return copy;
    }
    public Point getDest(String move){
    	if(move.charAt(move.length()-1)=='#' || move.charAt(move.length()-1)=='+'){
    		move=move.substring(0,move.length()-1);
    	}
    	return new Point((int)move.charAt(move.length()-2)-97,8-Integer.parseInt(""+move.charAt(move.length()-1)));
    }
    public void makePos(){
    	Piece[][]boardCpy=twoMakeCopy(board);
    	for(int i=0;i<whiteMoves.size();i++){
    		Point origin=getter.findOrigin(boardCpy,whiteMoves.get(i),true);
    		int x=(int)origin.getX();
    		int y=(int)origin.getY();
    		if(x==-1 && y==-1){
    			boardCpy[7][6]=boardCpy[7][4];
    			boardCpy[7][4]=null;
    			boardCpy[7][5]=boardCpy[7][7];
    			boardCpy[7][7]=null;
    		}
    		else if(x==-2 && y==-2){
    			boardCpy[7][2]=boardCpy[7][4];
    			boardCpy[7][4]=null;
    			boardCpy[7][4]=boardCpy[7][0];
    			boardCpy[7][0]=null;
    		}
    		else{
    			Point dest=getDest(whiteMoves.get(i));
	    		int dx=(int)dest.getX();
	    		int dy=(int)dest.getY();
	    		if(x>=100 && y>=100){
	    			x-=100;
	    			y-=100;
	    			boardCpy[dy][dx]=boardCpy[y][x];
	    			boardCpy[y][x]=null;
	    			boardCpy[dy+1][dx]=null;
	    		}
	    		else{
	    			boardCpy[dy][dx]=boardCpy[y][x];
	    			boardCpy[y][x]=null;
	    		}
    		}
    		whiteFens.add(makeFEN(boardCpy));
    		if(i<blackMoves.size()){
    			origin=getter.findOrigin(boardCpy,blackMoves.get(i),false);
	    		
	    		x=(int)origin.getX();
	    		y=(int)origin.getY();
	    		
	    		if(x==-1 && y==-1){
	    			boardCpy[0][6]=boardCpy[0][4];
	    			boardCpy[0][4]=null;
	    			boardCpy[0][5]=boardCpy[0][7];
	    			boardCpy[0][7]=null;
	    		}
	    		else if(x==-2 && y==-2){
	    			boardCpy[0][2]=boardCpy[0][4];
	    			boardCpy[0][4]=null;
	    			boardCpy[0][4]=boardCpy[0][0];
	    			boardCpy[0][0]=null;
	    		}
	    		else{
	    			Point dest=getDest(blackMoves.get(i));
	    			int dx=(int)dest.getX();
	    			int dy=(int)dest.getY();
	    			if(x>=100 && y>=100){
		    			x-=100;
		    			y-=100;
		    			boardCpy[dy][dx]=boardCpy[y][x];
		    			boardCpy[y][x]=null;
		    			boardCpy[dy+1][dx]=null;
		    		}
		    		else{
		    			boardCpy[dy][dx]=boardCpy[y][x];
		    			boardCpy[y][x]=null;
		    		}
	    		}
	    		
	    		blackFens.add(makeFEN(boardCpy));
    		}
    	}
    	uploaded=true;
    }
    public boolean isNumber(String s){
    	try{
    		int c=Integer.parseInt(s);
    	}
    	catch(NumberFormatException nfe){
    		return false;
    	}
    	return true;
    }
    public void reDraw(Graphics dbg){
    	uploadButton.drawButton(dbg);
    	dbg.setFont(new Font("Arial",Font.BOLD,15));
    	dbg.drawString(curFile,725,670);
    	if(uploaded){
    		forward.drawButton(dbg);
    		back.drawButton(dbg);
    		flipButton.drawButton(dbg);
    	}
    }
    public boolean collidePoint(int cx,int cy, int px, int py, int wid, int len){
    	if(cx>=px && cx<=wid && cy>=py && cy<=len){
    		return true;
    	}
    	return false;
    }
    public String makeFEN(Piece[][]b){
    	String code="";
    	for(int i=0;i<8;i++){
    		int j=0;
    		while(j<8){
    			if(b[i][j]==null){
    				int c=0;
    				int k;
    				for(k=j;k<8;k++){
    					if(b[i][k]==null){
    						c++;
    					}
    					else{
    						j=k-1;
    						break;
    					}
    				}
    				if(k==8){
    					j=8;
    				}
    				code+=""+c;
    			}
    			else{
    				code+=""+b[i][j].getName();
    			}
    			j++;
    		}
    		code+="/";
    	}
    	code=code.substring(0,code.length()-1);
    	return code;
    }
    
    public ArrayList<String> getFEN(boolean col){
    	if(col){
    		return whiteFens;
    	}
    	return blackFens;
    }
    public String getCurMove(){
    	return curMove;
    }
    public int getCurMoveIndex(){
    	return curMoveIndex;
    }
    public boolean getColor(){
    	return color;
    }
    public boolean getFlip(){
    	return flip;
    }
}