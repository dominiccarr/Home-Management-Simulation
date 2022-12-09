package clientui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import client.FridgeFreezerClient;

/**
 * 
 * @author dominic
 * 
 */
public class FridgeUI extends ClientUI {

	private static final long serialVersionUID = 7619226343561270265L;
	private JButton getContents;
	private JButton makeList;
	private final FridgeFreezerClient client;

	public FridgeUI(FridgeFreezerClient a) {
		super(a);
		client = a;
		init();
	}

	@Override
	public void init() {
		super.init();
		scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
		getContents = new JButton("Get Contents");
		makeList = new JButton("Make Shopping List");
		controls.add(getContents);
		controls.add(makeList);
		add(new JButton[] { getContents, makeList });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getContents) {
			client.getContents();
		} else if (e.getSource() == makeList) {
			client.getList();
		}
	}
}
