package Game;


import viewer.connectSignals;
import AI.AI;
import Visuals.BlackToken;
import Visuals.RedToken;
import Visuals.Tokens;


public class GameControl extends Game {
	private int yoffset = -30;// because the menu bar is 30 in height
	private static Position tmp = new Position(0);
	private Tokens target = null;
	private static Tokens multiJump = null;
	protected Game game = Game.getGame();
	public static boolean playersTurn = true;
	private static AI ai = new AI();

	public void selectToken(double x, double y) {
		if (playersTurn){
			tmp.setPosition(x, y+yoffset);
			if(multiJump == null || tmp.getPos() == multiJump.getPos())
				for (Tokens i : rTokens)
					if (i.getPos() == tmp.getPos()){
						target = i;
						return;
					}
		} 
		return;
	}

	public void moveToken(double x, double y) {
		if (target != null){
			target.setPosition(x, y+yoffset);
			game.reDrawGame();
		}
	}

	public void deSelectToken(double x, double y) {
		if (target != null){	
			int val = target.checkPos(x, y+yoffset);
			//if target is a valid tile and has a valid move
			if (val !=-1 && isOpen(target, val) ){
				if(hasJump(tmp.getPos())){ 	
					// make jump if valid
					makeJump(val);
				}else if (!hasJump(tmp.getPos()) && hasMove(val)){
					//else make move
					target.setPos(val);	
					isCrowned(target);
				}else target.setPos(tmp.getPos());
			}
			else{	
				//else no move was made, reset to initial position
				target.setPos(tmp.getPos()); 
			}
			game.reDrawGame();
		}
		target = null;
		if(!endOfGame)
			if (playersTurn){
				canvas.setTooltipText("Player Ones Move");
				connectSignals.statusbar.setMessage("Player Ones Move");
			}else{
				canvas.setTooltipText("AIs Move");
				connectSignals.statusbar.setMessage("AIs Move");
				multiJump = null;
				callAI();
			}

		return;
	}

	public static void callAI(){
		ai.move();
		return;
	}

	// valid tile for a move or jump
	public boolean isOpen(Tokens token, int newPosition){
		// make sure doesn't land on either players tokens
		for (Tokens j : rTokens){
			if (j.getPos() == newPosition && j != token)
				return false;
		}
		for (Tokens j : bTokens){
			if (j.getPos() == newPosition)
				return false;
		}
		// make sure its still on the board
		if(newPosition < 0 || newPosition > 63)
			return false;
		// make sure its a playable tile
		if(!token.checkPos(newPosition))
			return false;
		// if no move was made
		if((token.getClass() == rTokens.getClass())&&(newPosition == tmp.getPos()))
			return false;
		return true;
	}



	// check if tmp had a valid single move
	// will not move if can jump.
	private boolean hasMove(int newPosition) {
		//check first if another token is in a position to jump
		for (Tokens j : rTokens){
			if (hasJump(j.getPos()) && j.getPos() != target.getPos()){
				target.setPos(tmp.getPos());
				return false;
			}	
		}
		if(tmp.getPos()+ 7 == newPosition || tmp.getPos()+ 9 == newPosition || 
				(target.isKing() && (tmp.getPos()- 7 == newPosition || tmp.getPos()- 9 == newPosition))){
			playersTurn = false;
			return true;
		}

		return false;
	}


	// check if tmp has a single jump
	private boolean hasJump(int position) {
		//single jump
		for (Tokens j : bTokens){
			if ((j.getPos() == (position+ 7 ))&& isOpen(target, position+14) ||
					(target.isKing() && (j.getPos() == position- 7 && isOpen(target,position-14)))){
				return true;
			}
			if ((j.getPos() ==( position+ 9)) && isOpen(target, position+18)||
					(target.isKing() && (j.getPos() == position- 9 && isOpen(target,position-18)))){
				return true;
			}
		}
		return false;
	}



	private boolean makeJump(int newPosition) {
		//single jump
		for (Tokens j : bTokens){
			if (j.getPos() == tmp.getPos()+ 7 && (j.getPos() == newPosition -7)){
				bTokens.remove(j);
				target.setPos(newPosition);
				isCrowned(target);
				playersTurn = hasJump(newPosition);
				if (hasJump(newPosition)){
					multiJump = target;
				}else
					multiJump = null;
				return true;
			}
			else if (target.isKing() && j.getPos() == tmp.getPos()- 7 && (j.getPos() == newPosition +7)){
				bTokens.remove(j);
				target.setPos(newPosition);
				isCrowned(target);
				playersTurn = hasJump(newPosition);
				if (hasJump(newPosition)){
					multiJump = target;
				}else
					multiJump = null;
				return true;
			}
			else if (j.getPos() == tmp.getPos()+ 9 && j.getPos() == newPosition - 9){
				bTokens.remove(j);
				target.setPos(newPosition);
				isCrowned(target);
				playersTurn = hasJump(newPosition);
				if (hasJump(newPosition)){
					multiJump = target;
				}else
					multiJump = null;
				return true;
			}else if (target.isKing() && j.getPos() == tmp.getPos()- 9 && j.getPos() == newPosition + 9){
				bTokens.remove(j);
				target.setPos(newPosition);
				isCrowned(target);
				playersTurn = hasJump(newPosition);
				if (hasJump(newPosition)){
					multiJump = target;
				}else
					multiJump = null;
				return true;
			}
		}
		target.setPos(tmp.getPos());
		isCrowned(target);
		return false;
	}
	
	/**
	 * Determine if token should be king,
	 * by checking if landed on a crowning-row.
	 * @param token
	 * @return true if token is now a king
	 */
	public boolean isCrowned(Tokens token){
		if (token.getClass() == RedToken.class){
			for (int i: rCrown){
				if (token.getPos() == i){
					token.setKing();
					return true;
				}
			}
		}else if (token.getClass() == BlackToken.class){
			for (int i: bCrown){
				if (token.getPos() == i){
					token.setKing();
					return true;
				}
			}
		}
		return false;
	}

	public static void setPlayersTurn(){
		playersTurn = true;
		return;
	}
	public static  void setMultiJump(){
		multiJump = null;
		return;
	}


}
