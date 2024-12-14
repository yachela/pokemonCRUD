package ar.edu.davinci.UI;

import ar.edu.davinci.DAO.*;
import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.Model.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CreateTrainerScreen {
    private JPanel mainPanel;
    private JTextField nameField;
    private JTextField birthDateField;
    private JTextField nationalityField;
    private JButton createButton;

    private UserDAOImplH2 userDAO;
    private BattleManager battleManager;

    public CreateTrainerScreen(JFrame frame, UserDAOImplH2 userDAO, BattleManager battleManager) {
        this.userDAO = userDAO;
        this.battleManager = battleManager;

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2));

        mainPanel.add(new JLabel("Nombre:"));
        nameField = new JTextField();
        mainPanel.add(nameField);

        mainPanel.add(new JLabel("Fecha de Nacimiento (YYYY-MM-DD):"));
        birthDateField = new JTextField();
        mainPanel.add(birthDateField);

        mainPanel.add(new JLabel("Nacionalidad:"));
        nationalityField = new JTextField();
        mainPanel.add(nationalityField);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> {
            frame.setContentPane(new MainMenuScreen(frame, userDAO, battleManager).getMainPanel());
            frame.revalidate();
        });
        mainPanel.add(backButton);

        createButton = new JButton("Crear Entrenador");
        mainPanel.add(createButton);



        createButton.addActionListener(e -> {
            String name = nameField.getText();
            String birthDateText = birthDateField.getText();
            String nationality = nationalityField.getText();

            try {
                LocalDate birthDate = LocalDate.parse(birthDateText);

                User user = LoginScreen.getCurrentUser();
                if (user == null) {
                    throw new IllegalArgumentException("No se encontró un usuario asociado.");
                }

                Trainer trainer = new Trainer(name, birthDate, nationality);
                trainer.setUser(user);
                TrainerDAO trainerDAO = new TrainerDAOImplH2();
                trainerDAO.insertTrainer(trainer);
                JOptionPane.showMessageDialog(mainPanel, "Entrenador creado con éxito.");

                MainMenuScreen mainMenuScreen = new MainMenuScreen(frame, userDAO, battleManager);
                frame.setContentPane(mainMenuScreen.getMainPanel());
                frame.revalidate();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Formato de fecha inválido. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}