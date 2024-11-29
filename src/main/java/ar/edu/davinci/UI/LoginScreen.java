package ar.edu.davinci.UI;

import ar.edu.davinci.DAO.UserDAOImplH2;
import ar.edu.davinci.Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen {
    private JPanel mainPanel;
    private JTextField userNameTextField;
    private JPasswordField passwordTextField;
    private JButton iniciarSesionButton;
    private UserDAOImplH2 userDAO = new UserDAOImplH2();

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public LoginScreen() {
        iniciarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String userName = userNameTextField.getText();
        String userPassword = new String(passwordTextField.getPassword());

        User user = userDAO.getUserByUsername(userName);

        if (user != null && userPassword.equals(user.getPassword())) {
            JOptionPane.showMessageDialog(mainPanel, "Iniciaste sesion");
            LoginScreen.setCurrentUser(user);
            MainMenuScreen.show((JFrame) SwingUtilities.getWindowAncestor(mainPanel));
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Usuario o contraseña incorrectos");
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Juego Pokemon");
        frame.setContentPane(new LoginScreen().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}