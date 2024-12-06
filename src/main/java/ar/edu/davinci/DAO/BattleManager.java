package ar.edu.davinci.DAO;

import ar.edu.davinci.Model.User;

import java.util.List;

public class BattleManager {
    private final UserDAO userDAO;

    public BattleManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public User findRandomOpponent(User currentUser) {
        List<User> users = userDAO.getAllUsers();
        List<User> candidates = users.stream()
                .filter(user -> user.getId() != currentUser.getId())
                .toList();

        if (candidates.isEmpty()) {
            throw new IllegalStateException("No hay usuarios disponibles para enfrentarse.");
        }
        int opponentIndex = selectRandomOpponentIndex(candidates.size());
        return candidates.get(opponentIndex);
    }

    private int selectRandomOpponentIndex(int listSize) {
        return (int) (Math.random() * listSize);
    }
}