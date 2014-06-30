package Visuals;

import org.freedesktop.cairo.Context;

import Game.Game;
import Game.GameArea;
import Game.Position;

public class RedToken extends GameArea implements Tokens {
	Position token;
	boolean king;

	public  RedToken(int pos) {
		this.token = new Position(pos);
		king = false;
	}
	
	
	public boolean setPosition(double x, double y) {
		return token.setPosition(x, y);
	}

	
	public int getPos() {
		return token.getPos();
	}
	public boolean setPos(int i){
		return token.setPos(i);
	};
	public boolean setPos(double x, double y) {
		return token.setPos(x, y);
	}

	
	public void draw() {
		Context cr = Game.getContext();
		setColour(2);
		cr.arc( token.getX(), token.getY(),token.getRadius(), 0, 2*3.1415926);
		cr.stroke();
		cr.setLineWidth(2);
		if (king){
			cr.arc( token.getX(), token.getY(),token.getRadius()/1.2, 0, 2*3.1415926);
		}
		cr.stroke();
		return;
	}
	
	public void setKing(){
		king = true;
	}

	
	public boolean isKing() {
		return king;
	}

	
	public int checkPos(double x, double y) {
		return token.checkPos(x, y);
	}

	
	public boolean checkPos(int i) {
		return token.checkPos(i);
	}

}
