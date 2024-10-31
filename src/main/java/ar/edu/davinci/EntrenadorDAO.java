package ar.edu.davinci;

import java.util.List;

 public interface EntrenadorDAO {
    void insert(Entrenador entrenador);
    List<Entrenador> getAll();
    void update(Entrenador entrenador);
    void delete(Entrenador entrenador);
}
