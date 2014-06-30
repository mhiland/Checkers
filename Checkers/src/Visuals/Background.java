package Visuals;

import org.freedesktop.cairo.Context;

import Game.Game;
import Game.GameArea;

/**
 * The Background and frame
 * @author mdh
 *
 */
public class Background extends GameArea{

	/**
	 * Initialize the background and frame
	 */
	public boolean setBackground() {
		Context cr = Game.getContext();
		int size= 8;
		//Background
		for (int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				int clr = (i+j)%2;
				if (clr == 1)
					clr =6;
				setColour(clr);
				cr.rectangle(j*width/size,  i*height/size, width/size, height/size);
				cr.fill();
			}
		}
		cr.fill();

		//Plot boundary
		setColour(1);
		cr.moveTo(0, 0);
		cr.lineTo(width, 0);
		cr.lineTo(width, height) ;
		cr.lineTo(0,  height);
		cr.lineTo(0, 0);
		cr.stroke();
		
//		setColour(1);
//		cr.arc( width/size/2, height/size/2,height/size/2, 0, 2*3.1415926);
		return true;
	}
}