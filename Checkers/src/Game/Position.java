package Game;


public class Position extends GameArea {

	int pos;
	double x; 
	double y;
	float size= 8;
	private int[] validPositions = new int[]
			{1,3,5,7,
			8,10,12,14,
			17,19,21,23,
			24,26,28,30,
			33,35,37,39,
			40,42,44,46,
			49,51,53,55,
			56,58,60,62};


	public Position(int i){
		setPos(i);
	}
	public Position(double x, double y){
		setPosition(x, y);
	}

	public boolean setPosition(double x, double y) {
		if (x<0) x = 0;
		else if (x>width) x = width-1;
		if (y<0) y = 0;
		else if (y>height) y = height-1;
		int val = (int) ((x/width)*size + Math.floor((y/(height))*(size))*size);
		//if (isValid(val)){
		setPos(val);
		this.x=x;
		this.y=y;
		return true;
		//}
		//	return false;
	}
	public boolean setPos(double x, double y) {
		if (x<0) x = 0;
		else if (x>width) x = width-1;
		if (y<0) y = 0;
		else if (y>height) y = height-1;
		int val = (int) ((x/width)*size + Math.floor((y/(height))*(size))*size);
		if (isValid(val)){
			setPos(val);
			return true;
		}
		return false;
	}


	public boolean setPos(int i){
		if (isValid(i)){
			pos = i;
			x = xpos((int) ( i % (size)));
			y = ypos((int) Math.floor((i/(size)))) ;
			return true;
		}
		return false;

	}
	private double xpos(int i){
		double skip = width/size;
		if (i ==0)
			return width/size/2;
		return  skip += xpos(--i);
	}

	private double ypos(int i){
		double skip = height/size;
		if (i ==0)
			return height/size/2;
		return  skip += ypos(--i);
	}

	public int getPos(){
		return pos;
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public double getRadius() {
		return height/size/2;
	}

	public boolean isValid(int val){
		for (int i : validPositions){
			if (i == val){
				return true;
			}
		}
		return false;

	}
	public int checkPos(double x, double y) {
		// -1 for false, val for true
		if (x<0 || x>width)  return -1;
		if (y<0 || y>height) return -1;
		int val = (int) ((x/width)*size + Math.floor((y/(height))*(size))*size);
		if( isValid(val))
			return val;
		return -1;
	}
	
	public boolean checkPos(int val) {
		return isValid(val);
	}
	
}
