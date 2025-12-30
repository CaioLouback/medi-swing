package models;


public class Administrador extends Usuarios {

    private int nivelAcesso;

    public Administrador(String nome,String cpf, String senha, int nivelAcesso) {
        super(nome,cpf, senha);
        this.nivelAcesso = nivelAcesso;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
