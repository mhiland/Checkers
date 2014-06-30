package Game;

import java.util.Date;

import Visuals.Tokens;

/**
 * Bundle Move info for AI-queue and for post-game review
 * @author mdh
 *
 */
public class Jump {
	Tokens token;
	int priority;
	int positionA;
	int positionB;
	long timeStamp;

	public Jump(Tokens token, int priority, int positionA, int positionB){
		Date date= new java.util.Date();
		this.token = token;
		this.priority= priority;
		this.positionA = positionA;
		this.positionB = positionB;
		timeStamp = date.getTime();
	}
	
	public int getPriority(){
		return priority;
	}
	
	public Tokens getToken(){
		return token;
	}
	
	public int getA(){
		return positionA;
	}
	
	public int getB(){
		return positionB;
	}
	
	public long getTimeStamp(){
		return timeStamp;
	}

	public Object getPath() {
		// TODO Auto-generated method stub
		return null;
	}
}
