package pages;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import util.CustomImage;

public class SplashScreen extends JPanel {

	private static final long serialVersionUID = -3421670490444154816L;
	private JProgressBar progressBar;
	private int currentProgress;
	private Timer timer;

	public SplashScreen(JFrame frame, CustomImage logo) {
		JPanel splashFrame = new JPanel();
		splashFrame.setBackground(new Color(255, 255, 255));
		splashFrame.setBounds(0, -7, 1480, 701);
		frame.getContentPane().add(splashFrame);
		splashFrame.setVisible(true);
		splashFrame.setLayout(null);

		JLabel logoImg = new JLabel(logo.getImage(100, 100));
		logoImg.setBounds(690, 259, 100, 100);
		splashFrame.add(logoImg);

		progressBar = new JProgressBar();
		progressBar.setBounds(690, 400, 100, 100);
		progressBar.setMaximum(100);
		splashFrame.add(progressBar);

		currentProgress = 0;

		timer = new Timer(3, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentProgress++;
				progressBar.setValue((int) ((currentProgress / (float) progressBar.getMaximum()) * 100));
				if (currentProgress == 100) {
					timer.stop();
					new SignUp(frame, logo);
					splashFrame.setVisible(false);
				}
			}
		});
		timer.start();

	}
}
