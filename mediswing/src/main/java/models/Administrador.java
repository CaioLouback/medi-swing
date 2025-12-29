package models;


public class Administrador extends Usuarios {

    private int nivelAcesso;

    public Administrador(String nome, String senha, int nivelAcesso) {
        super(nome, senha);
        this.nivelAcesso = nivelAcesso;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
