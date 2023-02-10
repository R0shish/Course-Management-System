package pages;

import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import models.course.Module;
import models.user.Admin;
import models.user.SystemUser;
import models.user.Teacher;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import util.DataManager;
import util.DataRetriever;

public class Teachers extends JPanel {

	private static final long serialVersionUID = 7035062181966295884L;
	private static Teachers instance;
	JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public Teachers(JPanel main, SystemUser user) {
		main.add(this);
		this.setBounds(113, 0, 1341, 701);
		setLayout(null);

		JPanel students = new JPanel();
		students.setLayout(null);
		students.setBounds(113, 0, 1222, 701);
		add(students);

		String e = user instanceof Admin ? "Manage" : "View";
		JLabel title = new JLabel(e + " Teachers");
		title.setFont(new Font("Futura", Font.PLAIN, 25));
		title.setBounds(5, 75, 236, 36);
		students.add(title);

		JLabel addBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/resources/add.png")));
		addBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(4, 2));
				JLabel nameLabel = new JLabel("Enter teacher name: ");
				JTextField nameField = new JTextField();
				JLabel emailLabel = new JLabel("Enter teacher email: ");
				JTextField emailField = new JTextField();
				JLabel passwordLabel = new JLabel("Enter teacher password: ");
				JPasswordField passwordField = new JPasswordField();
				JLabel numberLabel = new JLabel("Enter teacher phone number: ");
				JTextField numberField = new JTextField();
				panel.add(emailLabel);
				panel.add(emailField);

				panel.add(nameLabel);
				panel.add(nameField);

				panel.add(passwordLabel);
				panel.add(passwordField);

				panel.add(numberLabel);
				panel.add(numberField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Add Teacher", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String email = emailField.getText().trim();
					String name = nameField.getText().trim();
					String password = new String(passwordField.getPassword());
					String number = numberField.getText().trim();
					try {
						DataManager.addTeacher(name, number, email, password);
						JOptionPane.showMessageDialog(null, "Teacher added successfully");
						refreshTable(students);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error adding teacher", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		addBtnLbl.setBounds(854, 47, 45, 45);

		JLabel editBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/resources/edit.png")));
		editBtnLbl.setBounds(946, 43, 45, 45);
		editBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(5, 2));
				JLabel idLabel = new JLabel("Enter teacher id: ");
				JTextField idField = new JTextField();
				JLabel nameLabel = new JLabel("Enter teacher name: ");
				JTextField nameField = new JTextField();
				JLabel emailLabel = new JLabel("Enter teacher email: ");
				JTextField emailField = new JTextField();
				JLabel passwordLabel = new JLabel("Enter teacher password: ");
				JPasswordField passwordField = new JPasswordField();
				JLabel numberLabel = new JLabel("Enter teacher phone number: ");
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

				int result = JOptionPane.showConfirmDialog(null, panel, "Update Teacher", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String id = idField.getText().trim();
					String email = emailField.getText().trim();
					String name = nameField.getText().trim();
					String password = new String(passwordField.getPassword());
					String number = numberField.getText().trim();
					try {
						DataManager.editTeacher(id, name, number, email, password);
						JOptionPane.showMessageDialog(null, "Teacher updated successfully");
						refreshTable(students);

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error updating teacher", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		JLabel removeBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/resources/remove.png")));
		removeBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(1, 2));
				JLabel idLabel = new JLabel("Enter teacher id: ");
				JTextField idField = new JTextField();

				panel.add(idLabel);
				panel.add(idField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Delete Teacher", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String teacherId = idField.getText();

					try {
						DataManager.deleteTeacher(teacherId);
						JOptionPane.showMessageDialog(null, "Teacher deleted successfully");
						refreshTable(students);

					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(null, "Error deleting teacher", e1.getMessage(),
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

		JLabel assignBtnLbl = new JLabel(new ImageIcon(Teachers.class.getResource("/resources/assign.png")));
		assignBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JLabel idLabel = new JLabel("Enter teacher id: ");
				JTextField idField = new JTextField();
				JLabel moduleIdLabel = new JLabel("Enter module id: ");
				JTextField moduleIdField = new JTextField();

				JPanel panel = new JPanel(new BorderLayout());
				JPanel inputPanel = new JPanel(new GridLayout(3, 2));

				ButtonGroup group = new ButtonGroup();

				JRadioButton assignRadioButton = new JRadioButton("Assign");
				JRadioButton unassignRadioButton = new JRadioButton("Unassign");

				group.add(assignRadioButton);
				group.add(unassignRadioButton);

				inputPanel.add(assignRadioButton);
				inputPanel.add(unassignRadioButton);

				inputPanel.add(idLabel);
				inputPanel.add(idField);
				inputPanel.add(moduleIdLabel);
				inputPanel.add(moduleIdField);

				String[] columnNames = { "Module ID", "Module Name" };

				// Get the data for the table by calling a method or using a database
				ArrayList<Module> modules = DataRetriever.getAllModules();

				int rowCount = modules.size();
				int columnCount = 2;

				Object[][] data = new Object[rowCount][columnCount];

				for (int i = 0; i < rowCount; i++) {
					data[i][0] = modules.get(i).getId();
					data[i][1] = modules.get(i).getName();
				}

				JTable modulesTable = new JTable(data, columnNames);
				JScrollPane scrollPane = new JScrollPane(modulesTable);

				panel.add(inputPanel, BorderLayout.NORTH);
				panel.add(scrollPane, BorderLayout.CENTER);

				int result = JOptionPane.showConfirmDialog(null, panel, "Assign Teacher", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					if (assignRadioButton.isSelected()) {
						// Assign button is selected
						String teacherId = idField.getText().trim();
						String moduleId = moduleIdField.getText().trim();
						try {
							DataManager.assignModulesToTeacher(teacherId, moduleId);
							JOptionPane.showMessageDialog(null, "Teacher assigned successfully");
							refreshTable(students);
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, "Error assigning teacher", e1.getMessage(),
									JOptionPane.ERROR_MESSAGE);
						}
					} else if (unassignRadioButton.isSelected()) {
						// Unassign button is selected
						String teacherId = idField.getText().trim();
						String moduleId = moduleIdField.getText().trim();
						try {
							DataManager.unassignModulesFromTeacher(teacherId, moduleId);
							JOptionPane.showMessageDialog(null, "Teacher unassigned successfully");
							refreshTable(students);
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, "Error unassigning teacher", e1.getMessage(),
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		assignBtnLbl.setBounds(771, 53, 45, 45);

		JLabel assignLbl = new JLabel("Assign");
		assignLbl.setHorizontalAlignment(SwingConstants.CENTER);
		assignLbl.setBounds(765, 103, 61, 16);

		if (user instanceof Admin) {
			students.add(addBtnLbl);
			students.add(editBtnLbl);
			students.add(removeBtnLbl);
			students.add(addLbl);
			students.add(editLbl);
			students.add(removeLbl);
			students.add(assignLbl);
			students.add(assignBtnLbl);
		}

		createTable(students);

	}

	private void createTable(JPanel students) {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 185, 1138, 454);
		students.add(scrollPane);

		ArrayList<Teacher> teachersData = DataRetriever.getTeachers();

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] { "ID", "Name", "Phone Number", "Email Address", "Modules Taught" });
		for (Teacher teacherData : teachersData) {
			model.addRow(new Object[] { teacherData.getId(), teacherData.getName(), teacherData.getPhone(),
					teacherData.getEmail(),
					teacherData.getModuleString() });
		}
		JTable table_1 = new JTable(model);
		table_1.setEnabled(false);
		scrollPane.setViewportView(table_1);

	}

	private void refreshTable(JPanel students) {
		students.remove(scrollPane);
		createTable(students);
		students.revalidate();
		students.repaint();
	}

	// Singleton to ensure one and only instance
	public static Teachers getInstance(JPanel main, SystemUser user) {
		if (instance == null) {
			instance = new Teachers(main, user);
		}
		return instance;
	}

	public static void dispose() {
		instance = null;
	}
}
