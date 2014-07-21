import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
public class MoveGetter {
	private char PieceName;
	private char other;
	private boolean capture;
	private int x,y;
    public MoveGetter() {
    }
    public boolean checkBounds(int x,int y){
    	if(x>=0 && x<8 && y>=0 && y<8){
    		return true;
    	}
    	return false;
    }
    public Point findKing(Piece[][]bo,char[]data,boolean color){
    	if(data[0]=='C'){
    		if(data[1]=='K'){
    			return new Point(-1,-1);
    		}
    		else if(data[1]=='Q'){
    			return new Point(-2,-2);
    		}
    	}
    	else{
    		int x=(int)data[3]-48;
    		int y=(int)data[4]-48;
    		System.out.println(x+" "+y);
    		for(int i=x-1;i<=x+1;i++){
    			for(int j=y-1;j<=y+1;j++){
    				if(i!=x || j!=y){
    					if(checkBounds(i,j)){
    						if(bo[j][i]!=null && Character.toUpperCase(bo[j][i].getName())=='K'){
    							return new Point(i,j);
    						}
    					}
    				}
    			}
    		}
    	}
    	return new Point(-10,-10);
    }
    public Point findBishop(Piece[][]bo,char[]data,boolean color){
    	int x=(int)data[3]-48;
    	int y=(int)data[4]-48;
    	int i=x-1,j=y-1;
    	while(i>=0 && j>=0){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='B' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i--;
    		j--;
    	}
    	i=x+1;
    	j=y-1;
    	while(i<8 && j>=0){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='B' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i++;
    		j--;
    	}
    	i=x-1;
    	j=y+1;
    	while(i>=0 && j<8){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='B' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i--;
    		j++;
    	}
    	i=x+1;
    	j=y+1;
    	while(i<8 && j<8){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='B' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i++;
    		j++;
    	}
    	return new Point(-10,-10);
    }
    public Point findRook(Piece[][]bo, char[]data,boolean color){
    	int x=(int)data[3]-48;
    	int y=(int)data[4]-48;
    	int diff=(int)data[1];
    	if(diff>=97 && diff<=104){
    		int col=diff-97;
    		for(int k=0;k<8;k++){
    			if(bo[k][col]!=null && Character.toUpperCase(bo[k][col].getName())=='R' && bo[k][col].getColor()==color){
    				return new Point(col,k);
    			}
    		}
    	}
    	if(diff>=49 && diff<=56){
    		int row=diff-97;
    		for(int k=0;k<8;k++){
    			if(bo[row][k]!=null && Character.toUpperCase(bo[row][k].getName())=='R' && bo[row][k].getColor()==color){
    				return new Point(k,row);
    			}
    		}
    	}
    	
    	int i=x-1,j=y;
    	while(i>=0){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='R' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i--;
    	}
    	i=x+1;
    	j=y;
    	while(i<8){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='R' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i++;
    	}
    	i=x;
    	j=y-1;
    	
    	while(j>=0){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='R' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		j--;
    	}
    	
    	i=x;
    	j=y+1;
    	if(bo[7][0]!=null){
    		System.out.println(bo[7][0].getName());
    	}
    	
    	while(j<8){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='R' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		j++;
    	}
    	return new Point(-10,-10);
    }
    public Point findQueen(Piece[][]bo, char[]data,boolean color){
    	int x=(int)data[3]-48;
    	int y=(int)data[4]-48;
    	int i=x-1,j=y-1;
    	while(i>=0 && j>=0){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='Q' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i--;
    		j--;
    	}
    	i=x+1;
    	j=y-1;
    	while(i<8 && j>=0){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='Q' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i++;
    		j--;
    	}
    	i=x-1;
    	j=y+1;
    	while(i>=0 && j<8){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='Q' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i--;
    		j++;
    	}
    	i=x+1;
    	j=y+1;
    	while(i<8 && j<8){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='Q' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i++;
    		j++;
    	}
    	i=x-1;
    	j=y;
    	while(i>=0){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='Q' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i--;
    	}
    	i=x+1;
    	j=y;
    	while(i<8){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='Q' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		i++;
    	}
    	i=x;
    	j=y-1;
    	while(j>=0){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='Q' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		j--;
    	}
    	i=x;
    	j=y+1;
    	while(j<8){
    		if(bo[j][i]!=null){
    			if(Character.toUpperCase(bo[j][i].getName())=='Q' && bo[j][i].getColor()==color){
    				return new Point(i,j);
    			}
    			else{
    				break;
    			}
    		}
    		j++;
    	}
    	return new Point(-10,-10);
    }
   	public Point findKnight(Piece[][]bo,char[]data,boolean color){
   		int x=(int)data[3]-48;
    	int y=(int)data[4]-48;
    	int diff=(int)data[1];
    	if(diff>=97 && diff<=104){
    		int col=diff-97;
    		for(int k=0;k<8;k++){
    			if(bo[k][col]!=null && Character.toUpperCase(bo[k][col].getName())=='N' && bo[k][col].getColor()==color){
    				return new Point(col,k);
    			}
    		}
    	}
    	if(diff>=49 && diff<=56){
    		int row=diff-97;
    		for(int k=0;k<8;k++){
    			if(bo[row][k]!=null && Character.toUpperCase(bo[row][k].getName())=='N' && bo[row][k].getColor()==color){
    				return new Point(k,row);
    			}
    		}
    	}
    	if(checkBounds(x-2,y-1)){
    		if(bo[y-1][x-2]!=null && Character.toUpperCase(bo[y-1][x-2].getName())=='N' && bo[y-1][x-2].getColor()==color){
    			return new Point(x-2,y-1);
    		}
    	}
    	if(checkBounds(x-1,y-2)){
    		if(bo[y-2][x-1]!=null && Character.toUpperCase(bo[y-2][x-1].getName())=='N' && bo[y-2][x-1].getColor()==color){
    			return new Point(x-1,y-2);
    		}
    	}
    	if(checkBounds(x+2,y-1)){
    		if(bo[y-1][x+2]!=null && Character.toUpperCase(bo[y-1][x+2].getName())=='N' && bo[y-1][x+2].getColor()==color){
    			return new Point(x+2,y-1);
    		}
    	}
    	if(checkBounds(x-1,y+2)){
    		if(bo[y+2][x-1]!=null && Character.toUpperCase(bo[y+2][x-1].getName())=='N' && bo[y+2][x-1].getColor()==color){
    			return new Point(x-1,y+2);
    		}
    	}
    	if(checkBounds(x-2,y+1)){
    		if(bo[y+1][x-2]!=null && Character.toUpperCase(bo[y+1][x-2].getName())=='N' && bo[y+1][x-2].getColor()==color){
    			return new Point(x-2,y+1);
    		}
    	}
    	if(checkBounds(x+1,y-2)){
    		if(bo[y-2][x+1]!=null && Character.toUpperCase(bo[y-2][x+1].getName())=='N' && bo[y-2][x+1].getColor()==color){
    			return new Point(x+1,y-2);
    		}
    	}
    	if(checkBounds(x+2,y+1)){
    		if(bo[y+1][x+2]!=null && Character.toUpperCase(bo[y+1][x+2].getName())=='N' && bo[y+1][x+2].getColor()==color){
    			return new Point(x+2,y+1);
    		}
    	}
    	if(checkBounds(x+1,y+2)){
    		if(bo[y+2][x+1]!=null && Character.toUpperCase(bo[y+2][x+1].getName())=='N' && bo[y+2][x+1].getColor()==color){
    			return new Point(x+1,y+2);
    		}
    	}
    	return new Point(-10,-10);
   	}
   	public Point findPawn(Piece[][]bo, char[]data,boolean color){
   		int x=data[3]-48;
	   	int y=data[4]-48;
   		if(color){
   			if(data[2]=='1'){
	   			int col=(int)data[1]-97;
	   			if(bo[y][x]==null){
	   				return new Point(col+100,y+1+100);
	   			}
	   			else{
	   				return new Point(col,y+1);
	   			}
	   		}
	   		else{
	   			if(bo[y+1][x]==null){
	   				return new Point(x,y+2);
	   			}
	   			else{
	   				return new Point(x,y+1);
	   			}
	   		}
   		}
   		else if(!color){
   			if(data[2]=='1'){
	   			int col=(int)data[1]-97;
	   			if(bo[y][x]==null){
	   				return new Point(col+100,y-1+100);
	   			}
	   			else{
	   				return new Point(col,y-1);
	   			}
	   		}
	   		else{
	   			if(bo[y-1][x]==null){
	   				return new Point(x,y-2);
	   			}
	   			else{
	   				return new Point(x,y-1);
	   			}
	   		}
   		}
   		return new Point(-10,-10);
   	}
    public Point findOrigin(Piece[][]bo, String move,boolean color){
    	char[] data=parseMove(move);
    	if(data[0]=='C' || data[0]=='K'){
    		return findKing(bo, data,color);
    	}
    	else if(data[0]=='B'){
    		return findBishop(bo,data,color);
    	}
    	else if(data[0]=='R'){
    		return findRook(bo,data,color);
    	}
    	else if(data[0]=='Q'){
    		return findQueen(bo,data,color);
    	}
    	else if(data[0]=='N'){
    		return findKnight(bo,data,color);
    	}
    	else if(data[0]=='P'){
    		return findPawn(bo,data,color);
    	}
    	return new Point(-20,-20);
    }
    public char[] parseMove(String move){
    	char []ans=new char[5];
    	if(move.equals("O-O-O")){
    		ans[0]='C';
    		ans[1]='Q';
    		for(int i=2;i<5;i++){
    			ans[i]='-';
    		}
    		return ans;
    	}
    	else if(move.equals("O-O")){
    		ans[0]='C';
    		ans[1]='K';
    		for(int i=2;i<5;i++){
    			ans[i]='-';
    		}
    		return ans;
    	}
    	if(move.charAt(move.length()-1)=='+' || move.charAt(move.length()-1)=='#'){
    		move=move.substring(0,move.length()-1);
    	}
    	if(Character.isUpperCase(move.charAt(0))){
    		ans[0]=move.charAt(0);
    		move=move.substring(1,move.length());
    	}
    	else{
    		ans[0]='P';
    	}
    	ans[4]=Character.forDigit(8-Integer.parseInt(move.charAt(move.length()-1)+""),10);
    	move=move.substring(0,move.length()-1);
    	ans[3]=Character.forDigit(move.charAt(move.length()-1)-97,10);
    	move=move.substring(0,move.length()-1);
    	if(move.length()>0){
    		if(move.charAt(0)!='x'){
	    		ans[1]=move.charAt(0);
	    		move=move.substring(1,move.length());
	    		if(move.length()>0){
	    			ans[2]='1';
	    		}
	    		else{
	    			ans[2]='0';
	    		}
	    	}
	    	else{
	    		ans[2]='1';
	    		ans[1]='z';
	    	}
    	}
    	else{
    		ans[2]='0';
    		ans[1]='z';
    	}
    	
    	return ans;
    }
    public void printData(char[]data){
    	for(int i=0;i<data.length;i++){
	   		System.out.print(data[i]);
	   	}
	   	System.out.print("\n");
    }
}