package ar.edu.davinci.UI;

import ar.edu.davinci.DAO.BattleManager;
import ar.edu.davinci.DAO.UserDAOImplH2;
import ar.edu.davinci.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class LoginScreen {
    private JPanel mainPanel;
    private JTextField userNameTextField;
    private JPasswordField passwordTextField;
    private JButton iniciarSesionButton;

    private UserDAOImplH2 userDAO;
    private BattleManager battleManager;
    private JFrame frame;

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public LoginScreen(JFrame frame, UserDAOImplH2 userDAO, BattleManager battleManager) {
        this.frame = frame;
        this.userDAO = userDAO;
        this.battleManager = battleManager;

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 215, 73)); // Color de fondo

        try {
            ImageIcon bannerImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("pokemon.png")));
            if (bannerImage.getIconWidth() == -1 || bannerImage.getIconHeight() == -1) {
                throw new Exception("Imagen no válida o no encontrada en la ruta especificada.");
            }
            JLabel bannerLabel = new JLabel(bannerImage);
            bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(bannerLabel, BorderLayout.NORTH);
        } catch (Exception ex) {

            System.err.println("Error al cargar la imagen: " + ex.getMessage());
            JLabel errorLabel = new JLabel("Banner no disponible");
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            errorLabel.setForeground(Color.RED);
            mainPanel.add(errorLabel, BorderLayout.NORTH);
        }

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false); // Hacerlo transparente para que muestre el fondo
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        userNameTextField = new JTextField(15);
        passwordTextField = new JPasswordField(15);
        iniciarSesionButton = new JButton("Iniciar sesión");

        iniciarSesionButton.addActionListener(this::handleLogin);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(new JLabel("Nombre"), constraints);

        constraints.gridx = 1;
        formPanel.add(userNameTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(new JLabel("Contraseña"), constraints);

        constraints.gridx = 1;
        formPanel.add(passwordTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        formPanel.add(iniciarSesionButton, constraints);

        mainPanel.add(formPanel, BorderLayout.CENTER);
    }

    private void handleLogin(ActionEvent e) {
        String userName = userNameTextField.getText();
        String userPassword = new String(passwordTextField.getPassword());
        User user = userDAO.getUserByUsername(userName);

        if (user != null && userPassword.equals(user.getPassword())) {
            JOptionPane.showMessageDialog(mainPanel, "Inicio de sesión exitoso.");
            LoginScreen.setCurrentUser(user);
            MainMenuScreen mainMenuScreen = new MainMenuScreen(frame, userDAO, battleManager);
            frame.setContentPane(mainMenuScreen.getMainPanel());
            frame.revalidate();
        } else {
            JOptionPane.showMessageDialog(mainPanel, "Usuario o contraseña incorrectos.");
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}