package ar.edu.davinci;

import ar.edu.davinci.Model.Trainer;
import ar.edu.davinci.Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class TestUser {

    @Test
    void testCreateTrainerSuccessfully() {
        User user = new User("Lali", "123456789");
        Trainer trainer = user.createTrainer("Kael", "Kalos", LocalDate.of(1992, 8, 14));

        Assertions.assertEquals(1, user.getTrainers().size());
        Assertions.assertEquals("Kael", trainer.getName());
    }

    @Test
    void testCreateTrainerExceedsLimit() {
        User user = new User("Lali", "113456789");
        user.createTrainer("Trainer 1", "Region 1", LocalDate.of(2000, 1, 1));
        user.createTrainer("Trainer 2", "Region 2", LocalDate.of(2001, 2, 2));
        user.createTrainer("Trainer 3", "Region 3", LocalDate.of(2002, 3, 3));

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            user.createTrainer("Trainer 4", "Region 4", LocalDate.of(2003, 4, 4));
        });

        Assertions.assertEquals("No podes tener mas de 3 entrenadores.", exception.getMessage());
    }

    @Test
    void testCreateTrainerWithDuplicateName() {
        User user = new User("Lali", "123456789");
        user.createTrainer("Trainer 1", "Region 1", LocalDate.of(2000, 1, 1));

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            user.createTrainer("Trainer 1", "Region 2", LocalDate.of(2001, 2, 2));
        });

        Assertions.assertEquals("Ya existe un entrenador con ese nombre.", exception.getMessage());
    }

    @Test
    void testCreateTrainerWithInvalidData() {
        User user = new User("Lali", "123456789");

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            user.createTrainer("", "Region 1", LocalDate.of(2000, 1, 1));
        });

        Assertions.assertEquals("El nombre del entrenador no puede estar vacío", exception.getMessage());
    }
}