package GUI;

import java.awt.Dimension;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Table {
	
	private final JFrame gameframe;
	
	private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
	
	public Table ()
	{
		this.gameframe = new JFrame("JChess");
		final JMenuBar tableMenuBar = new JMenuBar();
	    populateMenuBar(tableMenuBar);	
	    this.gameframe.setJMenuBar(tableMenuBar);
		this.gameframe.setSize(OUTER_FRAME_DIMENSION);
		this.gameframe.setVisible(true);
		
		
	}
	
	private void populateMenuBar (final JMenuBar tableMenuBar) {
		tableMenuBar.add(createFileMenu());		
	}

	private JMenu createFileMenu() {
		final JMenu     fileMenu = new JMenu("File");
		final JMenuItem openPGN =  new JMenuItem("Load PGN File");
		
		openPGN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.err.println("Open up Pgn");
			}
		});
		fileMenu.add(openPGN);
		return fileMenu;
	}
}
