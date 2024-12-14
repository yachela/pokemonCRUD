package ar.edu.davinci.UI;

import ar.edu.davinci.DAO.PokemonDAOImplH2;
import ar.edu.davinci.DAO.TrainerDAOImplH2;
import ar.edu.davinci.DAO.UserDAOImplH2;
import ar.edu.davinci.DAO.BattleManager;
import ar.edu.davinci.Interface.IType;
import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.Trainer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CreatePokemonScreen {
    private JPanel mainPanel;
    private JTextField specieField;
    private JTextField powerField;
    private JComboBox<String> typeComboBox;
    private JComboBox<Trainer> trainerComboBox;
    private JButton createPokemonButton;

    public CreatePokemonScreen(JFrame frame, UserDAOImplH2 userDAO, TrainerDAOImplH2 trainerDAO, BattleManager battleManager) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2));

        mainPanel.add(new JLabel("Especie:"));
        specieField = new JTextField();
        mainPanel.add(specieField);

        mainPanel.add(new JLabel("Poder:"));
        powerField = new JTextField();
        mainPanel.add(powerField);

        mainPanel.add(new JLabel("Tipo:"));
        typeComboBox = new JComboBox<>(new String[]{"Electric", "Fire", "Water", "Plant", "Stone"});
        mainPanel.add(typeComboBox);

        mainPanel.add(new JLabel("Entrenador:"));
        trainerComboBox = new JComboBox<>();
        populateTrainerComboBox(trainerDAO);
        mainPanel.add(trainerComboBox);

        createPokemonButton = new JButton("Crear Pokémon");
        mainPanel.add(createPokemonButton);

        createPokemonButton.addActionListener(e -> createPokemon(trainerDAO, frame, userDAO, battleManager));

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(e -> {
            frame.setContentPane(new MainMenuScreen(frame, userDAO, battleManager).getMainPanel());
            frame.revalidate();
        });
        mainPanel.add(backButton);
    }

    private void populateTrainerComboBox(TrainerDAOImplH2 trainerDAO) {
        List<Trainer> trainers = trainerDAO.getAllTrainers();
        for (Trainer trainer : trainers) {
            trainerComboBox.addItem(trainer);
        }
    }

    private void createPokemon(TrainerDAOImplH2 trainerDAO, JFrame frame, UserDAOImplH2 userDAO, BattleManager battleManager) {
        try {
            String specie = specieField.getText();
            float power = Float.parseFloat(powerField.getText());
            String type = (String) typeComboBox.getSelectedItem();
            Trainer trainer = (Trainer) trainerComboBox.getSelectedItem();

            if (trainer == null) {
                throw new IllegalArgumentException("Debe seleccionar un entrenador.");
            }

            IType pokemonType = IType.fromString(type);
            Pokemon pokemon = new Pokemon(pokemonType, specie);
            pokemon.setPower(power);
            pokemon.setTrainer(trainer);

            PokemonDAOImplH2 pokemonDAO = new PokemonDAOImplH2();
            pokemonDAO.insertPokemon(pokemon);

            JOptionPane.showMessageDialog(mainPanel, "Pokémon creado con éxito.");
            frame.setContentPane(new MainMenuScreen(frame, userDAO, battleManager).getMainPanel());
            frame.revalidate();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainPanel, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}