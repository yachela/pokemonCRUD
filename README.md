
Hito 1
Se desea modelar dos clases Entrenador y Pokemon.

De un Entrenador sabemos su nombre, fecha de nacimiento, nacionalidad, genero, edad y que Pokemons tiene, máximo 5.

Un Entrenador entiende:
- enfrentarseA(Entrenador otro entrenador);
- capturarPokemon(Pokemon pokemon) (No puede tener más de 5 pokemons)
-  Getters y setters

De un Pokemon sabemos su tipo (Eléctrico, Agua, Fuego, Planta, Piedra), su especie y su energía y su poder (números que van entre 0 y 100 siempre)

Un Pokemon entiende:
- atacar(Pokemon otroPokemon);
- restarVida(Float cant);
- aumentarVida(Float vida);

Además del modelo, se pide implementar el DAO de cada entidad, incluyendo el CRUD de cada una.