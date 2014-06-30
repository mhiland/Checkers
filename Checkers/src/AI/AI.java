package AI;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import Game.GameControl;
import Game.Jump;
import Game.Move;
import Visuals.Tokens;
import viewer.connectSignals;



public class AI extends GameControl{

	public static Timer hh = new Timer();
	private Queue<Move> movePriorityQueue;
	private Queue<Jump> jumpPriorityQueue;

	public AI(){
		movePriorityQueue = new PriorityQueue<Move>(12, moveComparator);
		jumpPriorityQueue = new PriorityQueue<Jump>(12, jumpComparator);
	}


	public void move() {
		delay(false);
	}

	//this code works but not currently implemented untill bugs worked out
	private void delay(boolean pause){
		if(pause){
			//Take a Pause
			hh.schedule(new TimerTask() {          
				@Override
				public void run() {
					// 2s delay
					aiMain();
				}
			}, 2000);
		}else{
			aiMain();
		}
	}
	
	public void aiMain(){
		playersTurn = true;
		canvas.setTooltipText("Player One's Move");
		connectSignals.statusbar.setMessage("Player One's Move");
		findAMove();
		return;
	}

	/**
	 * Function to move a black token from one pos to another
	 * @param startPos
	 * @param finalPos
	 */
	public void move(int startPos, int finalPos){
		for (Tokens j : bTokens){
			if (j.getPos() == startPos){
				j.setPos(finalPos);
				game.reDrawGame();
			}
			return;
		}
	}

	/**
	 * Make the Best Jump or Move.
	 * 
	 */
	public void findAMove(){
		movePriorityQueue.clear();
		jumpPriorityQueue.clear();

		for (Tokens j : bTokens){
			hasJump(j);
			hasMove(j);
		}

		Jump bestJump;
		bestJump = jumpPriorityQueue.poll();
		if(bestJump != null){
			for (Tokens k : bTokens){
				if (k.getPos() == bestJump.getA()){
					makeJump(k,bestJump.getPath());
					game.reDrawGame();
					return;
				}
			}
		}

		Move bestMove;
		bestMove = movePriorityQueue.poll();
		if(bestMove != null){
			for (Tokens k : bTokens){
				if (k.getPos() == bestMove.getA()){
					k.setPos(bestMove.getB());
					game.reDrawGame();
					return;
				}
			}
		}
		System.out.println("end of game");
		endOfGame = true;
		return ;
	}

	private void makeJump(Tokens k, Object path) {
		// TODO Auto-generated method stub

	}


	/**
	 * Position Available/Unoccupied 
	 */
	@Override
	public boolean isOpen(Tokens token, int newPosition){
		return super.isOpen(token, newPosition);
	}

	/**
	 * If a Move is available, it is added to the queue.
	 * @param aiToken
	 * @return
	 */
	private boolean hasMove(Tokens aiToken) {
		boolean movable = false;
		int currentPosition = aiToken.getPos();

		if(isOpen(aiToken,currentPosition-7)){
			int rank = getMoveRank(aiToken, currentPosition, currentPosition-7);
			movePriorityQueue.add(new Move(aiToken, rank, currentPosition, currentPosition-7));
			movable = true;
		}
		if (isOpen(aiToken,currentPosition-9)){
			int rank = getMoveRank(aiToken, currentPosition, currentPosition-9);
			movePriorityQueue.add(new Move(aiToken, rank, currentPosition, currentPosition-9));
			movable = true;
		}
		if (aiToken.isKing() && isOpen(aiToken,currentPosition+7)){
			int rank = getMoveRank(aiToken, currentPosition, currentPosition+7);
			movePriorityQueue.add(new Move(aiToken, rank, currentPosition, currentPosition+7));
			movable = true;
		}
		if (aiToken.isKing() && isOpen(aiToken,currentPosition+9)){
			int rank = getMoveRank(aiToken, currentPosition, currentPosition+9);
			movePriorityQueue.add(new Move(aiToken, rank, currentPosition, currentPosition+9));
			movable = true;
		} 
		//else token has no move
		return movable;
	}

	public int getMoveRank(Tokens token, int iPos, int fPos){
		//TODO rank against pitch/ giveaways / captures /crowning
		return 0;
	}

	//Comparator class implementation for Move
	public static Comparator<Move> moveComparator = new Comparator<Move>(){

		public int compare(Move m1, Move m2) {
			return (int) (m1.getPriority() - m2.getPriority());
		}
	};

	//Comparator class implementation for Jump
	public static Comparator<Jump> jumpComparator = new Comparator<Jump>(){

		public int compare(Jump j1, Jump j2) {
			return (int) (j1.getPriority() - j2.getPriority());
		}
	};	

	private boolean hasJump(Tokens aiToken){
		//TODO rank against single / double / triple jumps / captures/ crowning
		return false;
	}
}
