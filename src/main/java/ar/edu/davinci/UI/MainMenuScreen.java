package ar.edu.davinci.UI;

import ar.edu.davinci.DAO.BattleManager;
import ar.edu.davinci.DAO.UserDAOImplH2;
import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainMenuScreen {
    private JPanel mainPanel;
    private JButton jugarPartidaButton;
    private JButton misPokemonsButton;
    private JButton cerrarSesionButton;
    private UserDAOImplH2 userDAO;
    private BattleManager battleManager;

    public MainMenuScreen(JFrame frame, BattleManager battleManager) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        jugarPartidaButton = new JButton("Jugar Partida");
        misPokemonsButton = new JButton("Mis Pokémon");
        cerrarSesionButton = new JButton("Cerrar Sesión");

        mainPanel.add(jugarPartidaButton);
        mainPanel.add(misPokemonsButton);
        mainPanel.add(cerrarSesionButton);

        jugarPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateBattle(battleManager);
            }
        });

        misPokemonsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User currentUser = LoginScreen.getCurrentUser();

                List<Pokemon> misPokemons = currentUser.getTrainers().stream()
                        .flatMap(trainer -> trainer.getPokemonList().stream())
                        .toList();

                if (misPokemons.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Ninguno de tus entrenadores tiene Pokemons");
                } else {
                    misPokemons.forEach(pokemon -> JOptionPane.showMessageDialog(mainPanel, pokemon.getType() + "\n"));
                }
            }
        });

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new LoginScreen().getMainPanel());
                frame.revalidate();
            }
        });
    }

    private void simulateBattle(BattleManager battleManager) {
        User currentUser = LoginScreen.getCurrentUser();
        Trainer currentTrainer = selectTrainer(currentUser);
        if (currentTrainer == null) {
            JOptionPane.showMessageDialog(mainPanel, "No seleccionaste un entrenador");
            return;
        }

        User opponent = battleManager.findRandomOpponent(currentUser);
        Trainer opponentTrainer = selectTrainer(opponent);
        if (opponentTrainer == null) {
            JOptionPane.showMessageDialog(mainPanel, "El oponente no tiene entrenadores disponibles.");
            return;
        }

        String result = currentTrainer.faceTrainer(opponentTrainer);
        JOptionPane.showMessageDialog(mainPanel, result, "Resultado de la Batalla", JOptionPane.INFORMATION_MESSAGE);
    }

    private Trainer selectTrainer(User user) {
        List<Trainer> trainers = user.getTrainers();
        if (trainers.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Este usuario no tiene entrenadores disponibles");
            return null;
        }

        if (user == LoginScreen.getCurrentUser()) {

            String[] trainerNames = trainers.stream()
                    .map(Trainer::getName)
                    .toArray(String[]::new);

            String selectedTrainerName = (String) JOptionPane.showInputDialog(
                    mainPanel,
                    "Selecciona tu entrenador:",
                    "Seleccionar Entrenador",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    trainerNames,
                    trainerNames[0]
            );

            return trainers.stream()
                    .filter(trainer -> trainer.getName().equals(selectedTrainerName))
                    .findFirst()
                    .orElse(null);
        } else {

            int randomIndex = (int) (Math.random() * trainers.size());
            return trainers.get(randomIndex);
        }
    }

    public static void show(JFrame frame) {
        MainMenuScreen mainMenuScreen = new MainMenuScreen(frame, new BattleManager(new UserDAOImplH2()));
        frame.setContentPane(mainMenuScreen.getMainPanel());
        frame.revalidate();
        frame.repaint();
    }

    public Container getMainPanel() {
        return mainPanel;
    }
}