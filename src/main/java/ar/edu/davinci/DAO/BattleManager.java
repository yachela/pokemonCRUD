package ar.edu.davinci.DAO;

import ar.edu.davinci.Model.Pokemon;
import ar.edu.davinci.Model.User;

import java.util.List;

public class BattleManager {
    private final UserDAO userDAO;

    public BattleManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findRandomOpponent() {
        List<User> users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            throw new IllegalStateException("No hay usuarios disponibles para enfrentarse.");
        }
        int opponentIndex = selectRandomOpponentIndex(users.size());
        return users.get(opponentIndex);
    }

    private int selectRandomOpponentIndex(int listSize) {
        return (int) (Math.random() * listSize);
    }

    public Pokemon startBattle(Pokemon pokemon1, Pokemon pokemon2) {
        System.out.println("Comienza la batalla entre " + pokemon1.getSpecie() + " y " + pokemon2.getSpecie() + "");

        while (pokemon1.getEnergy() > 0 && pokemon2.getEnergy() > 0) {
            pokemon1.attack(pokemon2);
            if (pokemon2.isCapturable()) {
                break;
            }
            pokemon2.attack(pokemon1);
        }

        if (pokemon1.getEnergy() > 0) {
            System.out.println(pokemon1.getSpecie() + " ha ganado.");
            return pokemon1;
        } else {
            System.out.println(pokemon2.getSpecie() + " ha ganado.");
            return pokemon2;
        }
    }
}
