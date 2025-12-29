package models;


public class Paciente extends Usuarios {
    private String cpf;
    private String dataNascimento;
    private String endereco;
    private String telefone;

    public Paciente(String nome, String senha,String cpf, String dataNascimento,String endereco, String telefone) {
        super(nome, senha);
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
