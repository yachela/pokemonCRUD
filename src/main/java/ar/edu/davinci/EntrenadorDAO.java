package ar.edu.davinci;

import java.util.List;

 public interface EntrenadorDAO {
    void crear(Entrenador entrenador);
    Entrenador obtener(String nombre);
    List<Entrenador> obtenerTodos();
    void actualizar(Entrenador entrenador);
    void eliminar(Entrenador entrenador);
}
