package ar.edu.davinci;


    public interface PokemonDAO {
        void crear(Pokemon pokemon);
        Pokemon obtener(String especie);
        List<Pokemon> obtenerTodos();
        void actualizar(Pokemon pokemon);
        void eliminar(Pokemon pokemon);
    }

