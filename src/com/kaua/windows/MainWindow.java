package com.kaua.windows;

import javax.swing.JFrame;
import com.kaua.windows.panels.MainPanel;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	public MainWindow(int w, int h) {
		
		getContentPane().add(new MainPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(w +15, h +40);
		setLocationRelativeTo(null);
		setVisible(true);

	}

}
