package models;


public class Usuarios {
    protected int id;
    protected String nome;
    protected String senha;
    private String cpf;

    public Usuarios(String nome,String cpf, String senha) {
        this.id = 0;
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
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
     
    public String getCPF(){
        return cpf;
    }
    
    public void cadastrar(){
        
        
    }
    
}
