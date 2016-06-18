package assignment2;

import java.awt.*;
import javax.swing.*;

public class JFrameTester extends JFrame {
	public JFrameTester(String str) {
		super("FrameTester");
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		Panel p = new Panel();
		p.add(new Label(str));
		JButton cmd = new JButton("PUSH");
		cp.add("North", p);
		cp.add("Center",cmd);
		cp.add("South", new Label("Sout"));
		pack();
		setVisible(true);
	}
	
  public static void main(String[] args) {
	  JFrameTester f = new JFrameTester("HUH");
  }
}
