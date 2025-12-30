package models;


public class Medico extends Usuarios {
    protected String crm;
    protected String especialidade;
 
    public Medico(String nome,String crm,String especialidade,String cpf,String senha) {
        super(nome,cpf, senha);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getCRM() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }
    
}
