/*
 * A UI for controlling and playing the Game of Life using the Mouse.
 * Uses the class GameOfLife to provide ruleset and board.
 * Written by Kevin Zhan
 * Last updated: 12/10/14
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Display extends JFrame implements ActionListener, ChangeListener,
		MouseMotionListener {
	/*
	 * Contains a toolBar JPanel with multiple JButtons, a JSlider, JTextFields,
	 * and a JCheckBox for controlling the game. Contains a board BoardPanel,
	 * which is used to display the game board and allow the user to
	 * individually set cells as alive or dead.
	 */
	private int speed = 10, rows = 45, cols = 90, spotSize = 10;
	private boolean isPlaying = false;
	private GameOfLife game = new GameOfLife(rows, cols);
	private String title = "Game of Life";
	private JPanel toolBar = new JPanel();
	private JButton play = new JButton("Play");
	private JButton next = new JButton("Next Generation");
	private JButton previous = new JButton("Previous Generation");
	private JButton reset = new JButton("Reset");
	private JLabel sliderLabel = new JLabel("Speed:", JLabel.CENTER);
	private JSlider slider = new JSlider(0, 60, speed);
	private JLabel genNumLabel = new JLabel("Generation:", JLabel.CENTER);
	private JTextField genNum = new JTextField("" + game.getGenNum(), 5);
	private JTextField speedNum = new JTextField("" + speed, 2);
	private JCheckBox grid = new JCheckBox("Show Grid", false);
	private Timer playTimer = new Timer(1000 / speed, this);
	private BoardPanel board = new BoardPanel();

	/*
	 * Sets up all of the components defined in the private data.
	 */
	public Display() {
		play.setActionCommand("play");
		play.addActionListener(this);
		next.setActionCommand("next");
		next.addActionListener(this);
		previous.setActionCommand("previous");
		previous.addActionListener(this);
		reset.setActionCommand("reset");
		reset.addActionListener(this);
		playTimer.setActionCommand("next");
		genNum.setEditable(false);
		speedNum.setEditable(false);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(2);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(this);
		grid.setActionCommand("grid");
		grid.addActionListener(this);
		toolBar.add(reset);
		toolBar.add(previous);
		toolBar.add(next);
		toolBar.add(play);
		toolBar.add(sliderLabel);
		toolBar.add(slider);
		toolBar.add(speedNum);
		toolBar.add(genNumLabel);
		toolBar.add(genNum);
		toolBar.add(grid);
		board.setPreferredSize(new Dimension(cols * spotSize, rows * spotSize));
		board.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
		board.addMouseMotionListener(this);
		board.setBackground(new Color(255, 255, 255));
		this.setTitle(title);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(1000, 600));
		this.setLayout(new FlowLayout());
		this.add(toolBar);
		this.add(board);
		this.setLocation(200, 200);
		this.pack();
		this.setVisible(true);
	}

	/*
	 * Takes ActionCommands from the JButtons and the JTextbox in toolBar and
	 * applies respective actions.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("next")) {
			game.nextGeneration();
			genNum.setText("" + game.getGenNum());
			board.repaint();
		} else if (command.equals("play")) {
			if (isPlaying) {
				isPlaying = false;
				playTimer.stop();
				play.setText("Play");
			} else {
				isPlaying = true;
				playTimer.start();
				play.setText("Stop");
			}
		} else if (command.equals("previous") && game.getGenNum() > 0) {
			game.previousGeneration();
			genNum.setText("" + game.getGenNum());
			board.repaint();
		} else if (command.equals("reset")) {
			game = new GameOfLife(rows, cols);
			genNum.setText("0");
			board.repaint();
		} else if (command.equals("grid"))
			board.repaint();
	}

	/*
	 * Detects if the JSlider has changed and sets the speed value and the
	 * number in speedNum accordingly.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		speed = slider.getValue();
		speedNum.setText("" + speed);
		if (speed == 0)
			playTimer.stop();
		else {
			playTimer.setDelay(1000 / speed);
			if (isPlaying && !playTimer.isRunning())
				playTimer.start();
		}
	}

	/*
	 * Creates new Display object.
	 */
	public static void main(String[] args) {
		new Display();
	}

	/*
	 * Used to override the paintComponment in JPanel to display alive and dead
	 * cells along with the grid when turned on.
	 */
	private class BoardPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			for (int i = 0; i < cols; i++)
				for (int j = 0; j < rows; j++) {
					if (game.isAlive(j, i))
						g2d.fillRect(i * spotSize, j * spotSize, spotSize,
								spotSize);
					else if (grid.isSelected())
						g2d.drawRect(i * spotSize, j * spotSize, spotSize,
								spotSize);
				}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (board.contains(e.getX(), e.getY())) {
			int row = (e.getY()) / spotSize;
			int col = (e.getX()) / spotSize;
			if (SwingUtilities.isLeftMouseButton(e) && !game.isAlive(row, col))
				game.set(row, col, true);
			else if (SwingUtilities.isRightMouseButton(e))
				game.set(row, col, false);
			board.repaint(col * spotSize, row * spotSize, spotSize, spotSize);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
