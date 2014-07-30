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
	private VertScroll scrollBar;
	private Tabs tabs;
    public CtrlPanelAnalyse(Piece[][] board) {
    	this.scrollBar=new VertScroll(new Rectangle(610,30,610,670), new Rectangle(650,150,210,300));
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
    	String[]tabNames=new String[2];
    	tabNames[0]="Play Game";
    	tabNames[1]="Analyse Game";
    	this.tabs=new Tabs(tabNames,630,50,200,30,1);
    }
    public String[] onClick(int mx,int my,int mb){
    	if(uploadButton.onClick(mx,my,mb)){
    		uploadFile();
    	}
    	if(forward.onClick(mx,my,mb) && curMoveIndex<whiteFens.size()+blackFens.size()){
    		System.out.println("HI");
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
    	tabs.onClick(mx,my,mb);
    	String[]res=new String[2];
    	if(tabs.getActive()==0){
    		res[0]="board-Play";
    		res[1]="ctrlPanel-Play";
    	}
    	else if(tabs.getActive()==1){
    		res[0]="board-Analyse";
    		res[1]="ctrlPanel-Analyse";
    	}
    	return res;
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
    		int i=0;
    		while(line.equals("") || line.substring(0,1).equals("[")){
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
    	scrollBar.setImLen(whiteMoves.size()*30);
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
    public void drawWhiteMoves(Graphics dbg){
    	dbg.setFont(new Font("Arial",Font.BOLD,20));
    	dbg.setColor(new Color(0,0,0));
    	for(int i=0;i<whiteMoves.size();i++){
    		dbg.drawString((i+1)+".",660,scrollBar.getViewTop()+30*i);
    		dbg.drawString(whiteMoves.get(i),690,scrollBar.getViewTop()+30*i);
    	}
    }
    public void drawBlackMoves(Graphics dbg){
    	dbg.setFont(new Font("Arial",Font.BOLD,20));
    	dbg.setColor(new Color(0,0,0));
    	for(int i=0;i<blackMoves.size();i++){
    		dbg.drawString(blackMoves.get(i),780,scrollBar.getViewTop()+30*i);
    	}
    }
    public void reDraw(Graphics dbg){
    	scrollBar.drawBar(dbg);
    	drawWhiteMoves(dbg);
    	drawBlackMoves(dbg);
    	scrollBar.drawPadding(dbg,new Color(255,255,255));
    	tabs.drawTabs(dbg);
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
    public void onPress(int mx, int my, int mb){
    	scrollBar.onPress(mx,my,mb);
    }
    public void onRelease(int mx, int my, int mb){
    	scrollBar.onRelease(mx,my,mb);
    }
    public void onMove(int mx, int my, int mb){
    	scrollBar.onMove(mx,my,mb);
    }
}