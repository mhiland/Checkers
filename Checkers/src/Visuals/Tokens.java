package Visuals;



public interface Tokens {
	
	public boolean setPosition(double x, double y);
	public boolean setPos(double x, double y);
	public boolean setPos(int i);
	public int getPos();
	public boolean isKing();
	public void setKing();
	public void draw();
	public int checkPos(double x, double y);
	public boolean checkPos(int i);
	public double getX();
	public double getY();

	public Tokens token(int i);
}

