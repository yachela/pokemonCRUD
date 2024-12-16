package ar.edu.davinci;

import ar.edu.davinci.DAO.TrainerDAOImplH2;
import ar.edu.davinci.DAO.UserDAOImplH2;
import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.Model.User;
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
    public void testInsertTrainer() {
        User user = new User();
        user.setId(1);
        user.setName("Casia");
        user.setPassword("pikachu123");

        Trainer trainer = new Trainer("Brock", LocalDate.of(1995, 3, 10), "Pewter City");
        trainer.setUser(user);

        TrainerDAOImplH2 trainerDAO = new TrainerDAOImplH2();
        trainerDAO.insertTrainer(trainer);

        Assertions.assertNotNull(trainer.getId(), "El ID del entrenador no debe ser null después de insertarlo.");
    }

    @Test
    public void testGetAllTrainers() {
        UserDAOImplH2 userDAO = new UserDAOImplH2();
        TrainerDAOImplH2 trainerDAO = new TrainerDAOImplH2();


        User user = new User("testUser", "123456789", "securePassword123");
        userDAO.insertUser(user);


        Trainer trainer = new Trainer("Casia", LocalDate.of(1995, 3, 10), "Pivot");
        trainer.setUser(user);
        trainerDAO.insertTrainer(trainer);

        List<Trainer> trainers = trainerDAO.getAllTrainers();

        Assertions.assertFalse(trainers.isEmpty(), "La lista de entrenadores no debería estar vacía");
        Assertions.assertEquals("lali", trainers.get(0).getName());
        Assertions.assertEquals(user.getId(), trainers.get(0).getUser().getId());
    }

    @Test
    public void testUpdateTrainer() {
        User user = new User();
        user.setId(1);
        user.setName("Casia");
        user.setPassword("pikachu123");

        Trainer trainer = new Trainer("Misty", LocalDate.of(1996, 4, 20), "Cerulean City");
        trainer.setUser(user);

        TrainerDAOImplH2 trainerDAO = new TrainerDAOImplH2();
        trainerDAO.insertTrainer(trainer);

        trainer.setName("Misty actualizada");
        trainerDAO.updateTrainer(trainer);

        Trainer updatedTrainer = trainerDAO.getTrainerById(trainer.getId());
        Assertions.assertEquals("Misty actualizada", updatedTrainer.getName());
    }


    @Test
    void testDeleteTrainer() {
        UserDAOImplH2 userDAO = new UserDAOImplH2();
        TrainerDAOImplH2 trainerDAO = new TrainerDAOImplH2();


        User user = new User();
        user.setName("deleteTestUser");
        user.setPhone("123456789");
        user.setPassword("password123");

        userDAO.insertUser(user);

        Trainer trainer = new Trainer("Casia Orela", LocalDate.of(1990, 3, 15), "Argentina");
        trainer.setUser(user);
        trainerDAO.insertTrainer(trainer);

        List<Trainer> trainersBeforeDelete = trainerDAO.getAllTrainers();
        assertFalse(trainersBeforeDelete.isEmpty(), "Debe haber al menos un entrenador antes de la eliminación.");

        trainerDAO.deleteTrainer(trainer.getId());

        List<Trainer> trainersAfterDelete = trainerDAO.getAllTrainers();
        assertTrue(trainersAfterDelete.isEmpty(), "La lista de entrenadores debería estar vacía después de la eliminación.");
    }
}