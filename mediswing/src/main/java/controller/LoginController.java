package controller;

import static dao.MedicoDAO.loginMedico;
import javax.swing.JOptionPane;
import view.MedicoTela;

public class LoginController {
    
    public static void validarLogin(String cpf, String senha, javax.swing.JFrame janela){
        if(cpf.isEmpty() || senha.isEmpty()){
            JOptionPane.showMessageDialog(janela,"Favor preencher os campos corretamente!","Aviso",JOptionPane.WARNING_MESSAGE);
        }else{
            boolean retorno = loginMedico(cpf, senha);
            if(retorno == false){
                JOptionPane.showMessageDialog(janela,"CPF ou senha incorretos!","Atenção!",JOptionPane.WARNING_MESSAGE);
            } else {
                janela.dispose();
                MedicoTela tela = new MedicoTela();
                tela.setLocationRelativeTo(null);
                tela.setVisible(true);
                return;
            }
        }
    
    }
    
    
    
}
