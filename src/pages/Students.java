package pages;

import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import models.user.Admin;
import models.user.Student;
import models.user.SystemUser;
import models.user.Teacher;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import util.DataManager;
import util.DataRetriever;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Students extends JPanel {

	private static final long serialVersionUID = 7035062181966295884L;
	private static Students instance;
	JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public Students(JPanel main, SystemUser user) {
		main.add(this);
		this.setBounds(113, 0, 1341, 701);
		setLayout(null);

		JPanel students = new JPanel();
		students.setLayout(null);
		students.setBounds(113, 0, 1222, 701);
		add(students);

		String e = user instanceof Admin ? "Manage" : "View";
		JLabel title = new JLabel(e + " Students");
		title.setFont(new Font("Futura", Font.PLAIN, 25));
		title.setBounds(5, 75, 236, 36);
		students.add(title);

		JLabel addBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/resources/add.png")));
		addBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(6, 2));
				JLabel nameLabel = new JLabel("Enter student name: ");
				JTextField nameField = new JTextField();
				JLabel emailLabel = new JLabel("Enter student email: ");
				JTextField emailField = new JTextField();
				JLabel passwordLabel = new JLabel("Enter student password: ");
				JPasswordField passwordField = new JPasswordField();
				JLabel numberLabel = new JLabel("Enter student phone number: ");
				JTextField numberField = new JTextField();
				JLabel courseLabel = new JLabel("Enter course id: ");
				JTextField courseField = new JTextField();
				JLabel levelLabel = new JLabel("Enter student level: ");
				JTextField levelField = new JTextField();
				panel.add(emailLabel);
				panel.add(emailField);

				panel.add(nameLabel);
				panel.add(nameField);

				panel.add(passwordLabel);
				panel.add(passwordField);

				panel.add(numberLabel);
				panel.add(numberField);

				panel.add(courseLabel);
				panel.add(courseField);

				panel.add(levelLabel);
				panel.add(levelField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Add Student", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String email = emailField.getText().trim();
					String name = nameField.getText().trim();
					String password = new String(passwordField.getPassword());
					String number = numberField.getText().trim();
					String courseId = courseField.getText().trim();
					String level = levelField.getText().trim();
					try {
						DataManager.addStudent(name, number, email, password, Integer.parseInt(courseId),
								Integer.parseInt(level));
						JOptionPane.showMessageDialog(null, "Student added successfully");
						refreshTable(students);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error adding student", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		addBtnLbl.setBounds(854, 47, 45, 45);

		JLabel editBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/resources/edit.png")));
		editBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(5, 2));
				JLabel idLabel = new JLabel("Enter student id: ");
				JTextField idField = new JTextField();
				JLabel nameLabel = new JLabel("Enter student name: ");
				JTextField nameField = new JTextField();
				JLabel emailLabel = new JLabel("Enter student email: ");
				JTextField emailField = new JTextField();
				JLabel passwordLabel = new JLabel("Enter student password: ");
				JPasswordField passwordField = new JPasswordField();
				JLabel numberLabel = new JLabel("Enter student phone number: ");
				JTextField numberField = new JTextField();

				panel.add(idLabel);
				panel.add(idField);

				panel.add(emailLabel);
				panel.add(emailField);

				panel.add(nameLabel);
				panel.add(nameField);

				panel.add(passwordLabel);
				panel.add(passwordField);

				panel.add(numberLabel);
				panel.add(numberField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Update Student", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String id = idField.getText().trim();
					String email = emailField.getText().trim();
					String name = nameField.getText().trim();
					String password = new String(passwordField.getPassword());
					String number = numberField.getText().trim();
					try {
						DataManager.editStudent(id, name, number, email, password);
						JOptionPane.showMessageDialog(null, "Student updated successfully");
						refreshTable(students);

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error updating student", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		editBtnLbl.setBounds(946, 43, 45, 45);

		JLabel removeBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/resources/remove.png")));
		removeBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(1, 2));
				JLabel idLabel = new JLabel("Enter student id: ");
				JTextField idField = new JTextField();

				panel.add(idLabel);
				panel.add(idField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Delete Student", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String studentId = idField.getText();

					try {
						DataManager.deleteStudent(studentId);
						JOptionPane.showMessageDialog(null, "Student deleted successfully");
						refreshTable(students);

					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(null, "Error deleting student", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		removeBtnLbl.setBounds(1036, 47, 45, 45);

		JLabel addLbl = new JLabel("Add");
		addLbl.setHorizontalAlignment(SwingConstants.CENTER);
		addLbl.setBounds(846, 103, 61, 16);

		JLabel editLbl = new JLabel("Edit");
		editLbl.setHorizontalAlignment(SwingConstants.CENTER);
		editLbl.setBounds(931, 104, 61, 16);

		JLabel removeLbl = new JLabel("Remove");
		removeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		removeLbl.setBounds(1029, 103, 61, 16);

		if (user instanceof Admin) {
			students.add(removeBtnLbl);
			students.add(editBtnLbl);
			students.add(addBtnLbl);
			students.add(removeLbl);
			students.add(editLbl);
			students.add(addLbl);
		}
		createTable(students);

		if (user instanceof Teacher) {
			JButton markStudentBtn = new JButton("Mark Student");
			markStudentBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JPanel panel = new JPanel(new GridLayout(5, 2));
					JLabel idLabel = new JLabel("Enter student id: ");
					JTextField idField = new JTextField();
					JLabel moduleLabel = new JLabel("Enter module id: ");
					JTextField moduleField = new JTextField();
					JLabel marksLabel = new JLabel("Enter marks obtained: ");
					JTextField marksField = new JTextField();

					panel.add(idLabel);
					panel.add(idField);

					panel.add(moduleLabel);
					panel.add(moduleField);

					panel.add(marksLabel);
					panel.add(marksField);

					int result = JOptionPane.showConfirmDialog(null, panel, "Mark Student",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						String id = idField.getText().trim();
						String moduleId = moduleField.getText();
						String marks = marksField.getText().trim();
						try {
							DataManager.markStudent(id, moduleId, marks);
							JOptionPane.showMessageDialog(null, "Student marked successfully");
							refreshTable(students);

						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, "Error marking student", e1.getMessage(),
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
			markStudentBtn.setBounds(1021, 121, 117, 36);
			students.add(markStudentBtn);
		}
	}

	private void refreshTable(JPanel students) {
		students.remove(scrollPane);
		createTable(students);
		students.revalidate();
		students.repaint();
	}

	private void createTable(JPanel students) {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 185, 1138, 454);
		students.add(scrollPane);

		ArrayList<Student> studentData = DataRetriever.getStudents();

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] { "ID", "Name", "Phone Number", "Email Address", "Course Enrolled" });
		for (Student student : studentData) {
			model.addRow(
					new Object[] { student.getId(), student.getName(), student.getPhone(), student.getEmail(),
							student.getEnrolledCourse() });
		}
		JTable table_1 = new JTable(model);
		table_1.setEnabled(false);
		scrollPane.setViewportView(table_1);
	}

	// Singleton to ensure one and only instance
	public static Students getInstance(JPanel main, SystemUser user) {
		if (instance == null) {
			instance = new Students(main, user);
		}
		return instance;
	}

	public static void dispose() {
		instance = null;
	}
}
