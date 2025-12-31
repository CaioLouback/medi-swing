package controller;

import javax.swing.JOptionPane;
import static models.Administrador.loginAdministrador;
import static models.Medico.loginMedico;
import static models.Recepcionista.loginRecepcionista;
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
            } else {
                if (med == true ) {
                    janela.dispose();
                    MedicoTela tela = new MedicoTela();
                    tela.setLocationRelativeTo(null);
                    tela.setVisible(true);
                    return;
                } else if (recep == true ) {
                    janela.dispose();

                    RecepcionistaTela tela = new RecepcionistaTela();
                    tela.setLocationRelativeTo(null);
                    tela.setVisible(true);
                    return;
                } else if (adm == true) {
                    janela.dispose();
                    AdministradorTela tela = new AdministradorTela();
                    tela.setLocationRelativeTo(null);
                    tela.setVisible(true);
                    return;
                } 
            }
        }
    }
 
}
