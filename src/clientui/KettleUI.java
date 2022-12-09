package clientui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import client.KettleClient;

public class KettleUI extends ClientUI {

	private static final long serialVersionUID = -5745186095513967316L;
	private JButton boil;
	private final KettleClient parent;
	private KettleCanvas canvas;

	public KettleUI(KettleClient kettleClient) {
		super(kettleClient);
		parent = kettleClient;
		init();
	}

	@Override
	public void init() {
		super.init();
		boil = new JButton("Boil");
		canvas = new KettleCanvas();
		add(canvas);
		scroll.setBounds(5, 255, UIConstants.COMPONENTWIDTH, 150);
		canvas.setBounds(145, 40, 300, 210);
		add(new JButton[] { boil });
	}

	/**
	 * The Class KettleCanvas.
	 */
	private class KettleCanvas extends Canvas implements MouseListener {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1852807954117585858L;
		private final ImageIcon kettle = new ImageIcon(
				"src/resources/kettle.png");

		private Image a;
		private boolean b = false;

		/**
		 * Instantiates a new kettle canvas.
		 */
		public KettleCanvas() {
			this.addMouseListener(this);
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
			if (!b) {
				a = kettle.getImage().getScaledInstance(getWidth(),
						getHeight() - 25, 1);
				b = true;
			}
			g2.setColor(Color.blue);
			g2.drawImage(a, 0, 0, this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			parent.boil();
			paint(this.getGraphics());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == boil) {
			parent.boil();
		}
	}
}