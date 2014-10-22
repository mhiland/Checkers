package AI;

import Game.Game;
import Visuals.Tokens;


public class PriorityMove extends Game {
	Tokens token;
	int iPos;
	int fPos;
	private int priority;
	private static int o=1;
	private static int d=1;
	private static int offence;
	private static int defence;
	
	private int[] leftHalf = new int[]
			{1,3,
			8,10,
			17,19,
			24,26,
			33,35,
			40,42,
			49,51,
			56,58,};
	private int[] rightHalf = new int[]
			{5,7,
			12,14,
			21,23,
			28,30,
			37,39,
			44,46,
			53,55,
			60,62};
	private int[] topHalf = new int[]
			{1,3,5,7,
			8,10,12,14,
			17,19,21,23,
			24,26,28,30};
	private int[] bottomHalf = new int[]
			{33,35,37,39,
			40,42,44,46,
			49,51,53,55,
			56,58,60,62};
	protected int[] rCrown = new int[]{56,58,60,62};
	protected int[] bCrown = new int[]{1,3,5,7,};
	
	
	public PriorityMove(){
		return;
	}

	public int getPriority(Tokens token, int iPos, int fPos) {
		offence = 0;
		defence = 0;
		this.token = token;
		this.iPos = iPos;
		this.fPos = fPos;
//		if (rTokens.size() > bTokens.size()){
//			o = 1;
//			d = 2;
//		}else{
//			o = 2;
//			d = 1;
//		}
//		
		
		priority = (o*offence()+d*defence());
		if (token.isKing() && (bKings<2))
			priority-=3;
		//System.out.println("i:"+iPos+" f:"+fPos+" p:"+priority);
		return priority;
	}
	
	private boolean isRightMove(){
		return (fPos == iPos+7);
	}

	private int defence() {
		
		for(Tokens t:rTokens)
			
			// dont give away
			if ((t.getPos() == (fPos+7)&& (t.isKing()))
			|| (t.getPos() == (fPos+9)&& (t.isKing()))){
				defence -=1;
			}
		    if (redToken(fPos -9) && (isOpen(fPos +9)|| iPos ==(fPos +9))){
		    	defence -=1;
		    }
		    if (redToken(fPos -7)&& (isOpen(fPos +7) || iPos ==(fPos +7))){
		    	defence-=1;
		    }
		    
		    // dont leave a piece open
			if (redToken(iPos-18)&&(blackToken(iPos-9)))
				defence-=2;
			if (redToken(iPos-14)&&(blackToken(iPos-7)))
				defence-=2;
			
		    //backup a piece in trouble (block single jump)
			if (redToken(fPos-18)&& blackToken(fPos-9))
				defence+=2;
			if (redToken(fPos-14)&& blackToken(fPos-7))
				defence+=2;
		return defence;
	}
	
	

	private int offence() {
		//setup to make jump
		if(isOpen(fPos-18)&&redToken(fPos-9)&&
				((blackToken(fPos+9)&& ((token.getPos()!=fPos+9)) || !isValid(fPos+9))))
			offence+=1;
		else if((isOpen(fPos-14)&&redToken(fPos-7))&&
				((blackToken(fPos+7)&& (token.getPos()!=fPos+7))|| !isValid(fPos+7)))
			offence+=1;
		
		return offence;
	}

	public boolean isOpen(int i){
		return (isValid(i) && !redToken(i) && ! blackToken(i));
	}
	
	//
	// The distribution of the board 
	// sorted by red/black top/bottom/left/right
	//
	public int rightRedCount(){
		int val=0;
		for(Tokens r : rTokens){
			for(int i :rightHalf)
			if (rightHalf[i] == r.getPos()){
				val++;
			}
		}
		return val;
	}
	public int leftRedCount(){
		int val=0;
		for(Tokens r : rTokens){
			for(int i :leftHalf)
			if (leftHalf[i] == r.getPos()){
				val++;
			}
		}
		return val;
	}
	public int topRedCount(){
		int val=0;
		for(Tokens r : rTokens){
			for(int i :topHalf)
			if (topHalf[i] == r.getPos()){
				val++;
			}
		}
		return val;
	}
	public int bottomRedCount(){
		int val=0;
		for(Tokens r : rTokens){
			for(int i :bottomHalf)
			if (bottomHalf[i] == r.getPos()){
				val++;
			}
		}
		return val;
	}
	public int leftBlackCount(){
		int val=0;
		for(Tokens b : bTokens){
			for(int i :leftHalf)
			if (leftHalf[i] == b.getPos()){
				val++;
			}
		}
		return val;
	}
	public int rightBlackCount(){
		int val=0;
		for(Tokens b : bTokens){
			for(int i :rightHalf)
			if (rightHalf[i] == b.getPos()){
				val++;
			}
		}
		return val;
	}
	public int topBlackCount(){
		int val=0;
		for(Tokens b : bTokens){
			for(int i :topHalf)
			if (topHalf[i] == b.getPos()){
				val++;
			}
		}
		return val;
	}public int bottomBlackCount(){
		int val=0;
		for(Tokens b : bTokens){
			for(int i :bottomHalf)
			if (bottomHalf[i] == b.getPos()){
				val++;
			}
		}
		return val;
	}
	

}
