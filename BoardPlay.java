import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class BoardPlay {
	private int top,bottom,left,right;
	private Image[] piecePics;
	private FEN pos;
    private Piece [][]b;
    private boolean[] castling;
    private boolean activeColor;
    private boolean clicked,letgo;
    private int sqX,sqY;
    private boolean squareClicked;
    private String enPassant;
    private String ua,other;
    private MoveChecker checker;
    private int whiteMoveCount,blackMoveCount;
    private Clock clock;
    private long whiteTime,blackTime,delay;
    private String whiteName,blackName;
    private CtrlPanelPlay ctrlPanelPlay=new CtrlPanelPlay();
    private int halfMoves;
    private String event,site,result;
    public BoardPlay(String[]info) {
    	this.pos=new FEN(info[0]);
    	this.b=pos.getPos();
    	this.top=60;
    	this.bottom=660;
    	this.left=10;
    	this.right=610;
    	this.sqX=-1;
    	this.sqY=-1;
    	this.whiteMoveCount=0;
    	this.blackMoveCount=0;
    	this.whiteName=info[1];
    	this.blackName=info[2];
    	this.whiteTime=Long.parseLong(info[3]);
    	this.blackTime=Long.parseLong(info[4]);
    	this.delay=Long.parseLong(info[5]);
    	this.event=info[6];
    	this.site=info[7];
    	this.result="";
    	this.clock=new Clock(whiteTime,blackTime,delay);
    	this.piecePics=new Image[12];
    	this.squareClicked=false;
    	this.activeColor=pos.getColor();
    	this.enPassant=pos.getEnPassant();
    	this.castling=pos.getCastling();
    	this.ua="";
    	this.other="-";
    	this.checker=new MoveChecker();
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
    	this.halfMoves=0;
    }
    public String formatSquare(String sq){
    	return sq.substring(0,1)+(8-Integer.parseInt(sq.substring(1,2)));
    }
    public String makeSquare(int x, int y){
    	return ((char)(97+x))+(""+y);
    }
    public int[] makeCoord(String sq){
    	int []ans=new int[2];
    	if(sq.equals("-")){
    		ans[0]=-1;
    		ans[1]=-1;
    	}
    	else{
    		ans[0]=(int)(sq.charAt(0))-97;
    		ans[1]=Integer.parseInt(sq.substring(1,2));
    	}
    	return ans;
    }
    public void resolveEnPassant(){
    	
		int[]rSq=makeCoord(enPassant);
		if(activeColor){
			b[rSq[1]+1][rSq[0]]=null;
		}
		else{	
			b[rSq[1]-1][rSq[0]]=null;
		}
    }
    public void resolveCastling(String type){
		if(type.equals("WK")){
			b[7][5]=b[7][7];
			b[7][7]=null;
		}
		else if(type.equals("WQ")){
			b[7][3]=b[7][0];
			b[7][0]=null;
		}
		else if(type.equals("BK")){
			//System.out.println(type);
			b[0][5]=b[0][7];
			b[0][7]=null;
		}
		else if(type.equals("BQ")){
			//System.out.println(type);
			b[0][3]=b[0][0];
			b[0][0]=null;
		}
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
    public void highlightSelectedPiece(Graphics dbg){
    	dbg.setColor(new Color(255,255,0));
    	if(activeColor){
    		dbg.fillRect(left+sqX*75,top+sqY*75,75,75);
    	}
    	else{
    		dbg.fillRect(left+(7-sqX)*75,top+(7-sqY)*75,75,75);
    	}
    }
    public void highlightValidSquares(Graphics dbg, int x, int y){
    	dbg.setColor(new Color(0,255,0));
    	if(activeColor){
    		dbg.fillRect(left+x*75,top+y*75,75,75);
    		dbg.setColor(new Color(255,255,0));
    		dbg.drawRect(left+x*75,top+y*75,75,75);
    	}
    	else{
    		dbg.fillRect(left+(7-x)*75,top+(7-y)*75,75,75);
    		dbg.setColor(new Color(255,255,0));
    		dbg.drawRect(left+(7-x)*75,top+(7-y)*75,75,75);
    	}
    }
    public void selectPiece(int x, int y){
    	if(activeColor){
    		if(b[y][x]!=null && b[y][x].getColor()==activeColor){
				squareClicked=true;
				sqX=x;
				sqY=y;
			}
    	}
    	else{
    		if(b[7-y][7-x]!=null && b[7-y][7-x].getColor()==activeColor){
				squareClicked=true;
				sqX=7-x;
				sqY=7-y;
			}
    	}
    }
    public boolean movePiece(int x, int y){
		if(activeColor){
			String [] res=checker.check(b[sqY][sqX],b,enPassant,castling,sqX,sqY,x,y,ua,true);
			if(!res[0].equals("false")){
				long cur=System.nanoTime();
				if(whiteMoveCount==0){
					clock.initialWhite(cur);
				}
				if(whiteMoveCount>=1){
					clock.setWhiteLastMove(cur);
				}
				if(b[sqY][sqX].getName()=='P' || b[y][x]!=null){
					halfMoves=0;
				}
				else{
					halfMoves+=1;
				}
				for(int i=0;i<4;i++){
					castling[i]=Boolean.parseBoolean(res[i+1]);
				}
				if(res[0].substring(0,2).equals("en")){
					resolveEnPassant();
				}
				else if(res[0].substring(0,2).equals("WK") || res[0].substring(0,2).equals("WQ") || res[0].substring(0,2).equals("BK") || res[0].substring(0,2).equals("BQ")){
					resolveCastling(res[0].substring(0,2));
				}
				enPassant=res[5];
				if(res[0].substring(res[0].length()-2,res[0].length()).equals("Ma")){
					ua="mate";
				}
				else if(res[0].substring(res[0].length()-2,res[0].length()).equals("Ch")){
					ua="true";
				}
				else{
					ua="false";
				}
				if(!res[6].equals("-")){
					other=res[6];
				}
				else{
					other="-";
				}
				ctrlPanelPlay.createMove(b[sqY][sqX],sqX,sqY,x,y,res[0].substring(0,2),!(b[y][x]==null),ua,other);
				b[y][x]=b[sqY][sqX];
				b[sqY][sqX]=null;
				whiteMoveCount++;
				activeColor=!activeColor;
				if(ua.equals("mate")){
					System.out.println("CheckMate Again");
					return true;
				}
			}
		}
		else{
			String []res=checker.check(b[sqY][sqX],b,enPassant,castling,sqX,sqY,7-x,7-y,ua,true);
			if(!res[0].equals("false")){
				long cur=System.nanoTime();
				if(blackMoveCount==0){
					clock.initialBlack(cur);
				}
				if(blackMoveCount>=1){
					clock.setBlackLastMove(cur);
				}
				if(b[sqY][sqX].getName()=='p' || b[y][x]!=null){
					halfMoves=0;
				}
				else{
					halfMoves+=1;
				}
				for(int i=0;i<4;i++){
					castling[i]=Boolean.parseBoolean(res[i+1]);
				}
				if(res[0].substring(0,2).equals("en")){
					resolveEnPassant();
				}
				else if(res[0].substring(0,2).equals("WK") || res[0].substring(0,2).equals("WQ") || res[0].substring(0,2).equals("BK") || res[0].substring(0,2).equals("BQ")){
					resolveCastling(res[0].substring(0,2));
				}
				enPassant=res[5];
				if(res[0].substring(res[0].length()-2,res[0].length()).equals("Ma")){
					ua="mate";
				}
				else if(res[0].substring(res[0].length()-2,res[0].length()).equals("Ch")){
					ua="true";
				}
				else{
					ua="false";
				}
				if(!res[6].equals("-")){
					other=res[6];
				}
				else{
					other="-";
				}
				ctrlPanelPlay.createMove(b[sqY][sqX],sqX,sqY,7-x,7-y,res[0].substring(0,2),!(b[7-y][7-x]==null),ua,other);
				b[7-y][7-x]=b[sqY][sqX];
				b[sqY][sqX]=null;
				blackMoveCount++;
				activeColor=!activeColor;
				if(ua.equals("mate")){
					System.out.println("CheckMate Again");
					return true;
				}
			}
		}
	
		return false;
    }
    public boolean getSquareClicked(){
    	return squareClicked;
    }
    public void setSquareClicked(boolean val){
    	squareClicked=val;
    }
    public boolean getActiveColor(){
    	return activeColor;
    }
    public void setActiveColor(){
    	activeColor=!activeColor;
    }
    public Piece[][] getBoard(){
    	return b;
    }
    public int getWhiteMoveCount(){
    	return whiteMoveCount;
    }
    public void setWhiteMoveCount(){
    	whiteMoveCount++;
    }
    public int getBlackMoveCount(){
    	return blackMoveCount;
    }
    public void setBlackMoveCount(){
    	blackMoveCount++;
    }
    /*public long getTotTime(){
    	return totTime;
    }
    public void setTotTime(int val){
    	totTime=val;
    }*/
    public int getLeft(){
    	return left;
    }
    public int getRight(){
    	return right;
    }
    public int getTop(){
    	return top;
    }
    public int getBottom(){
    	return bottom;
    }
    public Clock getClock(){
    	return clock;
    }
    public boolean collidePoint(int cx,int cy, int px, int py, int wid, int len){
    	if(cx>=px && cx<=wid && cy>=py && cy<=len){
    		return true;
    	}
    	return false;
    }
    public String[] onClick(int mx, int my, int mb){
    	String [] res=new String[2];
    	if(collidePoint(mx,my,left,top,right,bottom)){
    		if(!squareClicked){
	    		int x=(mx-left)/75;
	    		int y=(my-top)/75;
	    		selectPiece(x,y);
	    	}
	    	else if(squareClicked){
	    		squareClicked=false;
				int x=(mx-left)/75;
				int y=(my-top)/75;
				boolean gameEnds=movePiece(x,y);
				if(gameEnds){
					result=setResult(!activeColor,false);
					ctrlPanelPlay.processEnd(event,site,whiteName,blackName,result);
					res[0]="opening";
					res[1]="instructions";
					return res;
				}
	    	}
	    	res[0]="board-Play";
	    	res[1]="ctrlPanel-Play";
	    	return res;
    	}
    	res=ctrlPanelPlay.onClick(mx,my,mb);
    	
    	return res;
    }
    public String setResult(boolean col,boolean draw){
    	if(draw){
    		return "1/2-1/2";
    	}
    	else if(col){
    		return "1-0";
    	}
    	else{
    		return "0-1";
    	}
    }
    public void onPress(int mx, int my, int mb){
    	ctrlPanelPlay.onPress(mx,my,mb);
    }
    public void onRelease(int mx, int my, int mb){
    	ctrlPanelPlay.onRelease(mx,my,mb);
    }
    public void reDraw(Graphics dbg){
    	drawBackground(dbg);
    	drawBoard(dbg);
    	drawPadding(dbg);
    	if(squareClicked){
    		highlightSelectedPiece(dbg);
    		ArrayList<Point>valid=checker.getValidMoves(b[sqY][sqX],b,enPassant,castling,sqX,sqY);
    		for(int i=0;i<valid.size();i++){
    			highlightValidSquares(dbg,(int)valid.get(i).getX(),(int)valid.get(i).getY());
    		}
    	}
    	dbg.setColor(new Color(0,0,0));
    	dbg.setFont(new Font("Arial",Font.BOLD,20));
    	if(activeColor){
    		setPiecesWhite(dbg,b);
    		if(whiteMoveCount==0){
    			dbg.drawString(whiteTime+":00",left+20,top+30+600);
    			dbg.drawString(blackTime+":00",left+20,top);
    		}
    		else{
    			dbg.drawString(clock.getWhiteTime(System.nanoTime(),true),left+20,top+30+600);
    			dbg.drawString(clock.getBlackTime(System.nanoTime(),false),left+20,top);
    		}
    		dbg.drawString(whiteName,left+120,top+30+600);
    		dbg.drawString(blackName,left+120,top);
    	}
    	else{
    		setPiecesBlack(dbg,b);
    		if(blackMoveCount==0){
    			dbg.drawString(blackTime+":00",left+20,top+30+600);
    			dbg.drawString(whiteTime+":00",left+20,top);
    		}
    		else{
    			dbg.drawString(clock.getBlackTime(System.nanoTime(),true),left+20,top+30+600);
    			dbg.drawString(clock.getWhiteTime(System.nanoTime(),false),left+20,top);
    		}
    		dbg.drawString(blackName,left+120,top+30+600);
    		dbg.drawString(whiteName,left+120,top);
    	}
    	ctrlPanelPlay.reDraw(dbg);
    }
    public String getEnPassant(){
    	if(enPassant.length()<2){
    		return enPassant;
    	}
    	return enPassant.substring(0,1)+(""+(8-Integer.parseInt(enPassant.substring(1,2))));
    }
    public String makeFEN(){
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
    	code+=" ";
    	if(activeColor){
    		code+="w";
    	}
    	else{
    		code+="b";
    	}
    	code+=" ";
    	boolean val=false;
    	if(castling[0]){
    		code+="K";
    		val=true;
    	}
    	if(castling[1]){
    		code+="Q";
    		val=true;
    	}
    	if(castling[2]){
    		code+="k";
    		val=true;
    	}
    	if(castling[3]){
    		code+="q";
    		val=true;
    	}
    	if(val){
    		code+=" ";
    	}
    	code+=getEnPassant();
    	code+=" ";
    	code+=""+halfMoves;
    	code+=" ";
    	code+=""+(1+blackMoveCount);
    	return code;
    }
}