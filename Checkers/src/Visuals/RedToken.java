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


	public double getX(){
		return token.getX();
	}
	
	public double getY(){
		return token.getY();
	}
	
	public Tokens token(int i){
		for (Tokens t: rTokens){
			if (t.getPos() == i)
				return t;
		}
		return null;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (king ? 1231 : 1237);
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RedToken other = (RedToken) obj;
		if (king != other.king)
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
	

}
