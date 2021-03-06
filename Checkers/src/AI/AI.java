package AI;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
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
	private PriorityMove pMove;

	public AI(){
		movePriorityQueue = new PriorityQueue<Move>(12, moveComparator);
		jumpPriorityQueue = new PriorityQueue<Jump>(12, jumpComparator);
		pMove = new PriorityMove();
	}


	/**
	 * Make the AI take its next move
	 * pause for 2s to simulate thinking
	 */
	public void move() {
		hh.schedule(new TimerTask() {          
			@Override
			public void run() {
				// 2s delay
				findAMove();
				game.reDrawGame();
				if(!endOfGame){
					playersTurn = true;
					canvas.setTooltipText("Player Ones Move");
					connectSignals.statusbar.setMessage("Player Ones Move");
				}else{
					canvas.setTooltipText("Game Over");
					connectSignals.statusbar.setMessage("Game Over");
					playersTurn = false;
				}
			}
		}, 2000);
	}



	/**
	 * Make the Best Jump or Move.
	 * 
	 */
	public void findAMove(){
		movePriorityQueue.clear();
		jumpPriorityQueue.clear();

		//find best jump/moves and add to priority queue
		for (Tokens j : bTokens){
			hasJump(j);
			hasMove(j);
		}
		//Get the best jump from the queue
		Jump bestJump;
		bestJump = jumpPriorityQueue.poll();
		if(bestJump != null){
			for (Tokens k : bTokens){
				if (k.getPos() == bestJump.getA()){
					makeJump(k,bestJump);
					return;
				}
			}
		}
		//Get the best move from the queue
		Move bestMove;
		bestMove = movePriorityQueue.poll();
		if(bestMove != null){
			//for all equal priority moves, randomly select or reject current move for the next
			for (Move m : movePriorityQueue){
				if( bestMove.getPriority()== m.getPriority()){
					int rn = new Random().nextInt(4);
					if (rn == 1){
						bestMove = m ;
					}
				}
			}
			for (Tokens k : bTokens){
				if (k.getPos() == bestMove.getA()){
					int tmp = k.getPos();
					k.setPos(bestMove.getB());
					System.out.println("A: "+ tmp + " "+ k.getPos()+ " m");
					isCrowned(k);
					return;
				}
			}
		}
		//If no moves or jumps to be made, ai loses.
		endOfGame = true;
		return ;
	}


	/**
	 * Execute the AI jump
	 * animated
	 * @param k
	 * @param path
	 */
	private void makeJump(Tokens k, Jump jump) {
		for (Tokens j : rTokens){
			if(j.getPos() == jump.getSkip()){
				rTokens.remove(j);
				int tmp = k.getPos();
				k.setPos(jump.getB());
				System.out.println("A: "+ tmp + " "+ k.getPos() + " j");
				isCrowned(k);
				jumpPriorityQueue.clear();
				if(hasJump(k))
					makeJump(k, jumpPriorityQueue.poll());
				return;
			}
		}
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

	/**
	 * Determines All Possible jumps for a token and adds to a priority queue
	 * @param aiToken
	 * @return
	 */
	private boolean hasJump(Tokens aiToken){
		int currentPosition = aiToken.getPos();
		boolean jumpable = false;
		for (Tokens j : rTokens){
			if((j.getPos() == currentPosition-7)&&(isOpen(aiToken,currentPosition-14))){
				int rank = getJumpRank(aiToken, currentPosition, currentPosition-14);
				jumpPriorityQueue.add(new Jump(aiToken, rank, currentPosition, currentPosition-7, currentPosition-14));
				jumpable = true;
			}
			if((j.getPos() == currentPosition-9)&&(isOpen(aiToken,currentPosition-18))){
				int rank = getJumpRank(aiToken, currentPosition, currentPosition-18);
				jumpPriorityQueue.add(new Jump(aiToken, rank, currentPosition, currentPosition-9, currentPosition-18));
				jumpable = true;
			}
			if((aiToken.isKing())&&(j.getPos() == currentPosition+7)&&(isOpen(aiToken,currentPosition+14))){
				int rank = getJumpRank(aiToken, currentPosition, currentPosition+14);
				jumpPriorityQueue.add(new Jump(aiToken, rank, currentPosition, currentPosition+7, currentPosition+14));
				jumpable = true;
			}
			if((aiToken.isKing())&&(j.getPos() == currentPosition+9)&&(isOpen(aiToken,currentPosition+18))){
				int rank = getJumpRank(aiToken, currentPosition, currentPosition-18);
				jumpPriorityQueue.add(new Jump(aiToken, rank, currentPosition, currentPosition+9, currentPosition+18));
				jumpable = true;
			}
		}
		return jumpable;
	}

	public int getJumpRank(Tokens token, int iPos, int fPos){
		//TODO rank against single / double / triple jumps / captures/ crowning
		return 0;
	}

	public int getMoveRank(Tokens token, int iPos, int fPos){
		//TODO rank against pitch/ giveaways / captures /crowning
		return pMove.getPriority(token, iPos, fPos);
	}

	//Comparator class implementation for Move
	public static Comparator<Move> moveComparator = new Comparator<Move>(){

		public int compare(Move m1, Move m2) {
			return (int) (m2.getPriority() - m1.getPriority());
		}
	};

	//Comparator class implementation for Jump
	public static Comparator<Jump> jumpComparator = new Comparator<Jump>(){

		public int compare(Jump j1, Jump j2) {
			return (int) (j1.getPriority() - j2.getPriority());
		}
	};	

}
