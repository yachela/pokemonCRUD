package ar.edu.davinci.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User  {

    private int id;
    private String name;
    private String phone;
    private String password;
    private List<Trainer> trainerList;

    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.trainerList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Trainer> getTrainerList() {
        return trainerList;
    }

    public Trainer createTrainer(String aName, String aNationality, LocalDate aBirthDate) {
        if (trainerList.size() >= 3) {
            throw new IllegalStateException("No podes tener mas de 3 entrenadores.");
        }

        if (aName == null || aName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del entrenador no puede estar vacío");
        }

        if (aNationality == null || aNationality.trim().isEmpty()) {
            throw new IllegalArgumentException("La nacionalidad del entrenador no puede estar vacía");
        }

        if (aBirthDate == null || aBirthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula ni estar en el futuro");
        }

        for (Trainer trainer : trainerList) {
            if (trainer.getName().equalsIgnoreCase(aName)) {
                throw new IllegalArgumentException("Ya existe un entrenador con ese nombre.");
            }
        }

        Trainer trainer = new Trainer(aName, aBirthDate, aNationality);
        this.trainerList.add(trainer);
        return trainer;
    }


    public void findBattle(List<User> users) {
        int opponentIndex = selectRandomOpponentIndex(users.size());
        User opponent = users.get(opponentIndex);

    }

    private int selectRandomOpponentIndex(int listSize) {
        return (int) (Math.random() * listSize);
    }


}