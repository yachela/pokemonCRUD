package ar.edu.davinci;

import ar.edu.davinci.DAO.BattleManager;
import ar.edu.davinci.DAO.UserDAOImplH2;
import ar.edu.davinci.UI.LoginScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pokemon Batallas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        UserDAOImplH2 userDAO = new UserDAOImplH2();
        BattleManager battleManager = new BattleManager(userDAO);

        LoginScreen loginScreen = new LoginScreen(frame, userDAO, battleManager);
        frame.setContentPane(createStyledPanel(loginScreen.getMainPanel()));

        frame.setVisible(true);
    }

    private static JPanel createStyledPanel(JPanel mainPanel) {

        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.setBackground(new Color(255, 197, 73));

        return mainPanel;
    }
}