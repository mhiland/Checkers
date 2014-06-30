package viewer;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.gnome.gdk.Pixbuf;
import org.gnome.glib.Glib;
import org.gnome.gtk.Builder;
import org.gnome.gtk.DrawingArea;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Window;





/**
 * Internal Viewer <br>
 * to view plots interactively.
 * @author mdh
 *
 */
public class Viewer{
	private Builder builder ;
	private Window win;
	protected static DrawingArea area;
	private OutputStream out;
	private OutputStream out2;
	private OutputStream out3;

	/**
	 * Construct the Viewer from external glade file
	 * @param args
	 */
	public Viewer(String windowTitle) {
		Glib.setProgramName(windowTitle);
		String[] args = null;
		Gtk.init(args);
		
		this.builder = new Builder();
		getGladeSetup();
		getIconSetup();
		area = (DrawingArea) builder.getObject("drawingArea");
		area.setTooltipText("Gnome Checkers");
		
	}

	
		/**
		 * Get external Glade file and set it up.
		 */
		private void getGladeSetup() {
			//Create Temp glade file
			try {
				File file = null;
				String resource = "checkers.glade";
				URL res = getClass().getResource(resource);
				System.out.println("success path "+res.getPath());
				System.out.println("success a1.1");
				if (res.toString().startsWith("jar:")) {
					try {
						System.out.println("success a2");
						InputStream input = getClass().getResourceAsStream(resource);
						file = File.createTempFile("tempfile", ".tmp");
						out = new FileOutputStream(file);
						int read;
						byte[] bytes = new byte[1024];
						while ((read = input.read(bytes)) != -1) {
							out.write(bytes, 0, read);
						}
						file.deleteOnExit();
						System.out.println("success a3");
					} catch (IOException ex) {
					}
				} else {
					System.out.println("success a4");
					file = new File(res.getFile());
					System.out.println("here 2"+file);
				}
				if (file != null && !file.exists()) {
					try {
						InputStream input = getClass().getResourceAsStream(resource);
						file = File.createTempFile("tempfile", ".tmp");
						System.out.println("success a5");
						out3 = new FileOutputStream(file);
						int read;
						byte[] bytes = new byte[1024];
						while ((read = input.read(bytes)) != -1) {
							out3.write(bytes, 0, read);
						}
						file.deleteOnExit();
					} catch (IOException ex) {
					}
				}
				System.out.println("success a6");
				builder.addFromFile(file.getPath());
				new connectSignals(builder);
				this.win = (Window) builder.getObject("window1");
				//win.setIcon(icon);
			} catch (Exception e) {
				
				try {
					
					builder.addFromFile("includes/checkers.glade");
					new connectSignals(builder);
					this.win = (Window) builder.getObject("window1");
					System.err.println("fall back gui launcher");
					
				}catch(Exception ex){
					System.err.println("Error 1: "+ e);
					System.out.println("GUI Launch error "+ex);
				}
			} 
	}
		
		/**
		 * Get external Icon File and set
		 */
		private void getIconSetup() {
			try {
				File icon = null;
				String iconResource = "icon.xpm";
				URL ires = getClass().getResource(iconResource);
				if (ires.toString().startsWith("jar:")) {
					try {
						InputStream input = getClass().getResourceAsStream(iconResource);
						icon = File.createTempFile("tempicon", ".xmp");
						out2 = new FileOutputStream(icon);
						int read;
						byte[] bytes = new byte[1024];

						while ((read = input.read(bytes)) != -1) {
							out2.write(bytes, 0, read);
						}
						Pixbuf tempIcon = new Pixbuf(icon.getPath());
						System.out.println(icon.getPath());
						win.setIcon(tempIcon);
						System.out.println("Success b");
						icon.deleteOnExit();
					} catch (IOException e) {
						System.err.println("a " + e);
					}
				} else {
					icon = new File(ires.getFile());
					Pixbuf tempIcon = new Pixbuf(icon.getPath());
					win.setIcon(tempIcon);
					System.out.println("Success c");
				}
			}catch (Exception e){
				System.err.println("No icon " + e);
			}
	}
		
		

		/**
		 * Return the drawing area.
		 * @return area
		 */
		public DrawingArea getArea(){
			return area;
		}
	}
