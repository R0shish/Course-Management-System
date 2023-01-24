package pages.admin;

import javax.swing.JPanel;

public class Courses extends JPanel {

	private static final long serialVersionUID = -4551964813659714257L;
	private static Courses instance;

	private Courses(JPanel main) {
		main.add(this);
		this.setBounds(0, 0, 1341, 701);
		setLayout(null);

		JPanel courses = new JPanel();
		courses.setLayout(null);
		courses.setBounds(113, 0, 1161, 701);
		add(courses);
	}

	// Singleton to ensure one and only instance

	public static Courses getInstance(JPanel main) {
		if (instance == null) {
			instance = new Courses(main);
		}
		return instance;
	}
}
