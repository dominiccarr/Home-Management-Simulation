/*
 * 
 */
package clientui;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.TVClient;

/**
 * The Class TVUI.
 */
public class TVUI extends ClientUI implements ActionListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1015688243621455514L;
	private JButton channel1;
	private JButton channel2;
	private JButton channel3;
	private JButton channel4;
	private JButton channel5;
	private JButton channel6;
	private JButton channel7;
	private JButton channel8;
	private JButton channel9;
	private JButton channel0;
	private JPanel buttonPanel;
	private final TVClient parent;
	private JButton getListings;
	private JButton volumeUp;
	private JButton volumeDown;
	private final TVUICanvas canvas;
	private int channel = 1;
	private int volume = 10;

	/**
	 * Instantiates a new TVUI.
	 * 
	 * @param client
	 *            the client
	 */
	public TVUI(TVClient client) {
		super(client);
		parent = client;
		canvas = new TVUICanvas();
		init();
	}

	@Override
	public void init() {
		super.init();
		channel1 = new JButton("1");
		channel2 = new JButton("2");
		channel3 = new JButton("3");
		channel4 = new JButton("4");
		channel5 = new JButton("5");
		channel6 = new JButton("6");
		channel7 = new JButton("7");
		channel8 = new JButton("8");
		channel9 = new JButton("9");
		channel0 = new JButton("0");
		JButton[] channelButtons = { channel0, channel1, channel2, channel3,
				channel4, channel5, channel6, channel7, channel8, channel9 };
		add(canvas);
		canvas.setBounds(5, 40, 520, 200);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 3));
		addButtons(channelButtons);
		add(buttonPanel);
		buttonPanel.setBounds(0, 245, UIConstants.COMPONENTWIDTH / 2, 150);
		scroll.setBounds(UIConstants.COMPONENTWIDTH / 2 + 5, 245,
				UIConstants.COMPONENTWIDTH / 2 - 5, 150);
		volumeUp = new JButton("+");
		volumeDown = new JButton("-");
		getListings = new JButton("Listings");
		JButton[] volumeButtons = { volumeUp, volumeDown, getListings };
		add(volumeButtons);

	}

	/**
	 * Adds the buttons.
	 * 
	 * @param a
	 *            the a
	 */
	public void addButtons(JButton[] a) {
		for (JButton in : a) {
			in.addActionListener(actionListener);
			buttonPanel.add(in);
		}
	}

	/**
	 * The Class TVUICanvas.
	 */
	private class TVUICanvas extends Canvas {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 5332989616895284474L;
		private final ImageIcon TV = new ImageIcon("src/resources/monitor2.png");
		private Image toDraw;
		private boolean d = false;

		/**
		 * Instantiates a new tVUI canvas.
		 * 
		 * @param i
		 *            the i
		 */
		public TVUICanvas() {
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
				toDraw = TV.getImage().getScaledInstance(getWidth() - 5,
						getHeight() - 5, 0);
				d = true;
			}
			g2.drawImage(toDraw, 0, 0, this);
			g2.setFont(new Font("Times New Roman", Font.PLAIN, 100));
			g2.drawString(channel + "", (getWidth() / 2) - 30,
					getHeight() / 2 + 30);
			g2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			g2.drawString("Volume " + volume, getWidth() - 150,
					getHeight() - 30);
		}
	}

	/** The action listener. */
	ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			JButton buttonPressed = (JButton) actionEvent.getSource();
			String channel = buttonPressed.getText();
			System.out.println(channel);
			parent.choose(channel);
			setChannel(Integer.valueOf(channel));
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == volumeUp) {
			parent.volumeUp();
		} else if (e.getSource() == volumeDown) {
			parent.volumeDown();
		} else if (e.getSource() == getListings) {
			parent.getListings();
		}
	}

	public void setChannel(int i) {
		channel = i;
		canvas.refresh();
	}

	public void setVolume(int i) {
		volume = i;
		canvas.refresh();
	}

	public void volumeDown() {
		volume--;
		canvas.refresh();
	}

	public void volumeUp() {
		volume++;
		canvas.refresh();
	}
}
