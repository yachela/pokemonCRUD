package ar.edu.davinci.UI;

import ar.edu.davinci.DAO.BattleManager;
import ar.edu.davinci.DAO.TrainerDAOImplH2;
import ar.edu.davinci.DAO.UserDAOImplH2;
import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainMenuScreen {
    private JPanel mainPanel;
    private JLabel welcomeLabel;
    private JButton battleButton;
    private JButton logoutButton;
    private JButton createTrainerButton;
    private JButton viewTrainersButton;
    private JButton createPokemonButton;

    public MainMenuScreen(JFrame frame, UserDAOImplH2 userDAO, BattleManager battleManager) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        welcomeLabel = new JLabel("¡Bienvenido al Sistema de Batallas Pokémon!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        battleButton = new JButton("Iniciar Batalla");
        createTrainerButton = new JButton("Crear Entrenador");
        viewTrainersButton = new JButton("Ver Entrenadores");
        createPokemonButton = new JButton("Agregar Pokémon");
        logoutButton = new JButton("Cerrar Sesión");

        buttonPanel.add(battleButton);
        buttonPanel.add(createTrainerButton);
        buttonPanel.add(viewTrainersButton);
        buttonPanel.add(createPokemonButton);
        buttonPanel.add(logoutButton);

        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);


        battleButton.addActionListener(e -> {
            try {
                User currentUser = LoginScreen.getCurrentUser();
                User opponent = battleManager.findRandomOpponent();

                // Obtener entrenadores del usuario y del oponente
                TrainerDAOImplH2 trainerDAO = new TrainerDAOImplH2();
                List<Trainer> userTrainers = trainerDAO.getAllTrainersByUser(currentUser.getId());
                List<Trainer> opponentTrainers = trainerDAO.getAllTrainersByUser(opponent.getId());

                if (userTrainers.isEmpty() || opponentTrainers.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Uno de los usuarios no tiene entrenadores registrados.");
                    return;
                }


                Trainer userTrainer = selectTrainer(userTrainers);
                Trainer opponentTrainer = selectTrainer(opponentTrainers);

                if (userTrainer.getPokemonList().isEmpty() || opponentTrainer.getPokemonList().isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Uno de los entrenadores no tiene Pokémon registrados.");
                    return;
                }

                Pokemon userPokemon = selectPokemon(userTrainer);
                Pokemon opponentPokemon = selectPokemon(opponentTrainer);


                Pokemon winner = battleManager.startBattle(userPokemon, opponentPokemon);

                JOptionPane.showMessageDialog(mainPanel, "¡El ganador es " + winner.getSpecie() + "!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error al iniciar la batalla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        createTrainerButton.addActionListener(e -> {
            frame.setContentPane(new CreateTrainerScreen(frame, userDAO, battleManager).getMainPanel());
            frame.revalidate();
        });


        viewTrainersButton.addActionListener(e -> {
            User currentUser = LoginScreen.getCurrentUser();
            TrainerDAOImplH2 trainerDAO = new TrainerDAOImplH2();
            List<Trainer> trainers = trainerDAO.getAllTrainersByUser(currentUser.getId());
            if (trainers.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "No tenes entrenadores registrados.");
            } else {
                showTrainersTable(trainers);
            }
        });

        createPokemonButton.addActionListener(e -> {
            frame.setContentPane(new CreatePokemonScreen(frame, userDAO, new TrainerDAOImplH2(), battleManager).getMainPanel());
            frame.revalidate();
        });


        logoutButton.addActionListener(e -> {
            frame.setContentPane(new LoginScreen(frame, userDAO, battleManager).getMainPanel());
            frame.revalidate();
        });
    }

    private void showTrainersTable(List<Trainer> trainers) {
        String[] columnNames = {"ID", "Nombre", "Fecha de Nacimiento", "Nacionalidad"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Trainer trainer : trainers) {
            Object[] rowData = {
                    trainer.getId(),
                    trainer.getName(),
                    trainer.getBirthDate(),
                    trainer.getNationality()
            };
            tableModel.addRow(rowData);
        }

        JTable trainerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(trainerTable);

        JFrame tableFrame = new JFrame("Lista de Entrenadores");
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.add(scrollPane);
        tableFrame.setSize(600, 400);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private Trainer selectTrainer(List<Trainer> trainers) {
        String[] trainerNames = trainers.stream().map(Trainer::getName).toArray(String[]::new);
        String selectedTrainerName = (String) JOptionPane.showInputDialog(
                null, "Selecciona un entrenador", "Seleccionar Entrenador",
                JOptionPane.QUESTION_MESSAGE, null, trainerNames, trainerNames[0]);

        return trainers.stream().filter(t -> t.getName().equals(selectedTrainerName)).findFirst().orElse(null);
    }

    private Pokemon selectPokemon(Trainer trainer) {
        String[] pokemonNames = trainer.getPokemonList().stream().map(Pokemon::getSpecie).toArray(String[]::new);
        String selectedPokemonName = (String) JOptionPane.showInputDialog(
                null, "Selecciona un Pokemon", "Seleccionar Pokemon",
                JOptionPane.QUESTION_MESSAGE, null, pokemonNames, pokemonNames[0]);

        return trainer.getPokemonList().stream().filter(p -> p.getSpecie().equals(selectedPokemonName)).findFirst().orElse(null);
    }
}