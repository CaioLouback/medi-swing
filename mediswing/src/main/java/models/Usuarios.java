package models;


public class Usuarios {
    protected int id;
    protected String nome;
    protected String senha;

    public Usuarios(String nome, String senha) {
        this.id = 0;
        this.nome = nome;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
     
    public void cadastrar(){
        
        
    }
    
}
