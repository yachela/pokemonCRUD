package ar.edu.davinci;

public class PokemonDTO {
    private String nombre;
    private ITipoPokemon tipo;
    private Float energia;
    private int poder;
    private String especie;

    public PokemonDTO(String nombre, ITipoPokemon tipo, Float energia, int poder, String especie) {
        this.tipo = tipo;
        this.energia = energia;
        this.poder = poder;
        this.nombre = nombre;
        this.especie = especie;
    }

    public PokemonDTO() {
    }

    public ITipoPokemon getTipo() {
        return tipo;
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
    public void atacar(PokemonDTO otroPokemon) {
        otroPokemon.restarVida(this.poder);
    }
    public void restarVida(float cantidad) {
        this.energia -= cantidad;
    }
    public void aumentarVida(float cantidad) {
        this.energia += cantidad;
    }
    public String getEspecie() {
        return especie;
    }
    public void setEspecie(String especie) {
        this.especie = especie;
    }
}
