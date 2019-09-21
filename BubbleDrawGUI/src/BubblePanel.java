import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class BubblePanel extends JPanel {
	int size = 15;
	Timer timer;
	private final int DELAY = 33; // 30 fps
	ArrayList<Bubble> bubbleList;
	private JTextField txtSize;
	private JTextField txtSpeed;
	private JCheckBox chkGroup;
	private JCheckBox chkPause;

	BubblePanel() {
		addMouseListener(new BubbleListener());
		addMouseMotionListener(new BubbleListener());
		addMouseWheelListener(new BubbleListener());
		bubbleList = new ArrayList<Bubble>();
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(600, 400));

		JPanel panel = new JPanel();
		panel.setForeground(Color.ORANGE);
		add(panel);

		JLabel lblNewLabel = new JLabel("Bubble Size: ");
		panel.add(lblNewLabel);

		txtSize = new JTextField();
		txtSize.setText("30");
		panel.add(txtSize);
		txtSize.setColumns(3);

		JLabel lblNewLabel_1 = new JLabel("(fps): ");
		panel.add(lblNewLabel_1);

		txtSpeed = new JTextField();
		txtSpeed.setText("33");
		panel.add(txtSpeed);
		txtSpeed.setColumns(3);

		JButton btnNewButton = new JButton("Update");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get size from txtSize
				int newSize = Integer.parseInt(txtSize.getText());
				// get speed from txtSpeed
				int newSpeed = Integer.parseInt(txtSpeed.getText());
				// set size
				size = newSize;
				// set speed
				timer.setDelay(1000 / newSpeed);

			}
		});
		panel.add(btnNewButton);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bubbleList = new ArrayList<>();
			}
		});

		chkGroup = new JCheckBox("Group bubbles");
		panel.add(chkGroup);
		panel.add(btnClear);

		chkPause = new JCheckBox("Pause");
		chkPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkPause.isSelected())
					timer.stop();
					else 
						timer.start();
			}
		});
		panel.add(chkPause);

		timer = new Timer(DELAY, new BubbleListener());
		timer.start();

	}

	public void paintComponent(Graphics page) {
		super.paintComponent(page);

		// Draw all the Bubbles from bubbleList
		for (Bubble bubble : bubbleList) {
			page.setColor(bubble.color);
			page.fillOval(bubble.x - bubble.size / 2, bubble.y - bubble.size / 2, bubble.size, bubble.size);
			repaint();
		}
		// write the count of Bubbles
		page.setColor(Color.GREEN);
		page.drawString(" Count of bubbles: " + bubbleList.size(), 5, 15);

	}

	private class BubbleListener implements MouseListener, MouseMotionListener, MouseWheelListener, ActionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			bubbleList.add(new Bubble(e.getX(), e.getY(), size));
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			bubbleList.add(new Bubble(e.getX(), e.getY(), size));

			if (chkGroup.isSelected()) {
				bubbleList.get(bubbleList.size() - 1).xSpeed = bubbleList.get(bubbleList.size() - 2).xSpeed;
				bubbleList.get(bubbleList.size() - 1).ySpeed = bubbleList.get(bubbleList.size() - 2).ySpeed;
			}
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			size += e.getWheelRotation();
			txtSize.setText("" + size);
			repaint();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (Bubble bubble : bubbleList)
				bubble.update();
			repaint();

		}

	}

	private class Bubble {
		public int x;
		public int y;
		public int size;
		public Color color;
		public int xSpeed;
		public int ySpeed;
		private final int MAX_SPEED = 5;

		public Bubble(int newX, int newY, int newSize) {
			x = newX;
			y = newY;
			size = newSize;
			color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(),
					(float) Math.random());
			xSpeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
			ySpeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
			if (xSpeed == 0 && ySpeed == 0) {
				xSpeed = 1;
				ySpeed = 1;
			}
		}

		public void update() {
			x -= xSpeed;
			y -= ySpeed;

			if (x - size / 2 <= 0)
				xSpeed = (int) (Math.random() * -MAX_SPEED -1);
			if (x + size / 2 >= getWidth())
				xSpeed = (int) (Math.random() * MAX_SPEED +1);
			if (y - size / 2 <= 0)
				ySpeed = (int) (Math.random() * -MAX_SPEED -1);
			if (y + size / 2 >= getHeight())
				ySpeed = (int) (Math.random() * MAX_SPEED +1);
		}

	}

}
