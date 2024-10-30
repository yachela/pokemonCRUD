package ar.edu.davinci;

public abstract class Pokemon{
    private String nombre;
    private Tipo tipo;
    private Float energia;
    private int poder;
    private Especie especie;

    public Pokemon(String nombre, Tipo tipo, Float energia, int poder){
        this.tipo = tipo;
        this.energia = energia;
        this.poder = poder;
        this.nombre = nombre + especie.generacion;
    }
    public Tipo getTipo() {
        return tipo;
    }
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Float getEnergia() {
        return energia;
    }
    public void setEnergia(Float energia) {
        this.energia = energia;
    }
    public int getPoder() {
        return poder;
    }
    public void setPoder(int poder) {
        this.poder = poder;
    }

    public void atacar(Pokemon otroPokemon) {
        System.out.println(tipo + " ataca a " + otroPokemon.getTipo() );
    }


    public void restarVida(Float cant) {

    }

    public Float aumentarVida(Pokemon otroPokemon) {
    return (float) 0;
    }
}
