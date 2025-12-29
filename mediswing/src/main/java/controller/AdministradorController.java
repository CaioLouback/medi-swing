package controller;

import static dao.MedicoDAO.cadastrarMedicoJson;
import javax.swing.JOptionPane;
import view.CadastroMedico;
import view.CadastroPaciente;
import view.CadastroRecepcionista;


public class AdministradorController {
    
    public static void dialogCadastroMedico(javax.swing.JFrame janela){
        CadastroMedico dialog =
        new CadastroMedico(janela, true); 
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        
    }
    
    public static void dialogCadastroPaciente(javax.swing.JFrame janela){
        CadastroPaciente pac = new CadastroPaciente(janela,true);
        pac.setLocationRelativeTo(null);
        pac.setVisible(true);
        
    }
    
    public static void dialogCadastroRecepcionista(javax.swing.JFrame janela){
        CadastroRecepcionista recep = new CadastroRecepcionista(janela, true);
        recep.setLocationRelativeTo(null);
        recep.setVisible(true);
        
    }
    
    public static void chamaCadastro(javax.swing.JFrame janela, String txt){
        if(txt.equals("Paciente")){
            dialogCadastroPaciente(janela);
        }else if(txt.equals("Recepcionista")){
            dialogCadastroRecepcionista(janela);
        }else{
            dialogCadastroMedico(janela);
        }       
    }
    
    public static void validarCadastroMedico(String nome, String crm, String especialidade, String cpf, String senha, String senha2,javax.swing.JDialog dialog ){
        if(nome.isEmpty() || crm.isEmpty() || especialidade.isEmpty() || cpf.isEmpty() || senha.isEmpty() || senha2.isEmpty()){
            JOptionPane.showMessageDialog(dialog,"Favor preencher os campos corretamente!","Aviso",JOptionPane.WARNING_MESSAGE);
        } else {
            if (temNumero(nome)) {
                JOptionPane.showMessageDialog(dialog,"O nome não pode conter números!","Aviso",JOptionPane.WARNING_MESSAGE);
            } 
            if(temNumero(especialidade)){
                JOptionPane.showMessageDialog(dialog,"A especialidade não pode conter números!","Aviso",JOptionPane.WARNING_MESSAGE);
            } 
            if(senha.equals(senha2)){
                cadastrarMedicoJson(nome, crm, especialidade, cpf, senha);
                JOptionPane.showMessageDialog(dialog,"Cadastro realizado com sucesso! ","Sucesso!",JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog,"Confirme a sua senha corretamente! ","Aviso",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    public static boolean temNumero(String texto) {
        return texto.matches(".*\\d.*");
    }
    

    
}
