/*
 * 
 */
package clientui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import client.LightClient;

/**
 * The Class LightUI.
 * 
 * @author dominic
 */
public class LightUI extends ClientUI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 845901338109882071L;
	private JButton on;
	private JButton off;
	private JButton dim;
	private final LightClient parent;
	public lightCanvas canvas;

	/**
	 * Instantiates a new light ui.
	 * 
	 * @param lightClient
	 *            the light client
	 */
	public LightUI(LightClient lightClient) {
		super(lightClient);
		parent = lightClient;
		init();
	}

	@Override
	public void init() {
		super.init();
		on = new JButton("On");
		off = new JButton("Off");
		dim = new JButton("Dim");
		canvas = new lightCanvas();
		add(canvas);
		canvas.setBounds(5, 40, 520, 150);
		scroll.setBounds(5, 200, UIConstants.COMPONENTWIDTH, 150);
		add(new JButton[] { on, off, dim });
	}

	/**
	 * Sets the canvas.
	 * 
	 * @param dimAmount
	 *            the new canvas
	 */
	public void setCanvas(int dimAmount) {

	}

	/**
	 * The Class lightCanvas.
	 */
	private class lightCanvas extends Canvas {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 8752511912198647756L;
		private Color lightColour = Color.black;
		private final Color yellow = new Color(255, 255, 0);

		/**
		 * Instantiates a new light canvas.
		 */
		public lightCanvas() {

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.Canvas#paint(java.awt.Graphics)
		 */
		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(lightColour);
			g2.fillRect(0, 0, getWidth(), getHeight());

		}

		/**
		 * Turn on.
		 */
		public void turnOn() {
			lightColour = yellow;
			paint(this.getGraphics());
		}

		/**
		 * Turn off.
		 */
		public void turnOff() {
			lightColour = Color.BLACK;
			paint(this.getGraphics());
		}

		/**
		 * Dim.
		 * 
		 * @param percent
		 *            the percent
		 */
		public void dim(int percent) {
			lightColour = Color.BLACK;
			paint(this.getGraphics());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == on) {
			parent.on();
		} else if (e.getSource() == off) {
			canvas.turnOff();
			parent.off();
		} else if (e.getSource() == dim) {
			parent.dim();
		}
	}

	public void on() {
		canvas.turnOn();
	}

	public void turnOff() {
		canvas.turnOff();
	}
}
