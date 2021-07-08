import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

public class RoundButton extends JButton{

	public RoundButton(ImageIcon imageIcon)
	{
		super(imageIcon);
		/**
		 * Create dimensions of the button
		 */
		Dimension size = getPreferredSize();
		size.width= size.height = Math.max(size.width, size.height);
		setPreferredSize(size);
		/**
		 * Fill the round background
		 */
		setContentAreaFilled(false);
	}
	/**
	 * Paint the oval in 
	 * 
	 * @param g Graphics that are used to fill in the circular button
	 */
	protected void paintCompoment(Graphics g)
	{
		if(getModel().isArmed())
		{
			g.setColor(Color.gray);

		}
		else{
			g.setColor(getBackground());
		}
		g.fillOval(0, 0, getSize().width-1,getSize().height-1 );
		/**
		 * Will call paint Label to paint 
		 */
		super.paintComponent(g);
	}
	/**
	 * Paint the border of the button
	 */
	protected void paintBorder(Graphics g )
	{
		g.setColor(getForeground());
		g.drawOval(0, 0, getSize().width-1,getSize().height-1);
	}
	/**
	 * Create hit detection in the button
	 */
	Shape shape;
	public boolean contains (int x, int y)
	{
		/**
		 * If the shape of the button is changed, the shape will scale to 
		 *  the changed shape
		 */
		if(shape==null||!shape.getBounds().equals(getBounds()))
		{
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x,y); 
	}
	/**
	 * Tester of the button
	 */
	public static void main(String[]args)
	{
		ImageIcon image = new ImageIcon("data\\Poker Chip 5.jpg");

		JButton button = new RoundButton(image);
		button.setBackground(Color.black);
		button.validate();

		JFrame frame = new JFrame();
		frame.getContentPane().setBackground(Color.GREEN);
		frame.getContentPane().add(button);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setSize(150,150);
		frame.setVisible(true);

	}
}


