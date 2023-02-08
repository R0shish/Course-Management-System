package pages.admin;

import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import models.user.Teacher;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import util.DataManager;
import util.DataRetriever;

public class Teachers extends JPanel {

	private static final long serialVersionUID = 7035062181966295884L;
	private static Teachers instance;

	/**
	 * Create the panel.
	 */
	public Teachers(JPanel main) {
		main.add(this);
		this.setBounds(113, 0, 1341, 701);
		setLayout(null);

		JPanel students = new JPanel();
		students.setLayout(null);
		students.setBounds(113, 0, 1222, 701);
		add(students);

		JLabel title = new JLabel("Manage Teachers");
		title.setFont(new Font("Futura", Font.PLAIN, 25));
		title.setBounds(5, 75, 236, 36);
		students.add(title);

		JLabel addBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/resources/add.png")));
		addBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(2, 2));
				JLabel nameLabel = new JLabel("Enter course name: ");
				JTextField nameField = new JTextField();
				JLabel codeLabel = new JLabel("Enter course code: ");
				JTextField codeField = new JTextField();
				panel.add(codeLabel);
				panel.add(codeField);
				panel.add(nameLabel);
				panel.add(nameField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Add Course", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String courseName = nameField.getText();
					String courseId = codeField.getText();
					String[] column = { "course_id", "course_name" };
					String[] values = { courseId, courseName };
					try {
						DataManager.insert("courses", column, values);
						JOptionPane.showMessageDialog(null, "Course added successfully");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error adding course", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		addBtnLbl.setBounds(854, 47, 45, 45);
		students.add(addBtnLbl);

		JLabel editBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/resources/edit.png")));
		editBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(2, 2));
				JLabel nameLabel = new JLabel("Enter course name: ");
				JTextField nameField = new JTextField();
				JLabel codeLabel = new JLabel("Enter course code: ");
				JTextField codeField = new JTextField();
				panel.add(codeLabel);
				panel.add(codeField);
				panel.add(nameLabel);
				panel.add(nameField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Edit Course", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String courseName = nameField.getText();
					String courseId = codeField.getText();
					try {
						DataManager.editCourse(courseId, courseName);
						JOptionPane.showMessageDialog(null, "Course updated successfully");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error updating course", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		editBtnLbl.setBounds(946, 43, 45, 45);
		students.add(editBtnLbl);

		JLabel removeBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/resources/remove.png")));
		removeBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(2, 2));
				JLabel idLabel = new JLabel("Enter course id: ");
				JTextField idField = new JTextField();

				panel.add(idLabel);
				panel.add(idField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Delete Course", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String courseId = idField.getText();

					try {
						DataManager.deleteCourse(courseId);
						JOptionPane.showMessageDialog(null, "Course deleted successfully");
					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(null, "Error deleting course", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		removeBtnLbl.setBounds(1036, 47, 45, 45);
		students.add(removeBtnLbl);

		JLabel addLbl = new JLabel("Add");
		addLbl.setHorizontalAlignment(SwingConstants.CENTER);
		addLbl.setBounds(846, 103, 61, 16);
		students.add(addLbl);

		JLabel editLbl = new JLabel("Edit");
		editLbl.setHorizontalAlignment(SwingConstants.CENTER);
		editLbl.setBounds(931, 104, 61, 16);
		students.add(editLbl);

		JLabel removeLbl = new JLabel("Remove");
		removeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		removeLbl.setBounds(1029, 103, 61, 16);
		students.add(removeLbl);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 185, 1138, 454);
		students.add(scrollPane);

		ArrayList<Teacher> teachersData = DataRetriever.getTeachers();

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] { "ID", "Name", "Phone Number", "Email Address", "Module" });
		for (Teacher teacherData : teachersData) {
			model.addRow(new Object[] { teacherData.getId(), teacherData.getName(), teacherData.getPhone(), "",
					teacherData.getModules() });
		}
		JTable table_1 = new JTable(model);
		scrollPane.setViewportView(table_1);

	}

	// Singleton to ensure one and only instance
	public static Teachers getInstance(JPanel main) {
		if (instance == null) {
			instance = new Teachers(main);
		}
		return instance;
	}
}
