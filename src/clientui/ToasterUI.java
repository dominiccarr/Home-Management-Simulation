package clientui;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.ToasterClient;

/**
 * The Class ToasterUI.
 */
public class ToasterUI extends ClientUI implements ChangeListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1015688243621455514L;
	private final ToasterClient parent;
	private JButton startToasting;
	private final ToasterUICanvas canvas;
	private int temperature = 1;
	private JSlider levels;

	/**
	 * Instantiates a new ToasterUI.
	 * 
	 * @param client
	 *            the client
	 */
	public ToasterUI(ToasterClient client) {
		super(client);
		parent = client;
		canvas = new ToasterUICanvas();
		init();
	}

	@Override
	public void init() {
		super.init();
		levels = new JSlider(1, 7);
		levels.setPaintTicks(true);
		controls.add(levels);
		levels.addChangeListener(this);
		add(canvas);
		canvas.setBounds(160, 40, 300, 200);

		scroll.setBounds(5, 255, UIConstants.COMPONENTWIDTH, 150);

		startToasting = new JButton("Toast");
		JButton[] buttons = { startToasting };
		add(buttons);

	}

	/**
	 * The Class ToasterUICanvas.
	 */
	private class ToasterUICanvas extends Canvas {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 5332989616895284474L;
		private final ImageIcon toasterImage = new ImageIcon(
				"src/resources/toaster.png");
		private Image toDraw;
		private boolean d = false;

		/**
		 * Instantiates a new ToasterUI canvas.
		 */
		public ToasterUICanvas() {
		}

		public void refresh() {
			paint(this.getGraphics());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.Canvas#paint(java.awt.Graphics)
		 */
		@Override
		public void paint(Graphics g) {
			super.paint(this.getGraphics());
			Graphics2D g2 = (Graphics2D) g;

			if (!d) {
				toDraw = toasterImage.getImage().getScaledInstance(
						getWidth() - 100, getHeight(), 0);
				d = true;
			}

			g2.drawImage(toDraw, 0, 0, this);
			g2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			g2.drawString("Level " + temperature, 0, getHeight() - 5);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startToasting) {
			parent.startToasting();
		}
	}

	public void showToastDone() {
		canvas.refresh();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			parent.adjust(source.getValue());
		}
	}

	public void adjust(int i) {
		temperature = i;
		canvas.refresh();
	}
}
