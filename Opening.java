import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
public class Opening {
	private InputBox[] inputs;
	private String whiteName,blackName,whiteTime,blackTime,delayTime,event,site;
	private ClickButton enter;
	private boolean showError;
    public Opening() {
    	this.inputs=new InputBox[7];
    	inputs[0]=new InputBox("Event: ",new Rectangle(100,100,300,30),"Game For Fun",true,32,122,"",15);
    	inputs[1]=new InputBox("Site: ",new Rectangle(100,160,300,30),"Toronto, CAN",false,32,122,"",15);
    	inputs[2]=new InputBox("Player Name White: ", new Rectangle(100,220,300,30), "A",false,32,122,"",15);
    	inputs[3]=new InputBox("Player Name Black: ", new Rectangle(100,280,300,30), "B",false,32,122,"",15);
    	inputs[4]=new InputBox("White Time",new Rectangle(100,340,100,30),"10",false,48,57,"minutes",4);
    	inputs[5]=new InputBox("Black Time",new Rectangle(300,340,100,30),"10",false,48,57,"minutes",4);
    	inputs[6]=new InputBox("Delay",new Rectangle(100,400,100,30),"5",false,48,57,"seconds",2);
    	this.event="";
    	this.site="";
    	this.whiteName="A";
    	this.blackName="B";
    	this.whiteTime="10";
    	this.blackTime="10";
    	this.delayTime="5";
    	this.enter=new ClickButton(new Rectangle(300,500,100,50),"Play Now!",new Color(0,255,0),20);
    	this.showError=false;
    }
    public String[] onClick(int mx, int my, int mb){
    	String[]res=new String[2];
    	checkInputs(mx,my,mb);
    	if(enter.onClick(mx,my,mb)){
    		if(!whiteName.equals("") && !blackName.equals("") && !whiteTime.equals("") && !blackTime.equals("") && !delayTime.equals("")){
    			res[0]="board-Play";
	    		res[1]="ctrlPanel-Play";
	    		showError=false;
	    		return res;
    		}
    		else{
    			showError=true;
    		}
    	}
    	res[0]="opening";
    	res[1]="instructions";
    	return res;
    }
    
    public void checkInputs(int mx, int my, int mb){
    	boolean[] originalActive=new boolean[inputs.length];
    	for(int i=0;i<inputs.length;i++){
    		originalActive[i]=inputs[i].getActive();
    	}
    	for(int i=0;i<inputs.length;i++){
    		boolean active=inputs[i].onClick(mx,my,mb);
    		if(active && !originalActive[i]){
    			for(int j=0;j<inputs.length;j++){
    				if(j!=i){
    					inputs[j].setActive(false);
    				}
    			}
    			break;
    		}
    	}
    	event=inputs[0].getText();
    	site=inputs[1].getText();
    	whiteName=inputs[2].getText();
    	blackName=inputs[3].getText();
    	whiteTime=inputs[4].getText();
    	blackTime=inputs[5].getText();
    	delayTime=inputs[6].getText();
    }
    public void reDraw(Graphics dbg){
    	dbg.setColor(new Color(255,255,255));
    	dbg.fillRect(0,0,610,700);
    	for(int i=0;i<inputs.length;i++){
    		inputs[i].drawBox(dbg);
    	}
    	enter.drawButton(dbg);
    	if(showError){
    		dbg.drawString("Please complete above info",100,600);
    	}
    }
    public void pause(long len){//to prevent screen from flashing
    	try{ 
    		Thread.sleep(len);
    	}
    	catch(InterruptedException ex){
    		System.out.println(ex);
    	}
    }
    public void onType(char key){
    	for(int i=0;i<inputs.length;i++){
    		inputs[i].onType(key);
    	}
    }
    public void onKeyPress(int key){
    	if(key==10){
    		for (int i=0;i<inputs.length;i++){
    			if(inputs[i].getActive()){
    				inputs[i].setActive(false);
    				inputs[(i+1)%inputs.length].setActive(true);
    				break;
    			}
    		}
    	}
    	for(int i=0;i<inputs.length;i++){
    		inputs[i].onKeyPress(key);
    	}
    }
    public String[] retrieveInfo(){
    	String[]info=new String[8];
    	info[0]="rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    	info[1]=whiteName;
    	info[2]=blackName;
    	info[3]=whiteTime;
    	info[4]=blackTime;
    	info[5]=delayTime;
    	info[6]=event;
    	info[7]=site;
    	return info;
    }
    
    
}