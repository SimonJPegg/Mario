/**
 * 
 */
package competition.uu2013.reddit;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.TextArea;

import javax.swing.JFrame;
import ch.idsia.benchmark.mario.engine.MarioVisualComponent;

/**
 * @author RobotCaleb
 *
 */
public class ASCIIFrame extends JFrame
{
	private static final long serialVersionUID = 8327358465710691901L;
	private TextArea asciiText;
	Dimension defaultSize = new Dimension(300, 300);
	Point defaultLocation = new Point(350, 350);
	Point gameLocation = new Point();
	Dimension gameSize = new Dimension();
	
	public ASCIIFrame()
	{
		super("ASCII Map - Seed: %d - Difficulty: %d");
		
		asciiText = new TextArea(20, 80);
		asciiText.setFont(new Font("Courier", Font.PLAIN, 10));
		getContentPane().add(asciiText);
		
		setVisible(true);
	}
	
	public void Update(String asciiMap, MarioVisualComponent marioComponent)
	{
		Point location = marioComponent.getLocationOnScreen();
		
		defaultLocation.x = location.x;
		defaultLocation.y = location.y + marioComponent.getSize().height;
		
		asciiText.setText(asciiMap);
		pack();
	}
	
	public void tick()
	{
		setLocation(defaultLocation);
	}
}
