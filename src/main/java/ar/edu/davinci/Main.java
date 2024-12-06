package ar.edu.davinci.UI;

import ar.edu.davinci.DAO.BattleManager;
import ar.edu.davinci.DAO.UserDAOImplH2;
import ar.edu.davinci.Model.User;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Pokemon Trainer Battle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        UserDAOImplH2 userDAO = new UserDAOImplH2();
        BattleManager battleManager = new BattleManager(userDAO);

        User currentUser = userDAO.getUserByUsername("Ash");

        if (currentUser == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado. Por favor, inicia sesión.");
            frame.setContentPane(new LoginScreen().getMainPanel());
        } else {

            LoginScreen.setCurrentUser(currentUser);


            MainMenuScreen mainMenu = new MainMenuScreen(frame, battleManager);
            frame.setContentPane(mainMenu.getMainPanel());
        }

        frame.setVisible(true);
    }
}