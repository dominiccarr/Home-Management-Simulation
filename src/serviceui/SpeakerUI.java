package serviceui;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import services.MediaServerService;
import services.SpeakersService;
import clientui.UIConstants;

public class SpeakerUI extends ServiceUI {

	private static final long serialVersionUID = 7602410216230631365L;
	private JProgressBar jpb;

	public SpeakerUI(final SpeakersService s, String title) {
		super(s, title);
	}

	@Override
	public void init() {
		setResizable(false);
		pack();
		setSize(UIConstants.UIWIDTH, 300);
		setLocation(setPosition(this));
		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		panel.setBounds(0, 0, UIConstants.UIWIDTH - 1, UIConstants.UIWIDTH - 1);
		setVisible(true);
		update(this.getGraphics());
		scroll.setBounds(5, 5, UIConstants.UIWIDTH - 10, 200);
		scroll.setViewportView(pane);
		panel.add(scroll);
		jpb = new JProgressBar(0, 200);
		jpb.setValue(0);
		jpb.setStringPainted(true);
		pane.setEditable(false);
		jpb.setBounds(5, 220, UIConstants.UIWIDTH - 10, 30);
		panel.add(jpb);
	}

	@Override
	public void updateArea(String s) {
		if (s.equals("update")) {
			int i = jpb.getValue();
			i += 1;
			jpb.setValue(i);
		} else {
			super.updateArea(s);
			jpb.setValue(0);
		}
	}

}
