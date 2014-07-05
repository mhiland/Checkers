package Game;


public class Position extends GameArea {

	int pos;
	double x; 
	double y;
	static float size= 8;
	


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
	public static double xpos(int i){
		double skip = width/size;
		if (i ==0)
			return width/size/2;
		return  skip += xpos(--i);
	}

	public static double ypos(int i){
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pos;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (pos != other.pos)
			return false;
		return true;
	}
	
}
