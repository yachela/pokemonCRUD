package ar.edu.davinci;

public abstract class Pokemon{
    private String nombre;
    private ITipoPokemon tipo;
    private Float energia;
    private int poder;
    private Especie especie;

    public Pokemon(String nombre, ITipoPokemon tipo, Float energia, int poder){
        this.tipo = tipo;
        this.energia = energia;
        this.poder = poder;
        this.nombre = nombre + especie.generacion;
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
        otroPokemon.restarVida(this.poder);
    }

    public void restarVida(float cantidad) {
        this.energia -= cantidad;
    }

    public void aumentarVida(float cantidad) {
        this.energia += cantidad;
    }
}
