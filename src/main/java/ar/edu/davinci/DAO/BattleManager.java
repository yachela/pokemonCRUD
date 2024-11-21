package ar.edu.davinci.DAO;
import ar.edu.davinci.Model.User;
import java.util.List;

    public class BattleManager {

        public User findRandomOpponent(User currentUser, List<User> users) {
            List<User> candidates = users.stream()
                    .filter(user -> user.getId() != currentUser.getId())
                    .toList();

            int opponentIndex = selectRandomOpponentIndex(candidates.size());
            return candidates.get(opponentIndex);
        }

        private int selectRandomOpponentIndex(int listSize) {
            return (int) (Math.random() * listSize);
        }
    }
