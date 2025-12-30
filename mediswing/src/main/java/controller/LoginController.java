package controller;

import static dao.AdministradorDAO.loginAdministrador;
import static dao.MedicoDAO.loginMedico;
import static dao.RecepcionistaDAO.loginRecepcionista;
import javax.swing.JOptionPane;
import view.AdministradorTela;
import view.MedicoTela;
import view.RecepcionistaTela;

public class LoginController {
    
    public static void validarLogin(String cpf, String senha, javax.swing.JFrame janela){
        if(cpf.isEmpty() || senha.isEmpty()){
            JOptionPane.showMessageDialog(janela,"Favor preencher os campos corretamente!","Aviso",JOptionPane.WARNING_MESSAGE);
        }else{
            boolean med = loginMedico(cpf, senha);
            boolean recep = loginRecepcionista(cpf, senha);
            boolean adm = loginAdministrador(cpf, senha);
            if(med == false && recep == false && adm == false){
                JOptionPane.showMessageDialog(janela,"CPF ou senha incorretos!","Atenção!",JOptionPane.WARNING_MESSAGE);
            } else if(med) {
                janela.dispose();
                MedicoTela tela = new MedicoTela();
                tela.setLocationRelativeTo(null);
                tela.setVisible(true);
                return;
            } else if (recep){
                janela.dispose();
                RecepcionistaTela tela = new RecepcionistaTela();
                tela.setLocationRelativeTo(null);
                tela.setVisible(true);
                return;
            }else if(adm){
                janela.dispose();
                AdministradorTela tela = new AdministradorTela();
                tela.setLocationRelativeTo(null);
                tela.setVisible(true);
                return;
            }
            
        }
    
    }
    
    
    
}
