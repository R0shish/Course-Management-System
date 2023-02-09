import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import auth.Auth;
import pages.SplashScreen;
import util.CustomImage;
import util.DataManager;
import util.DataRetriever;
import util.DatabaseManager;

public class App {

	private JFrame frmHeraldCourseManagement;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmHeraldCourseManagement.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public App() {
		initialize();
	}

	private void initialize() {
		frmHeraldCourseManagement = new JFrame();
		frmHeraldCourseManagement.setTitle("Herald Course Management System");
		frmHeraldCourseManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHeraldCourseManagement.getContentPane().setLayout(null);
		frmHeraldCourseManagement.setSize(1480, 720);
		frmHeraldCourseManagement.setResizable(false);

		CustomImage logo = new CustomImage("../resources/logo.png");

		new SplashScreen(frmHeraldCourseManagement, logo);

		try {
			DatabaseManager db = DatabaseManager.getInstance();
			new Auth(db);
			new DataRetriever();
			new DataManager();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null,
					"Can not connect to database!\nPlease make sure mySQL is correctly setup and running!",
					"Error 500: Server Communication Failed", JOptionPane.ERROR_MESSAGE);
			System.exit(500);
		}
	}
}
