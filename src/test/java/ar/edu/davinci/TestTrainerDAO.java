package ar.edu.davinci;

import ar.edu.davinci.DAO.TrainerDAOImplH2;
import ar.edu.davinci.Model.Trainer;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTrainerDAO {

    private TrainerDAOImplH2 trainerDAO;

    @BeforeEach
    void setUp() {
        trainerDAO = new TrainerDAOImplH2();
    }


    @Test
    void testInsertTrainer() {
        Trainer trainer = new Trainer("Casia", LocalDate.of(1990, 3, 15), "Argentina");
        trainerDAO.insertTrainer(trainer);

        assertNotNull(trainer.getId());
    }

    @Test
    void testGetAllTrainers() {
        Trainer trainer = new Trainer("Casia", LocalDate.of(1990, 3, 15), "Argentina");
        trainerDAO.insertTrainer(trainer);

        List<Trainer> trainers = trainerDAO.getAllTrainers();
        assertEquals(1, trainers.size());
        assertEquals("Casia", trainers.get(0).getName());
        assertEquals(33, trainers.get(0).getAge());
    }

    @Test
    void testUpdateTrainer() {
        Trainer trainer = new Trainer("Casia", LocalDate.of(1990, 3, 15), "Argentina");
        trainerDAO.insertTrainer(trainer);

        trainer.setName("Casia Orela");
        trainer.setNationality("España");
        trainerDAO.updateTrainer(trainer);

        Trainer updatedTrainer = trainerDAO.getAllTrainers().get(0);
        assertEquals("Casia Orela", updatedTrainer.getName());
        assertEquals("España", updatedTrainer.getNationality());
    }

    @Test
    void testDeleteTrainer() {
        Trainer trainer = new Trainer("Casia Orela", LocalDate.of(1990, 3, 15), "Argentina");
        trainerDAO.insertTrainer(trainer);

        trainerDAO.deleteTrainer(trainer.getId());

        List<Trainer> trainers = trainerDAO.getAllTrainers();
        assertTrue(trainers.isEmpty());
    }
}