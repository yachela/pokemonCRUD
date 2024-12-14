package ar.edu.davinci.DAO;

import ar.edu.davinci.Model.Trainer;

import java.util.List;

public interface TrainerDAO {
    void insertTrainer(Trainer trainer);

    List<Trainer> getAllTrainers();

    Trainer getTrainerById(int trainerId);

    void updateTrainer(Trainer trainer);

    void deleteTrainer(int trainerId);

    List<Trainer> getAllTrainersByUser(int userId);

}