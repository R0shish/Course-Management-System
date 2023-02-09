package pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
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
import models.user.SystemUser;
import util.CustomImage;
import util.Validator;

public class Login extends JPanel {

	private static final long serialVersionUID = -2934127260269983979L;

	private JTextField emailTxt;
	private JPasswordField passwordTxt;

	public Login(JFrame frame, CustomImage logo) {

		ImageIcon loginImg = new ImageIcon(getClass().getResource("/resources/login_image.png"));
		Image loginImage = loginImg.getImage().getScaledInstance(1100, 708, java.awt.Image.SCALE_SMOOTH);
		loginImg = new ImageIcon(loginImage);

		JPanel login = new JPanel();
		login.setBackground(new Color(255, 255, 255));
		login.setBounds(0, -7, 1480, 701);
		frame.getContentPane().add(login);
		login.setVisible(true);
		login.setLayout(null);

		JLabel password_eye = new JLabel(
				new ImageIcon(Login.class.getResource("/resources/password_eye_show.png")));
		password_eye.setBounds(339, 414, 30, 19);
		login.add(password_eye);

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
		emailTxt.setBorder(
				BorderFactory.createCompoundBorder(emailTxt.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
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
		bottomLbl.setBounds(110, 544, 152, 27);
		login.add(bottomLbl);

		JLabel signUpBtn = new JLabel("Sign Up");
		signUpBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SignUp(frame, logo);
				login.setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				signUpBtn.setForeground(new Color(242, 147, 179));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				signUpBtn.setForeground(Color.BLACK);
			}
		});
		signUpBtn.setFont(new Font("Kohinoor Bangla", Font.BOLD, 12));
		signUpBtn.setBounds(245, 544, 43, 27);
		login.add(signUpBtn);

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

					new Dashboard(frame, user, login);

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
		passwordTxt.setBorder(BorderFactory.createCompoundBorder(passwordTxt.getBorder(),
				BorderFactory.createEmptyBorder(0, 5, 0, 38)));
		login.add(passwordTxt);

		password_eye.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (passwordTxt.getEchoChar() == '●') {
					passwordTxt.setEchoChar((char) 0);
					password_eye
							.setIcon(new ImageIcon(Login.class.getResource("/resources/password_eye_hide.png")));
				} else {
					passwordTxt.setEchoChar('●');
					password_eye
							.setIcon(new ImageIcon(Login.class.getResource("/resources/password_eye_show.png")));
				}
			}
		});

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
