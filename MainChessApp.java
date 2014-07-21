import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

public class MainChessApp extends JFrame implements KeyListener,MouseListener,MouseMotionListener{
	private BufferedImage dbImage;//
	private Graphics dbg;//
	private static int mb,mx,my;//mouse pressed, mouse x pos, mouse y pos
	private BoardPlay board;
	private Opening opening=new Opening();
	private Instructions instructions=new Instructions();
	private BoardAnalyse analysis;
    private boolean clicked,letgo;
    private String leftState,rightState;
    public MainChessApp(){
    	super("Chess");
    	addMouseListener(this);
    	addKeyListener(this);
    	setSize(1220,700);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
    	this.mb=0;
    	this.mx=0;
    	this.my=0;
    	this.clicked=false;
    	this.letgo=false;
    	this.leftState="opening";
    	this.rightState="instructions";
    }
    
    public static void delay(long len){//to prevent screen from flashing
    	try{ 
    		Thread.sleep(len);
    	}
    	catch(InterruptedException ex){
    		System.out.println(ex);
    	}
    }
    public void mousePressed(MouseEvent e){
    	mb=e.getButton();
    	mx=e.getX();//mouse pos x
    	my=e.getY();//mouse pos y
    	if(leftState.equals("board-Play")){
    		board.onPress(mx,my,mb);
    	}
    }
    public void mouseEntered(MouseEvent e){}//not used overriding methods
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){
    	mb=e.getButton();
    	mx=e.getX();//mouse pos x
    	my=e.getY();//mouse pos y
    	if(leftState.equals("opening")){
    		String [] res=opening.onClick(mx,my,mb);
    		if(res[0].equals("board-Play")){
    			String [] boardInfo=opening.retrieveInfo();
    			board=new BoardPlay(boardInfo);
    		}
    		leftState=res[0];
    		rightState=res[1];
    	}
    	else if(leftState.equals("board-Play")){
	    	String[] res=board.onClick(mx,my,mb);
	    	if(res[0].equals("board-Analyse")){
	    		analysis=new BoardAnalyse();
	    	}
	    	leftState=res[0];
	    	rightState=res[1];
    	}
    	else if(leftState.equals("board-Analyse")){
    		String[] res=analysis.onClick(mx,my,mb);
    		leftState=res[0];
    		rightState=res[1];
    	}
    	if(rightState.equals("instructions")){
    		String [] res=opening.onClick(mx,my,mb);
    		leftState=res[0];
    		rightState=res[1];
    	}
    	else if(rightState.equals("ctrlPanel-Play")){
    		
    	}
    }
    public void mouseReleased(MouseEvent e){//reset mouse button
    	mb=e.getButton();
    	mx=e.getX();
    	my=e.getY();
    	if(leftState.equals("board-Play")){
    		board.onRelease(mx,my,mb);
    	}
    }
    public void keyTyped(KeyEvent e){
    	if(leftState.equals("opening")){
    		opening.onType(e.getKeyChar());
    	}
    }
    public void keyPressed(KeyEvent e){//check if any key is pressed
    	if(leftState.equals("opening")){
    		opening.onKeyPress(e.getKeyCode());
    	}
    }
    public void keyReleased(KeyEvent e){//check if any key is let go
    	
    }
    public void mouseMoved(MouseEvent e){}
    public void mouseDragged(MouseEvent e){}
    
    public boolean collidePoint(int cx,int cy, int px, int py, int wid, int len){
    	if(cx>=px && cx<=wid && cy>=py && cy<=len){
    		return true;
    	}
    	return false;
    }
    public void paint(Graphics g){//drawing everything
    	
    	if (dbImage==null){//creating new image
    		dbImage=new BufferedImage(getWidth(), getHeight(),BufferedImage.TYPE_INT_ARGB);
    		dbg=dbImage.getGraphics();
    	}
    	if(leftState.equals("opening")){
    		opening.reDraw(dbg);
    	}
    	else if(leftState.equals("board-Play")){
	    	board.reDraw(dbg);
    	}
    	else if(leftState.equals("board-Analyse")){
    		analysis.reDraw(dbg);
    	}
    	if(rightState.equals("instructions")){
    		instructions.reDraw(dbg);
    	}
    	g.drawImage(dbImage,0,0,this);//drawing everything onto the screen
    }

    public static void main(String [] args) throws IOException{
		MainChessApp app=new MainChessApp();
    	boolean running=true;
    	while(running){
    		app.repaint();
    		app.delay(20);
    		//System.out.println(mx+" "+my);
    	}
    }
    
    
}