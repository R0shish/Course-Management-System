package pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import auth.Auth;
import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
import models.Admin;
import models.Student;
import models.SystemUser;
import models.Teacher;
import util.CustomImage;
import util.Validator;

public class Login extends JPanel {

	private static final long serialVersionUID = -2934127260269983979L;

	private JTextField emailTxt;
	private JPasswordField passwordTxt;

	public Login(JFrame frame, CustomImage logo) {

		ImageIcon loginImg = new ImageIcon(getClass().getResource("../resources/login_image.png"));
		Image loginImage = loginImg.getImage().getScaledInstance(1100, 708, java.awt.Image.SCALE_SMOOTH);
		loginImg = new ImageIcon(loginImage);

		JPanel login = new JPanel();
		login.setBackground(new Color(255, 255, 255));
		login.setBounds(0, -7, 1480, 701);
		frame.getContentPane().add(login);
		login.setVisible(true);
		login.setLayout(null);

		JLabel logoImg = new JLabel(logo.getImage(100, 100));
		logoImg.setBounds(52, 43, 100, 100);
		login.add(logoImg);

		JLabel loginImgLabel = new JLabel(loginImg);
		loginImgLabel.setBounds(437, 0, 1043, 708);
		login.add(loginImgLabel);

		JLabel title = new JLabel("Welcome");
		title.setFont(new Font("Futura", Font.PLAIN, 40));
		title.setBounds(52, 155, 352, 54);
		login.add(title);

		JLabel subtitle = new JLabel("Please login to get started");
		subtitle.setFont(new Font("Futura", Font.PLAIN, 20));
		subtitle.setBounds(52, 205, 302, 27);
		login.add(subtitle);

		emailTxt = new JTextField();
		emailTxt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				passwordTxt.requestFocusInWindow();
			}
		});
		emailTxt.setFont(new Font("Futura", Font.PLAIN, 15));
		emailTxt.setColumns(10);
		emailTxt.setBounds(52, 315, 332, 43);
		login.add(emailTxt);

		JLabel emailLbl = new JLabel("Email");
		emailLbl.setFont(new Font("Futura", Font.PLAIN, 15));
		emailLbl.setBounds(55, 293, 42, 16);
		login.add(emailLbl);

		JLabel passwordLbl = new JLabel("Password");
		passwordLbl.setFont(new Font("Futura", Font.PLAIN, 15));
		passwordLbl.setBounds(55, 384, 80, 16);
		login.add(passwordLbl);

		JLabel bottomLbl = new JLabel("Don't have an account ?");
		bottomLbl.setEnabled(false);
		bottomLbl.setFont(new Font("Kohinoor Bangla", Font.PLAIN, 12));
		bottomLbl.setBounds(109, 544, 152, 27);
		login.add(bottomLbl);

		JLabel loginBtn = new JLabel("Sign Up");
		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SignUp(frame, logo);
				login.setVisible(false);
			}
		});
		loginBtn.setFont(new Font("Kohinoor Bangla", Font.BOLD, 12));
		loginBtn.setBounds(245, 544, 43, 27);
		login.add(loginBtn);

		JLabel errorEmailLbl = new JLabel("");
		errorEmailLbl.setForeground(new Color(255, 0, 7));
		errorEmailLbl.setFont(new Font("Kohinoor Bangla", Font.PLAIN, 12));
		errorEmailLbl.setBounds(55, 356, 322, 27);
		login.add(errorEmailLbl);

		JLabel errorPasswordLbl = new JLabel("");
		errorPasswordLbl.setForeground(new Color(255, 0, 7));
		errorPasswordLbl.setFont(new Font("Kohinoor Bangla", Font.PLAIN, 12));
		errorPasswordLbl.setBounds(54, 442, 322, 27);
		login.add(errorPasswordLbl);

		ActionListener loginAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorEmailLbl.setText("");
				errorPasswordLbl.setText("");

				String email = emailTxt.getText().strip().toLowerCase();
				String password = new String(passwordTxt.getPassword()).strip();

				if (!Validator.validate(email, errorEmailLbl, "Email")) {
					return;
				}

				try {
					SystemUser user = Auth.returnSystemUser(email, password);

					emailTxt.setText("");
					passwordTxt.setText("");

					if (user instanceof Student) {
						new Dashboard(frame, (Student) user, login);
					} else if (user instanceof Teacher) {
						new Dashboard(frame, (Teacher) user, login);
					} else if (user instanceof Admin) {
						new Dashboard(frame, (Admin) user, login);
					}
					login.setVisible(false);

				} catch (InvalidEmailException errEmail) {
					errorEmailLbl.setText(errEmail.getMessage());
				} catch (InvalidPasswordException errPassword) {
					errorPasswordLbl.setText(errPassword.getMessage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		};

		passwordTxt = new JPasswordField();
		passwordTxt.setColumns(10);
		passwordTxt.setFont(new Font("Futura", Font.PLAIN, 15));
		passwordTxt.setBounds(52, 401, 332, 43);
		passwordTxt.addActionListener(loginAction);
		login.add(passwordTxt);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(loginAction);
		btnLogin.setFont(new Font("Futura", Font.PLAIN, 15));
		btnLogin.setForeground(new Color(242, 252, 255));
		btnLogin.setOpaque(true);
		btnLogin.setBorderPainted(false);
		btnLogin.setBackground(new Color(0, 0, 0));
		btnLogin.setBounds(49, 489, 332, 43);
		login.add(btnLogin);
	}

}
