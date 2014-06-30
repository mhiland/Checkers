package Game;

import java.util.Timer;
import java.util.TimerTask;


import viewer.connectSignals;



public class AI extends GameControl{
	
	public static Timer hh = new Timer();

	public AI(){
		
	}

	public void move() {
		
		hh.schedule(new TimerTask() {          
		    @Override
		    public void run() {
		        // 2s delay
		    	playersTurn = true;
				canvas.setTooltipText("Player One's Move");
				connectSignals.statusbar.setMessage("Player One's Move");
				return;
		    }
		}, 2000);
		
	}

}
