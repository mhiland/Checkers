package viewer;

import org.gnome.gdk.EventButton;
import org.gnome.gdk.EventMask;
import org.gnome.gdk.EventMotion;
import org.gnome.gdk.RGBA;
import org.gnome.gtk.Builder;
import org.gnome.gtk.Button;
import org.gnome.gtk.Dialog;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.StateFlags;
import org.gnome.gtk.Statusbar;
import org.gnome.gtk.Widget;
import org.gnome.gtk.MenuItem.Activate;
import org.gnome.gtk.Widget.EnterNotifyEvent;
import org.gnome.gtk.Window;
import org.gnome.gtk.Button.Clicked;
import org.gnome.gtk.ImageMenuItem;
import org.gnome.gtk.MenuItem;
import org.gnome.pango.FontDescription;

import AI.AI;
import Game.GameArea;
import Game.GameControl;



/**
 * This class connects the GUI functionality from glade
 * @author mdh
 *
 */
public class connectSignals extends GameArea {
	private Builder builder;
	private static Window aboutWindow;
	private static Window window;
	private static boolean buttonPressed = false;
	private static GameControl game;
	public static Statusbar statusbar;
	private boolean start = true;
	
	public connectSignals(Builder bldr) {
		game = new GameControl();
		builder = bldr;
		aboutWindow = (Window) builder.getObject("aboutWindow");
		window = (Window) builder.getObject("window1");

		final Button button = (Button) builder.getObject("quit_button");
		button.connect(quitButton());

		final ImageMenuItem menuItemQuit = (ImageMenuItem) builder.getObject("menuitemquit");
		menuItemQuit.connect(quit(builder));
		
		final ImageMenuItem newGame = (ImageMenuItem) builder.getObject("newGame");
		newGame.connect((Activate) restartGame(builder));
		
		final ImageMenuItem about = (ImageMenuItem) builder.getObject("about");
		about.connect( showAbout(builder));
		
		statusbar = (Statusbar) builder.getObject("statusbar");
		statusbar.connect(  StatusBarInitial(statusbar));
		StatusBar(statusbar);
		
		mouseControl();	
	}
	
	


	private EnterNotifyEvent StatusBarInitial(final Statusbar statusbar) {
		window.connect(new Widget.MotionNotifyEvent() {
			public boolean onMotionNotifyEvent(Widget source, EventMotion event) {
				if (start && !endOfGame)
				statusbar.setMessage("Player One's Move");
				start = false;
				return false;
			}
		});
	return null;}




	private void StatusBar( Statusbar statusbar) {
	
		FontDescription desc = new FontDescription("DejaVu Serif, Book 11");
		statusbar.overrideFont(desc);
		statusbar.overrideBackground(StateFlags.NORMAL,  RGBA.WHITE);
		statusbar.overrideColor(StateFlags.NORMAL, RGBA.BLACK);
		
		statusbar.setMessage("Ready");

	
		return ;
	}


	private Activate restartGame(Builder builder2) {
		// TODO reset stats
		return new MenuItem.Activate() {
			public void onActivate(MenuItem arg0) {
				System.out.println("restart");
				start = true;
				endOfGame = false;
				statusbar.setMessage("Ready");
				rTokens.clear();
				bTokens.clear();
				GameControl.setPlayersTurn();
				GameControl.setMultiJump();
				game.setupGame();
				game.reDrawGame();
			}
		};
	}


	// Handle Mouse Click/Drag feature
	private static EnterNotifyEvent mouseControl() {	
		window.addEvents(EventMask.BUTTON_RELEASE);
		window.addEvents(EventMask.BUTTON_MOTION);
		
		window.connect(new Widget.ButtonPressEvent() {
			public boolean onButtonPressEvent(Widget source, EventButton event) {
				//System.out.println("mouse button Down");
				System.out.println(Math.floor((event.getX()/width)*8 + Math.floor(((event.getY()-30)/(height))*(8))*8));
				buttonPressed = true;
				game.selectToken(event.getX(), event.getY());
				return false;
			}
		}); 
		
		window.connect(new Widget.ButtonReleaseEvent() {
			public boolean onButtonReleaseEvent(Widget source, EventButton event) {
				buttonPressed = false;
				game.deSelectToken(event.getX(), event.getY());
				return false;
			}
		}); 
		
		window.connect(new Widget.MotionNotifyEvent() {
			public boolean onMotionNotifyEvent(Widget source, EventMotion event) {
				if (buttonPressed)
					game.moveToken(event.getX(), event.getY());
				return false;
			}
		});
	return null;}
	
	
	// Get XY Position
	public static void mousePosition(EventButton event){
	double x, y;
   	 x = event.getX();
   	 y = event.getY();
   	 System.out.println("x: " + x + " "+ "y: " + y);
	}
	
	// Handle Mouse Position
	public static void mousePosition(double x, double y){
	   	 System.out.println("x: " + x + " "+ "y: " + y);
		}

	
	
	// quit button from file menu
	private static Activate quit(final Builder builder) {
		return new MenuItem.Activate() {
			public void onActivate(MenuItem arg0) {
				Gtk.mainQuit();
			}
		};    
	}


	// open about window
	private static Activate showAbout(final Builder builder) {
		return new MenuItem.Activate() {
			public void onActivate(MenuItem arg0) {
				aboutWindow.setTitle("  About ");
				((Dialog) aboutWindow).run();
				aboutWindow.hide();
			}
		};    
	}
	

	// quit button in main window
	private static Clicked quitButton() {
		return new Button.Clicked() {
			public void onClicked(Button source) {
				AI.hh.cancel();
				Gtk.mainQuit();
			}};
	}
	

}
