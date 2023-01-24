package pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import models.Admin;
import models.Student;
import models.Teacher;
import util.CustomImage;
import util.CellRenderer;

public class Dashboard extends JPanel {

	private static final long serialVersionUID = -3421670490444154816L;

	/**
	 * @wbp.parser.constructor
	 */
	public Dashboard(JFrame frame, Student user, JPanel login) {
		System.out.println("Student");
		JPanel dashboardFrame = new JPanel();

		dashboardFrame.setBackground(new Color(238, 238, 238));
		dashboardFrame.setBounds(0, -7, 1480, 701);
		frame.getContentPane().add(dashboardFrame);
		dashboardFrame.setVisible(true);
		dashboardFrame.setLayout(null);
		CustomImage roundLogo = new CustomImage("../resources/logo_round.png");

		JLabel title = new JLabel("Welcome back, " + user.getName() + " ! 👋");
		title.setFont(new Font("Futura", Font.PLAIN, 20));
		title.setBounds(188, 33, 449, 37);
		dashboardFrame.add(title);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd");
		JLabel subtitle = new JLabel(sdf.format(cal.getTime()));
		subtitle.setEnabled(false);
		subtitle.setFont(new Font("Futura", Font.PLAIN, 15));
		subtitle.setBounds(188, 65, 449, 21);
		dashboardFrame.add(subtitle);

		JPanel searchBar = new JPanel();
		searchBar.setBackground(Color.WHITE);
		searchBar.setBounds(936, 33, 418, 45);
		dashboardFrame.add(searchBar);
		searchBar.setLayout(null);
		JLabel searchIcon = new JLabel(new ImageIcon(getClass().getResource("../resources/search_icon.png")));
		searchIcon.setBounds(382, 0, 30, 45);
		searchBar.add(searchIcon);

		JTextField searchField = new JTextField(30);
		searchField.setFont(new Font("Futura", Font.PLAIN, 15));
		searchField.setBorder(null);
		searchField.setBackground(Color.WHITE);
		searchField.setBounds(21, 0, 360, 45);
		searchBar.add(searchField);
		searchField.setLayout(new BorderLayout());

		JLabel title2 = new JLabel("Available Courses");
		title2.setFont(new Font("Futura", Font.PLAIN, 20));
		title2.setBounds(188, 212, 280, 37);
		dashboardFrame.add(title2);

		JPanel coursesPanel = new JPanel();
		coursesPanel.setBackground(new Color(252, 255, 255));
		coursesPanel.setBounds(188, 263, 676, 369);
		dashboardFrame.add(coursesPanel);
		coursesPanel.setLayout(null);

		JLabel lblTeachers = new JLabel("Teachers");
		lblTeachers.setFont(new Font("Futura", Font.PLAIN, 20));
		lblTeachers.setBounds(936, 212, 280, 37);
		dashboardFrame.add(lblTeachers);

		JPanel teachersPanel = new JPanel();
		teachersPanel.setBackground(Color.WHITE);
		teachersPanel.setBounds(936, 262, 418, 369);
		dashboardFrame.add(teachersPanel);
		teachersPanel.setLayout(null);

		DefaultListModel<Teacher> model = new DefaultListModel<Teacher>();
		for (int i = 1; i <= 5; i++) {
			model.addElement(new Teacher("Teacher " + i));
		}
		JList<Teacher> teachers = new JList<Teacher>(model);
		teachers.setCellRenderer(new CellRenderer());
		teachers.setBorder(null);
		teachers.setBounds(0, 0, 418, 365);
		JScrollPane scrollPane = new JScrollPane(teachers);
		scrollPane.setBounds(0, 0, 418, 369);
		teachersPanel.add(scrollPane);

		JPanel sidebar = new JPanel();
		sidebar.setBackground(new Color(255, 255, 255));
		sidebar.setBounds(0, 0, 134, 701);
		dashboardFrame.add(sidebar);
		sidebar.setLayout(null);
		JLabel logoImg = new JLabel(roundLogo.getImage(50, 50));
		logoImg.setBounds(38, 41, 50, 50);
		sidebar.add(logoImg);

		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login.setVisible(true);
				dashboardFrame.setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Futura", Font.PLAIN, 15));
		btnNewButton.setBounds(6, 634, 122, 37);
		sidebar.add(btnNewButton);
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				searchField.setText("");
			}
		});

	}

	public Dashboard(JFrame frame, Teacher user, JPanel login) {
		System.out.println("Teacher");
		JPanel dashboardFrame = new JPanel();

		dashboardFrame.setBackground(new Color(238, 238, 238));
		dashboardFrame.setBounds(0, -7, 1480, 701);
		frame.getContentPane().add(dashboardFrame);
		dashboardFrame.setVisible(true);
		dashboardFrame.setLayout(null);
		CustomImage roundLogo = new CustomImage("../resources/logo_round.png");

		JLabel title = new JLabel("Welcome back, " + user.getName() + " ! 👋");
		title.setFont(new Font("Futura", Font.PLAIN, 20));
		title.setBounds(188, 33, 449, 37);
		dashboardFrame.add(title);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd");
		JLabel subtitle = new JLabel(sdf.format(cal.getTime()));
		subtitle.setEnabled(false);
		subtitle.setFont(new Font("Futura", Font.PLAIN, 15));
		subtitle.setBounds(188, 65, 449, 21);
		dashboardFrame.add(subtitle);

		JPanel searchBar = new JPanel();
		searchBar.setBackground(Color.WHITE);
		searchBar.setBounds(936, 33, 418, 45);
		dashboardFrame.add(searchBar);
		searchBar.setLayout(null);
		JLabel searchIcon = new JLabel(new ImageIcon(getClass().getResource("../resources/search_icon.png")));
		searchIcon.setBounds(382, 0, 30, 45);
		searchBar.add(searchIcon);

		JTextField searchField = new JTextField(30);
		searchField.setFont(new Font("Futura", Font.PLAIN, 15));
		searchField.setBorder(null);
		searchField.setBackground(Color.WHITE);
		searchField.setBounds(21, 0, 360, 45);
		searchBar.add(searchField);
		searchField.setLayout(new BorderLayout());

		JLabel title2 = new JLabel("Available Courses");
		title2.setFont(new Font("Futura", Font.PLAIN, 20));
		title2.setBounds(188, 212, 280, 37);
		dashboardFrame.add(title2);

		JPanel coursesPanel = new JPanel();
		coursesPanel.setBackground(new Color(252, 255, 255));
		coursesPanel.setBounds(188, 261, 676, 370);
		dashboardFrame.add(coursesPanel);

		JLabel lblTeachers = new JLabel("Teachers");
		lblTeachers.setFont(new Font("Futura", Font.PLAIN, 20));
		lblTeachers.setBounds(936, 212, 280, 37);
		dashboardFrame.add(lblTeachers);

		JPanel teachersPanel = new JPanel();
		teachersPanel.setBackground(Color.WHITE);
		teachersPanel.setBounds(936, 262, 418, 369);
		dashboardFrame.add(teachersPanel);

		JPanel sidebar = new JPanel();
		sidebar.setBackground(new Color(255, 255, 255));
		sidebar.setBounds(0, 0, 134, 701);
		dashboardFrame.add(sidebar);
		sidebar.setLayout(null);
		JLabel logoImg = new JLabel(roundLogo.getImage(50, 50));
		logoImg.setBounds(38, 41, 50, 50);
		sidebar.add(logoImg);

		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login.setVisible(true);
				dashboardFrame.setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Futura", Font.PLAIN, 15));
		btnNewButton.setBounds(6, 634, 122, 37);
		sidebar.add(btnNewButton);
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				searchField.setText("");
			}
		});

	}

	public Dashboard(JFrame frame, Admin user, JPanel login) {
		System.out.println("Admin");
		JPanel dashboardFrame = new JPanel();

		dashboardFrame.setBackground(new Color(238, 238, 238));
		dashboardFrame.setBounds(0, -7, 1480, 701);
		frame.getContentPane().add(dashboardFrame);
		dashboardFrame.setVisible(true);
		dashboardFrame.setLayout(null);
		CustomImage roundLogo = new CustomImage("../resources/logo_round.png");

		JLabel title = new JLabel("Welcome back, " + user.getName() + " ! 👋");
		title.setFont(new Font("Futura", Font.PLAIN, 20));
		title.setBounds(188, 33, 449, 37);
		dashboardFrame.add(title);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd");
		JLabel subtitle = new JLabel(sdf.format(cal.getTime()));
		subtitle.setEnabled(false);
		subtitle.setFont(new Font("Futura", Font.PLAIN, 15));
		subtitle.setBounds(188, 65, 449, 21);
		dashboardFrame.add(subtitle);

		JPanel searchBar = new JPanel();
		searchBar.setBackground(Color.WHITE);
		searchBar.setBounds(936, 33, 418, 45);
		dashboardFrame.add(searchBar);
		searchBar.setLayout(null);
		JLabel searchIcon = new JLabel(new ImageIcon(getClass().getResource("../resources/search_icon.png")));
		searchIcon.setBounds(382, 0, 30, 45);
		searchBar.add(searchIcon);

		JTextField searchField = new JTextField(30);
		searchField.setFont(new Font("Futura", Font.PLAIN, 15));
		searchField.setBorder(null);
		searchField.setBackground(Color.WHITE);
		searchField.setBounds(21, 0, 360, 45);
		searchBar.add(searchField);
		searchField.setLayout(new BorderLayout());

		JLabel title2 = new JLabel("Available Courses");
		title2.setFont(new Font("Futura", Font.PLAIN, 20));
		title2.setBounds(188, 212, 280, 37);
		dashboardFrame.add(title2);

		JPanel coursesPanel = new JPanel();
		coursesPanel.setBackground(new Color(252, 255, 255));
		coursesPanel.setBounds(188, 261, 676, 370);
		dashboardFrame.add(coursesPanel);

		JLabel lblTeachers = new JLabel("Teachers");
		lblTeachers.setFont(new Font("Futura", Font.PLAIN, 20));
		lblTeachers.setBounds(936, 212, 280, 37);
		dashboardFrame.add(lblTeachers);

		JPanel teachersPanel = new JPanel();
		teachersPanel.setBackground(Color.WHITE);
		teachersPanel.setBounds(936, 262, 418, 369);
		dashboardFrame.add(teachersPanel);

		JPanel sidebar = new JPanel();
		sidebar.setBackground(new Color(255, 255, 255));
		sidebar.setBounds(0, 0, 134, 701);
		dashboardFrame.add(sidebar);
		sidebar.setLayout(null);
		JLabel logoImg = new JLabel(roundLogo.getImage(50, 50));
		logoImg.setBounds(38, 41, 50, 50);
		sidebar.add(logoImg);

		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login.setVisible(true);
				dashboardFrame.setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Futura", Font.PLAIN, 15));
		btnNewButton.setBounds(6, 634, 122, 37);
		sidebar.add(btnNewButton);
		searchIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				searchField.setText("");
			}
		});

	}
}
