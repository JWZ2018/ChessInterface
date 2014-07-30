import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class CtrlPanelPlay {
	private ArrayList<String>whiteMoves,blackMoves;
	private VertScroll scrollBar;
	private Tabs tabs;
    public CtrlPanelPlay() {
    	this.whiteMoves=new ArrayList<String>();
    	this.blackMoves=new ArrayList<String>();
    	this.scrollBar=new VertScroll(new Rectangle(610,30,610,670), new Rectangle(650,150,210,500));
    	String[]tabNames=new String[2];
    	tabNames[0]="Play Game";
    	tabNames[1]="Analyse Game";
    	this.tabs=new Tabs(tabNames,630,50,200,30,0);
    }
    public void reDraw(Graphics dbg){
    	
    	dbg.setColor(new Color(255,255,255));
    	dbg.fillRect(610,30,610,670);
    	scrollBar.drawBar(dbg);
    	drawWhiteMoves(dbg);
    	drawBlackMoves(dbg);
    	scrollBar.drawPadding(dbg,new Color(255,255,255));
    	tabs.drawTabs(dbg);
    	//System.out.println(scrollBar.getViewTop());
    	
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
    public void createMove(Piece p, int cx,int cy,int nx, int ny,String castling,boolean capture,String check,String other){
    	String ans="";
    	if(castling.equals("WK") || castling.equals("BK")){
    		ans="O-O";
    	}
    	else if(castling.equals("WQ") || castling.equals("BQ")){
    		ans="O-O-O";
    	}
    	else{
    		if(p.getName()!='p' && p.getName()!='P'){
    			ans+=(""+p.getName()).toUpperCase();
    		}
    		if(!other.equals("-")){
    			ans+=other;
    		}
    		if(capture){
    			if(p.getName()=='p' || p.getName()=='P'){
    				ans+=(char)(97+cx)+"";
    			}
    			ans+="x";
    		}
    		ans+=makeSquare(nx,ny);
    		if(check.equals("true")){
    			ans+="+";
    		}
    		if(check.equals("mate")){
    			ans+="#";
    		}
    	}
    	if(p.getColor()){
    		whiteMoves.add(ans);
    	}
    	else{
    		blackMoves.add(ans);
    	}
    	scrollBar.setImLen(whiteMoves.size()*30);
    	if(p.getColor()){
    		scrollBar.incBarTop(30.0);
    	}
    }
    public String makeSquare(int x, int y){
    	return ((char)(97+x))+(""+(8-y));
    }
    public String[] onClick(int mx,int my,int mb){
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
    public void onPress(int mx, int my, int mb){
    	scrollBar.onPress(mx,my,mb);
    }
    public void onRelease(int mx, int my, int mb){
    	scrollBar.onRelease(mx,my,mb);
    }
    public boolean collidePoint(int mx, int my, int px, int py, int width, int height){
    	if(mx>=px && mx <=px+width && my >=py && my<=py+height){
    		return true;
    	}
    	return false;
    }
    public void processEnd(String event, String site, String whiteName, String blackName,String result){
    	String[] ids = TimeZone.getAvailableIDs(1 * 60 * 60 * 1000);
		if (ids.length == 0){
			System.exit(0);
		}
		SimpleTimeZone pdt = new SimpleTimeZone(1 * 60 * 60 * 1000, ids[0]);
		pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		GregorianCalendar calendar = new GregorianCalendar(pdt);
		calendar.setTime(new java.util.Date());
		int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year= calendar.get(Calendar.YEAR);
		int hour= calendar.get(Calendar.HOUR);
		int minute =calendar.get(Calendar.MINUTE);
		int second=calendar.get(Calendar.SECOND);
		String filename=event+"-"+whiteName+".vs."+blackName+"-"+hour+"."+minute+"."+second+"-"+dayofmonth+"."+month+"."+year;
		String date=year+"."+month+"."+dayofmonth;
    	try{
    		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Games//"+filename+".pgn")));
    		out.println("[Event \""+event+"\"]");
    		out.println("[Site \""+site+"\"]");
    		out.println("[Date \""+date+"\"]");
    		out.println("[White \""+whiteName+"\"]");
    		out.println("[Black \""+blackName+"\"]");
    		out.println("[Result \""+result+"\"]");
    		for (int i=0;i<whiteMoves.size();i++){
    			out.print((i+1)+". "+whiteMoves.get(i)+" ");
    			if(i<blackMoves.size()){
    				out.print(blackMoves.get(i)+" ");
    			}
    		}
    		out.print(result);
    		out.close();
    	}
    	catch(IOException e){
    		System.out.println(e);
    	}
    	
    }
}