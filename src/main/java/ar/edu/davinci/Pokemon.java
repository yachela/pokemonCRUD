package ar.edu.davinci;

public class Pokemon{
    private ITipoPokemon tipo;
    private Float energia;
    private int poder;
    private String especie;

    public Pokemon(ITipoPokemon tipo, Float energia, int poder, String especie) {
        this.tipo = tipo;
        this.energia = energia;
        this.poder = poder;
        this.especie = especie;
    }

    public Pokemon(){
    }

    public ITipoPokemon getTipo(ITipoPokemon tipo) {
        return this.tipo;
    }
    public void setTipo(ITipoPokemon tipo) {
        this.tipo = tipo;
    }

    public Float getEnergia(Float energia) {
        return this.energia;
    }
    public void setEnergia(Float energia) {
        this.energia = energia;
    }
    public int getPoder(int poder) {
        return this.poder;
    }
    public void setPoder(int poder) {
        this.poder = poder;
    }
    public String getEspecie(String especie) {
        return especie;
    }

    public void setEspecie() {
        this.especie = especie;
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
