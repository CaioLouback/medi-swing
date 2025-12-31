package controller;

import static dao.AdministradorDAO.cadastrarAdministradorJson;
import static dao.MedicoDAO.cadastrarMedicoJson;
import static dao.MedicoDAO.deletarMedico;
import static dao.MedicoDAO.listarMedicos;
import static dao.PacienteDAO.cadastrarPacienteJson;
import static dao.PacienteDAO.deletarPaciente;
import static dao.PacienteDAO.listarPacientes;
import static dao.RecepcionistaDAO.cadastrarRecepcionistaJson;
import static dao.RecepcionistaDAO.deletarRecepcionista;
import static dao.RecepcionistaDAO.listarRecepcionistas;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
        }else if(txt.equals("Médico")){
            dialogCadastroMedico(janela);
        }else{
            System.out.println("COLOCAR CADASTRO DE ADM!");
        }
    }
    
    public static void validarCadastroMedico(String nome, String crm, String especialidade, String cpf, String senha, String senha2,javax.swing.JDialog dialog ){
        if(nome.isEmpty() || crm.isEmpty() || especialidade.isEmpty() || cpf.isEmpty() || senha.isEmpty() || senha2.isEmpty()){
            JOptionPane.showMessageDialog(dialog,"Favor preencher os campos corretamente!","Aviso",JOptionPane.WARNING_MESSAGE);
        } else {
            if (temNumero(nome)) {
                JOptionPane.showMessageDialog(dialog,"O nome não pode conter números!","Aviso",JOptionPane.WARNING_MESSAGE);
            }else{
                if(temNumero(especialidade)){
                    JOptionPane.showMessageDialog(dialog,"A especialidade não pode conter números!","Aviso",JOptionPane.WARNING_MESSAGE);
                } else{
                    if(senha.equals(senha2)){
                        cadastrarMedicoJson(nome, crm, especialidade, cpf, senha);
                        JOptionPane.showMessageDialog(dialog,"Cadastro realizado com sucesso! ","Sucesso!",JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(dialog,"Confirme a sua senha corretamente! ","Aviso",JOptionPane.WARNING_MESSAGE);
                    }
                }   
            }
        }
    }
    
    public static void validarCadastroRecep(String nome, String cpf, String senha, String senha2,javax.swing.JDialog dialog ){
        if(nome.isEmpty()|| cpf.isEmpty() || senha.isEmpty() || senha2.isEmpty()){
            JOptionPane.showMessageDialog(dialog,"Favor preencher os campos corretamente!","Aviso",JOptionPane.WARNING_MESSAGE);
        } else {
            if (temNumero(nome)) {
                JOptionPane.showMessageDialog(dialog,"O nome não pode conter números!","Aviso",JOptionPane.WARNING_MESSAGE);
            }else {
                if(senha.equals(senha2)){
                    cadastrarRecepcionistaJson(nome, cpf, senha);
                    JOptionPane.showMessageDialog(dialog,"Cadastro realizado com sucesso! ","Sucesso!",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog,"Confirme a sua senha corretamente! ","Aviso",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
    
    public static void validarCadastroPaciente(String nome, String cpf, String nascimento, String endereco, String telefone,javax.swing.JDialog dialog ){
        if(nome.isEmpty()|| cpf.isEmpty() || nascimento.isEmpty() || endereco.isEmpty() || telefone.isEmpty()){
            JOptionPane.showMessageDialog(dialog,"Favor preencher os campos corretamente!","Aviso",JOptionPane.WARNING_MESSAGE);
        } else {
            if (temNumero(nome)){
                JOptionPane.showMessageDialog(dialog,"O nome não pode conter números!","Aviso",JOptionPane.WARNING_MESSAGE);
            } else {
                cadastrarPacienteJson(nome, cpf, nascimento, endereco, telefone);
                JOptionPane.showMessageDialog(dialog,"Cadastro realizado com sucesso! ","Sucesso!",JOptionPane.INFORMATION_MESSAGE);
            }  
        }
    }
    
    
    public static boolean temNumero(String texto) {
        return texto.matches(".*\\d.*");
    }
    
    
    
    
    
}
