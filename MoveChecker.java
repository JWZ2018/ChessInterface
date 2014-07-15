import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
public class MoveChecker {
	private String[]res;
    public MoveChecker() {
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
    public String[] checkRook(String[]res,int cx,int cy, int nx, int ny, Piece[][] board){
    	if(cx==nx){
    		for(int i=Math.min(cy,ny)+1;i<Math.max(cy,ny);i++){
    			if(board[i][cx]!=null){
    				res[0]=String.valueOf(false);
    				return res;
    			}
    		}
    		if(cx==0 && cy==0){
    			res[4]=String.valueOf(false);
    		}
    		else if(cx==0 && cy==7){
    			res[2]=String.valueOf(false);
    		}
    		else if(cx==7 && cy==0){
    			res[3]=String.valueOf(false);
    		}
    		else if(cx==7 && cy==7){
    			res[1]=String.valueOf(false);
    		}
    		res[0]=String.valueOf(true);
    		res[5]="-";
    		return res;
    	}
    	else if(cy==ny){
    		for(int i=Math.min(cx,nx)+1;i<Math.max(cx,nx);i++){
    			if(board[cy][i]!=null){
    				res[0]=String.valueOf(false);
    				return res;
    			}
    		}
    		if(cx==0 && cy==0){
    			res[4]=String.valueOf(false);
    		}
    		else if(cx==0 && cy==7){
    			res[2]=String.valueOf(false);
    		}
    		else if(cx==7 && cy==0){
    			res[3]=String.valueOf(false);
    		}
    		else if(cx==7 && cy==7){
    			res[1]=String.valueOf(false);
    		}
    		res[0]=String.valueOf(true);
    		res[5]="-";
    		return res;
    	}
    	res[0]=String.valueOf(false);
    	return res;
    }
    public String[] checkBishop(String[] res,int cx, int cy, int nx, int ny, Piece[][]board){
    	if(Math.abs(nx-cx)!=Math.abs(ny-cy)){
    		res[0]=String.valueOf(false);
    		return res;
    	}
    	int diff=Math.abs(cx-nx);
    	if(cx>nx){
    		if(cy>ny){
    			for(int i=1;i<diff;i++){
    				if(board[cy-i][cx-i]!=null){
    					res[0]=String.valueOf(false);
    					return res;
    				}
    			}
    			res[0]=String.valueOf(true);
    			res[5]="-";
    			return res;
    		}
    		else{
    			for(int i=1;i<diff;i++){
    				if(board[cy+i][cx-i]!=null){
    					res[0]=String.valueOf(false);
    					return res;
    				}
    			}
    			res[0]=String.valueOf(true);
    			res[5]="-";
    			return res;
    		}
    	}
    	else{
    		if(cy>ny){
    			for(int i=1;i<diff;i++){
    				if(board[cy-i][cx+i]!=null){
    					res[0]=String.valueOf(false);
    					return res;
    				}
    			}
    			res[0]=String.valueOf(true);
    			res[5]="-";
    			return res;
    		}
    		else{
    			for(int i=1;i<diff;i++){
    				if(board[cy+i][cx+i]!=null){
    					res[0]=String.valueOf(false);
    					return res;
    				}
    			}
    			res[0]=String.valueOf(true);
    			res[5]="-";
    			return res;
    		}
    	}
    }
    public String[] checkQueen(String[]res, int cx, int cy, int nx, int ny, Piece[][]board){
    	if(cx==nx || cy==ny){
    		return checkRook(oneMakeCopy(res),cx,cy,nx,ny,board);
    	}
    	else if(Math.abs(cx-nx)==Math.abs(cy-ny)){
    		return checkBishop(oneMakeCopy(res),cx,cy,nx,ny,board);
    	}
    	res[0]=String.valueOf(false);
    	return res;
    }
    public String[] checkKnight(String[]res, int cx,int cy,int nx,int ny, Piece[][]board){
    	if(Math.abs(cx-nx)==2 && Math.abs(cy-ny)==1){
    		res[0]=String.valueOf(true);
    		res[5]="-";
    		return res;
    	}
    	else if(Math.abs(cy-ny)==2 && Math.abs(cx-nx)==1){
    		res[0]=String.valueOf(true);
    		res[5]="-";
    		return res;
    	}
    	res[0]=String.valueOf(false);
    	return res;
    }
    public String[] checkPawn(String[]res, int cx,int cy, int nx, int ny, Piece[][]board,String enPassant){
    	if(cx==nx){
    		if(board[cy][cx].getColor()){
    			if(cy==6){
    				if(cy-ny==1){
    					res[0]=String.valueOf(true);
    					res[5]="-";
    					return res;
    				}
    				else if(cy-ny==2){
    					if(board[cy-1][cx]==null){
    						res[0]=String.valueOf(true);
    						res[5]=makeSquare(cx,cy-1);
    						//System.out.println(res[5]);
    						return res;
    					}
    					res[0]=String.valueOf(false);
    					return res;
    				}
    			}
    			else{
    				if(cy-ny==1){
    					res[0]=String.valueOf(true);
    					res[5]="-";
    					return res;
    				}
    				res[0]=String.valueOf(false);
    				return res;
    			}
    		}
    		else{
    			if(cy==1){
    				if(ny-cy==1){
    					res[0]=String.valueOf(true);
    					res[5]="-";
    					return res;
    				}
    				else if(ny-cy==2){
    					if(board[cy+1][cx]==null){
    						res[0]=String.valueOf(true);
    						res[5]=makeSquare(cx,cy+1);
    						//System.out.println(res[5]);
    						return res;
    					}
    					res[0]=String.valueOf(false);
    					return res;
    				}
    			}
    			else{
    				if(ny-cy==1){
    					res[0]=String.valueOf(true);
    					res[5]="-";
    					return res;
    				}
    				res[0]=String.valueOf(false);
    				return res;
    			}
    		}
    	}
    	else if(Math.abs(cx-nx)==1){
    		int[]eCoord=makeCoord(enPassant);
    		if(board[cy][cx].getColor()){
    			if(cy-ny==1){
    				if(board[ny][nx]!=null && board[ny][nx].getColor()==!board[cy][cx].getColor()){
    					res[0]=String.valueOf(true);
    					res[5]="-";
    					return res;
    				}
    				else if(nx==eCoord[0] && ny==eCoord[1]){
    					res[0]="en";
    					res[5]="-";
    					//System.out.println("sold");
    					return res;
    				}
    				res[0]=String.valueOf(false);
    				return res;
    			}
    			res[0]=String.valueOf(false);
    			return res;
    		}
    		else{
    			if(ny-cy==1){
    				if(board[ny][nx]!=null && board[ny][nx].getColor()==!board[cy][cx].getColor()){
    					res[0]=String.valueOf(true);
    					res[5]="-";
    					return res;
    				}
    				else if(nx==eCoord[0] && ny==eCoord[1]){
    					res[0]=String.valueOf(true);
    					res[5]="-";
    					return res;
    				}
    				res[0]=String.valueOf(false);
    				return res;
    			}
    			res[0]=String.valueOf(false);
    			return res;
    		}
    	}
    	res[0]=String.valueOf(false);
    	return res;
    }
    public String[] checkKing(String[]res,int cx,int cy, int nx,int ny, Piece[][] board,boolean []castling,String ua,String enPassant){
    	if(Math.abs(cx-nx)<=1 && Math.abs(cy-ny)<=1){
    		res[0]=String.valueOf(true);
    		res[1]=String.valueOf(false);
    		res[2]=String.valueOf(false);
    		res[3]=String.valueOf(false);
    		res[4]=String.valueOf(false);
    		res[5]="-";
    		return res;
    	}
    	else if(ua.equals("false")){
    		if(board[cy][cx].getColor()){
	    		if(cx==4 && cy==7 && nx==6 && ny==7){
	    			if(castling[0]){
	    				if(board[7][5]==null && board[7][6]==null){
	    					boolean passCheck=checkCheck(twoMakeCopy(board), enPassant,castling,true,4,7,5,7,ua);
	    					if(!passCheck){
	    						res[0]="WK";
		    					res[1]=String.valueOf(false);
		    					res[2]=String.valueOf(false);
		    					res[5]="-";
		    					return res;
	    					}
	    				}
	    			}
	    		}
	    		else if(cx==4 && cy==7 && nx==2 && ny==7){
	    			if(castling[1]){
	    				if(board[7][3]==null && board[7][3]==null && board[7][1]==null){
	    					boolean passCheck=checkCheck(twoMakeCopy(board), enPassant,castling,true,4,7,3,7,ua);
	    					if(!passCheck){
	    						res[0]="WQ";
		    					res[1]=String.valueOf(false);
		    					res[2]=String.valueOf(false);
		    					res[5]="-";
		    					return res;
	    					}
	    				}
	    			}
	    		}
	    	}
	    	else if(!board[cy][cx].getColor()){
	    		if(cx==4 && cy==0 && nx==6 && ny==0){
	    			if(castling[2]){
	    				if(board[0][5]==null && board[0][6]==null){
	    					boolean passCheck=checkCheck(twoMakeCopy(board), enPassant,castling,false,4,0,5,0,ua);
	    					if(!passCheck){
	    						res[0]="BK";
		    					res[1]=String.valueOf(false);
		    					res[2]=String.valueOf(false);
		    					res[5]="-";
		    					return res;
	    					}
	    				}
	    			}
	    		}
	    		else if(cx==4 && cy==0 && nx==2 && ny==0){
	    			if(castling[3]){
	    				if(board[0][3]==null && board[0][3]==null && board[0][1]==null){
	    					boolean passCheck=checkCheck(twoMakeCopy(board), enPassant,castling,false,4,0,3,0,ua);
	    					if(!passCheck){
	    						res[0]="BQ";
		    					res[1]=String.valueOf(false);
		    					res[2]=String.valueOf(false);
		    					res[5]="-";
		    					return res;
	    					}
	    				}
	    			}
	    		}
	    	}
    	}
    	
    	res[0]=String.valueOf(false);
    	return res;
    }
    public String[] check(Piece p, Piece[][]board,String enPassant,boolean[]castling,int cx,int cy, int nx, int ny,String ua,boolean cc){
    	String[] res=new String[7];
    	res[1]=String.valueOf(false);
    	res[1]=String.valueOf(castling[0]);
    	res[2]=String.valueOf(castling[1]);
    	res[3]=String.valueOf(castling[2]);
    	res[4]=String.valueOf(castling[3]);
    	res[5]=enPassant;
    	res[6]="-";
    	if(board[ny][nx]!=null && board[ny][nx].getColor()==board[cy][cx].getColor()){
    		res[0]=String.valueOf(false);
    		return res;
    	}
    	if(p.getName()=='r' || p.getName()=='R'){
    		res=checkRook(oneMakeCopy(res),cx,cy,nx,ny,board);
    		res[6]=checkOther(oneMakeCopy(res),cx,cy,nx,ny,board);
    	}
    	else if(p.getName()=='b' || p.getName()=='B'){
    		res=checkBishop(oneMakeCopy(res),cx,cy,nx,ny,board);
    	}
    	else if(p.getName()=='q' || p.getName()=='Q'){
    		res=checkQueen(oneMakeCopy(res),cx,cy,nx,ny,board);
    	}
    	else if(p.getName()=='n' || p.getName()=='N'){
    		res=checkKnight(oneMakeCopy(res),cx,cy,nx,ny,board);
    		res[6]=checkOther(oneMakeCopy(res),cx,cy,nx,ny,board);
    	}
    	else if(p.getName()=='p' || p.getName()=='P'){
    		res=checkPawn(oneMakeCopy(res),cx,cy,nx,ny,board,enPassant);
    	}
    	else if(p.getName()=='k' || p.getName()=='K'){
    		res=checkKing(oneMakeCopy(res),cx,cy,nx,ny,board,castling,ua,enPassant);
    	}
    	if(res[0]!="false" && cc){
    		boolean c=checkCheck(twoMakeCopy(board),enPassant,castling,board[cy][cx].getColor(),cx,cy,nx,ny,ua);
    		if(c){
    			System.out.println("Invalid"+(board[cy][cx].getColor())+" is in check");
    			res[0]=String.valueOf(false);
    			return res;
    		}
    		c=checkCheck(twoMakeCopy(board),enPassant,castling,!board[cy][cx].getColor(),cx,cy,nx,ny,ua);
    		if(c){
    			if(checkCheckMate(twoMakeCopy(board),!p.getColor(),castling,enPassant)){
    				res[0]+="Ma";
    				System.out.println("CheckMate");
    			}
    			else{
    				res[0]+="Ch";
    			}
    			return res;
    		}
    	}
    	return res;
    }
    public String checkOther(String[]res,int cx,int cy, int nx,int ny,Piece[][]board){
    	for(int j=0;j<8;j++){
    		for(int i=0;i<8;i++){
    			if(board[j][i]!=null && board[j][i].getName()==board[cy][cx].getName()){
    				if(cx!=i || cy!=j){
    					if(board[cy][cx].getName()=='n' || board[cy][cx].getName()=='N'){
    						String[] otherRes=checkKnight(oneMakeCopy(res),i,j,nx,ny,board);
	    					if(!otherRes[0].equals("false")){
	    						if(cx!=i){
	    							return (char)(97+cx)+"";
	    						}
	    						else{
	    							return ""+(8-cy);
	    						}
	    					}
	    					return "-";
    					}
    					if(board[cy][cx].getName()=='r' || board[cy][cx].getName()=='R'){
    						String[] otherRes=checkRook(oneMakeCopy(res),i,j,nx,ny,board);
	    					if(!otherRes[0].equals("false")){
	    						if(cx!=i){
	    							return (char)(97+cx)+"";
	    						}
	    						else{
	    							return ""+(8-cy);
	    						}
	    					}
	    					return "-";
    					}
    				}
    			}
    		}
    	}
    	return "-";
    }
    public boolean checkCheckMate(Piece[][]bo, boolean col,boolean[]castling,String enPassant){
    	boolean validMoves=false;
    	for(int j=0;j<8;j++){
    		for(int i=0;i<8;i++){
    			if(bo[j][i]!=null && bo[j][i].getColor()!=col){
    				ArrayList<Point>moves=getValidMoves(bo[j][i],bo,enPassant,castling,i,j);
    				for(int k=0;k<moves.size();k++){
    					boolean c=checkCheck(twoMakeCopy(bo),enPassant,castling,col,i,j,(int)moves.get(k).getX(),(int)moves.get(k).getY(),"true");
    					if(!c){
    						validMoves=true;
    						break;
    					}
    				}
    				if(validMoves){
    					break;
    				}
    			}
    		}
    		if(validMoves){
    			break;
    		}
    	}
    	return validMoves;
    }
    public boolean checkCheck(Piece[][] bo, String enPassant, boolean [] castling,boolean col,int cx,int cy, int nx,int ny,String ua){
    	bo[ny][nx]=bo[cy][cx];
    	bo[cy][cx]=null;
    	int kx=-1,ky=-1;
    	boolean val=false;
    	for(int i=0;i<8;i++){
    		for(int j=0;j<8;j++){
    			if(bo[i][j]!=null){
    				if(bo[i][j].getName()=='K' && col){
	    				ky=i;
	    				kx=j;
	    				val=true;
	    				break;
	    			}
	    			else if(bo[i][j].getName()=='k' && !col){
	    				ky=i;
	    				kx=j;
	    				val=true;
	    				break;
	    			}
    			}
    		}
    		if(val){
    			break;
    		}
    	}
    	for(int j=0;j<8;j++){
    		for(int i=0;i<8;i++){
    			if(bo[j][i]!=null && bo[j][i].getColor()!=col){
    				String [] r=check(bo[j][i],bo,enPassant,castling,i,j,kx,ky,ua,false);
    				if(!r[0].equals("false")){
    					System.out.println(bo[j][i].getName()+" "+i+" "+j);
    					
    					return true;
    				}
    			}
    		}
    	}
    	return false;
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
    public String[] oneMakeCopy(String [] r){
    	String[] copy=new String[r.length];
    	for(int i=0;i<r.length;i++){
    		copy[i]=r[i];
    	}
    	return copy;
    }
    public void printBoard(Piece[][]b){
    	for(int i=0;i<8;i++){
    		for(int j=0;j<8;j++){
    			if(b[i][j]!=null){
    				System.out.print(b[i][j].getName());
    			}
    			else{
    				System.out.print("-");
    			}
    		}
    		System.out.print("\n");
    	}
    }
    public boolean checkBounds(int x,int y){
    	if(x>=0 && x<8 && y>=0 && y<8){
    		return true;
    	}
    	return false;
    }
    public ArrayList<Point> getValidMoves(Piece p, Piece[][]board,String enPassant,boolean[]castling,int cx,int cy){
    	ArrayList<Point> moves=new ArrayList<Point>();
    	if(p.getName()=='r' || p.getName()=='R'){
    		moves=getRookValid(p,twoMakeCopy(board),cx,cy);
    		return moves;
    	}
    	else if(p.getName()=='b' || p.getName()=='B'){
    		moves=getBishopValid(p,twoMakeCopy(board),cx,cy);
    		return moves;
    	}
    	else if(p.getName()=='q' || p.getName()=='Q'){
    		moves=getQueenValid(p,twoMakeCopy(board),cx,cy);
    		return moves;
    	}
    	else if(p.getName()=='n' || p.getName()=='N'){
    		moves=getKnightValid(p,twoMakeCopy(board),cx,cy);
    		return moves;
    	}
    	else if(p.getName()=='k' || p.getName()=='K'){
    		moves=getKingValid(p,twoMakeCopy(board),cx,cy,castling);
    		return moves;
    	}
    	else if(p.getName()=='p' || p.getName()=='P'){
    		moves=getPawnValid(p,twoMakeCopy(board),cx,cy,enPassant);
    		return moves;
    	}
    	return moves;
    }
    public ArrayList<Point> getRookValid(Piece p, Piece[][]bo, int cx,int cy){
    	ArrayList<Point> moves=new ArrayList<Point>();
    	int i=cx-1;
    	while(i>=0){
    		if(bo[cy][i]==null){
    			moves.add(new Point(i,cy));
    		}
    		else{
    			if(bo[cy][i].getColor()!=p.getColor()){
    				moves.add(new Point(i,cy));
    			}
    			break;
    		}
    		i--;
    	}
    	i=cx+1;
    	while(i<8){
    		if(bo[cy][i]==null){
    			moves.add(new Point(i,cy));
    		}
    		else{
    			if(bo[cy][i].getColor()!=p.getColor()){
    				moves.add(new Point(i,cy));
    			}
    			break;
    		}
    		i++;
    	}
    	i=cy-1;
    	while(i>=0){
    		if(bo[i][cx]==null){
    			moves.add(new Point(cx,i));
    		}
    		else{
    			if(bo[i][cx].getColor()!=p.getColor()){
    				moves.add(new Point(cx,i));
    			}
    			break;
    		}
    		i--;
    	}
    	i=cy+1;
    	while(i<8){
    		if(bo[i][cx]==null){
    			moves.add(new Point(cx,i));
    		}
    		else{
    			if(bo[i][cx].getColor()!=p.getColor()){
    				moves.add(new Point(cx,i));
    			}
    			break;
    		}
    		i++;
    	}
    	return moves;
    }
    public ArrayList<Point> getBishopValid(Piece p, Piece[][]bo, int cx,int cy){
    	ArrayList<Point> moves=new ArrayList<Point>();
    	int i=cx-1;
    	int j=cy-1;
    	while(i>=0 && j>=0){
    		if(bo[j][i]==null){
    			moves.add(new Point(i,j));
    		}
    		else{
    			if(bo[j][i].getColor()!=p.getColor()){
    				moves.add(new Point(i,j));
    			}
    			break;
    		}
    		i--;
    		j--;
    	}
    	i=cx-1;
    	j=cy+1;
    	while(i>=0 && j<8){
    		if(bo[j][i]==null){
    			moves.add(new Point(i,j));
    		}
    		else{
    			if(bo[j][i].getColor()!=p.getColor()){
    				moves.add(new Point(i,j));
    			}
    			break;
    		}
    		i--;
    		j++;
    	}
    	i=cx+1;
    	j=cy-1;
    	while(i<8 && j>=0){
    		if(bo[j][i]==null){
    			moves.add(new Point(i,j));
    		}
    		else{
    			if(bo[j][i].getColor()!=p.getColor()){
    				moves.add(new Point(i,j));
    			}
    			break;
    		}
    		i++;
    		j--;
    	}
    	i=cx+1;
    	j=cy+1;
    	while(i<8 && j<8){
    		if(bo[j][i]==null){
    			moves.add(new Point(i,j));
    		}
    		else{
    			if(bo[j][i].getColor()!=p.getColor()){
    				moves.add(new Point(i,j));
    			}
    			break;
    		}
    		i++;
    		j++;
    	}
    	return moves;
    }
    public ArrayList<Point> getQueenValid(Piece p, Piece[][]bo, int cx,int cy){
    	ArrayList<Point>bishopValid=getBishopValid(p,bo,cx,cy);
    	ArrayList<Point>rookValid=getRookValid(p,bo,cx,cy);
    	for (int i=0;i<bishopValid.size();i++){
    		rookValid.add(bishopValid.get(i));
    	}
    	return rookValid;
    }
    public ArrayList<Point> getKnightValid(Piece p, Piece[][]bo, int cx, int cy){
    	ArrayList<Point> moves=new ArrayList<Point>();
    	if(checkBounds(cx-2,cy-1)){
    		if(bo[cy-1][cx-2]==null){
    			moves.add(new Point(cx-2,cy-1));
    		}
    		else if(bo[cy-1][cx-2].getColor()!=p.getColor()){
    			moves.add(new Point(cx-2,cy-1));
    		}
    	}
    	if(checkBounds(cx-1,cy-2)){
    		if(bo[cy-2][cx-1]==null){
    			moves.add(new Point(cx-1,cy-2));
    		}
    		else if(bo[cy-2][cx-1].getColor()!=p.getColor()){
    			moves.add(new Point(cx-1,cy-2));
    		}
    	}
    	if(checkBounds(cx-2,cy+1)){
    		if(bo[cy+1][cx-2]==null){
    			moves.add(new Point(cx-2,cy+1));
    		}
    		else if(bo[cy+1][cx-2].getColor()!=p.getColor()){
    			moves.add(new Point(cx-2,cy+1));
    		}
    	}
    	if(checkBounds(cx+1,cy-2)){
    		if(bo[cy-2][cx+1]==null){
    			moves.add(new Point(cx+1,cy-2));
    		}
    		else if(bo[cy-2][cx+1].getColor()!=p.getColor()){
    			moves.add(new Point(cx+1,cy-2));
    		}
    	}
    	if(checkBounds(cx-1,cy+2)){
    		if(bo[cy+2][cx-1]==null){
    			moves.add(new Point(cx-1,cy+2));
    		}
    		else if(bo[cy+2][cx-1].getColor()!=p.getColor()){
    			moves.add(new Point(cx-1,cy+2));
    		}
    	}
    	if(checkBounds(cx+2,cy-1)){
    		if(bo[cy-1][cx+2]==null){
    			moves.add(new Point(cx+2,cy-1));
    		}
    		else if(bo[cy-1][cx+2].getColor()!=p.getColor()){
    			moves.add(new Point(cx+2,cy-1));
    		}
    	}
    	if(checkBounds(cx+2,cy+1)){
    		if(bo[cy+1][cx+2]==null){
    			moves.add(new Point(cx+2,cy+1));
    		}
    		else if(bo[cy+1][cx+2].getColor()!=p.getColor()){
    			moves.add(new Point(cx+2,cy+1));
    		}
    	}
    	if(checkBounds(cx+1,cy+2)){
    		if(bo[cy+2][cx+1]==null){
    			moves.add(new Point(cx+1,cy+2));
    		}
    		else if(bo[cy+2][cx+1].getColor()!=p.getColor()){
    			moves.add(new Point(cx+1,cy+2));
    		}
    	}
    	return moves;
    }
public ArrayList<Point> getKingValid(Piece p, Piece[][]bo, int cx, int cy,boolean[] castling){
    	ArrayList<Point> moves=new ArrayList<Point>();
    	if(checkBounds(cx-1,cy-1)){
    		if(bo[cy-1][cx-1]==null){
    			moves.add(new Point(cx-1,cy-1));
    		}
    		else if(bo[cy-1][cx-1].getColor()!=p.getColor()){
    			moves.add(new Point(cx-1,cy-1));
    		}
    	}
    	if(checkBounds(cx,cy-1)){
    		if(bo[cy-1][cx]==null){
    			moves.add(new Point(cx,cy-1));
    		}
    		else if(bo[cy-1][cx].getColor()!=p.getColor()){
    			moves.add(new Point(cx,cy-1));
    		}
    	}
    	if(checkBounds(cx+1,cy-1)){
    		if(bo[cy-1][cx+1]==null){
    			moves.add(new Point(cx+1,cy-1));
    		}
    		else if(bo[cy-1][cx+1].getColor()!=p.getColor()){
    			moves.add(new Point(cx+1,cy-1));
    		}
    	}
    	if(checkBounds(cx+1,cy)){
    		if(bo[cy][cx+1]==null){
    			moves.add(new Point(cx+1,cy));
    		}
    		else if(bo[cy][cx+1].getColor()!=p.getColor()){
    			moves.add(new Point(cx+1,cy));
    		}
    	}
    	if(checkBounds(cx+1,cy+1)){
    		if(bo[cy+1][cx+1]==null){
    			moves.add(new Point(cx+1,cy+1));
    		}
    		else if(bo[cy+1][cx+1].getColor()!=p.getColor()){
    			moves.add(new Point(cx+1,cy+1));
    		}
    	}
    	if(checkBounds(cx,cy+1)){
    		if(bo[cy+1][cx]==null){
    			moves.add(new Point(cx,cy+1));
    		}
    		else if(bo[cy+1][cx].getColor()!=p.getColor()){
    			moves.add(new Point(cx,cy+1));
    		}
    	}
    	if(checkBounds(cx-1,cy+1)){
    		if(bo[cy+1][cx-1]==null){
    			moves.add(new Point(cx-1,cy+1));
    		}
    		else if(bo[cy+1][cx-1].getColor()!=p.getColor()){
    			moves.add(new Point(cx-1,cy+1));
    		}
    	}
    	if(checkBounds(cx-1,cy)){
    		if(bo[cy][cx-1]==null){
    			moves.add(new Point(cx-1,cy));
    		}
    		else if(bo[cy][cx-1].getColor()!=p.getColor()){
    			moves.add(new Point(cx-1,cy));
    		}
    	}
    	if(p.getColor()){
    		if(castling[0]){
    			if(bo[7][5]==null && bo[7][6]==null){
    				moves.add(new Point(6,7));
    			}
    		}
    		if(castling[1]){
    			if(bo[7][3]==null && bo[7][2]==null && bo[7][1]==null){
    				moves.add(new Point(2,7));
    			}
    		}
    	}
    	if(!p.getColor()){
    		if(castling[2]){
    			if(bo[0][5]==null && bo[0][6]==null){
    				moves.add(new Point(6,0));
    			}
    		}
    		if(castling[3]){
    			if(bo[0][3]==null && bo[0][2]==null && bo[0][1]==null){
    				moves.add(new Point(2,0));
    			}
    		}
    	}
    	return moves;
    }
    public ArrayList<Point> getPawnValid(Piece p, Piece[][]bo,int cx,int cy, String enPassant){
    	ArrayList<Point> moves=new ArrayList<Point>();
    	if(p.getColor()){
    		if(cy==6){
    			if(bo[5][cx]==null){
    				moves.add(new Point(cx,5));
    			}
    			if(bo[4][cx]==null){
    				moves.add(new Point(cx,4));
    			}
    		}
    		else{
    			if(bo[cy-1][cx]==null){
    				moves.add(new Point(cx,cy-1));
    			}
    		}
    		if(cx>0){
    			if(bo[cy-1][cx-1]!=null && bo[cy-1][cx-1].getColor()!=p.getColor()){
    				moves.add(new Point(cx-1,cy-1));
    			}
    		}
    		if(cx<7){
    			if(bo[cy-1][cx+1]!=null && bo[cy-1][cx+1].getColor()!=p.getColor()){
    				moves.add(new Point(cx+1,cy-1));
    			}
    		}
    		if(!enPassant.equals("-")){
    			int[]eCoord=makeCoord(enPassant);
    			if(Math.abs(eCoord[0]-cx)==1 && eCoord[1]==cy-1){
    				moves.add(new Point(eCoord[0],eCoord[1]));
    			}
    		}
    	}
    	if(!p.getColor()){
    		if(cy==1){
    			if(bo[2][cx]==null){
    				moves.add(new Point(cx,2));
    			}
    			if(bo[3][cx]==null){
    				moves.add(new Point(cx,3));
    			}
    		}
    		else{
    			if(bo[cy+1][cx]==null){
    				moves.add(new Point(cx,cy+1));
    			}
    		}
    		if(cx>0){
    			if(bo[cy+1][cx-1]!=null && bo[cy+1][cx-1].getColor()!=p.getColor()){
    				moves.add(new Point(cx-1,cy+1));
    			}
    		}
    		if(cx<7){
    			if(bo[cy+1][cx+1]!=null && bo[cy+1][cx+1].getColor()!=p.getColor()){
    				moves.add(new Point(cx+1,cy+1));
    			}
    		}
    		if(!enPassant.equals("-")){
    			int[]eCoord=makeCoord(enPassant);
    			if(Math.abs(eCoord[0]-cx)==1 && eCoord[1]==cy+1){
    				moves.add(new Point(eCoord[0],eCoord[1]));
    			}
    			
    		}
    	}
    	return moves;
    }
}
