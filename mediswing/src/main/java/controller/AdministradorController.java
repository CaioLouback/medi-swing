package controller;

import view.CadastroMedico;
import view.CadastroPaciente;
import view.CadastroRecepcionista;


public class AdministradorController {
    
    public static void dialogCadastroMedico(javax.swing.JFrame janela){
        CadastroMedico dialog =
        new CadastroMedico(janela, true); 
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
    }
    
    public static void dialogCadastroPaciente(javax.swing.JFrame janela){
        CadastroPaciente pac = new CadastroPaciente(janela,true);
        pac.setVisible(true);
        pac.setLocationRelativeTo(null);
    }
    
    public static void dialogCadastroRecepcionista(javax.swing.JFrame janela){
        CadastroRecepcionista recep = new CadastroRecepcionista(janela, true);
        recep.setVisible(true);
        recep.setLocationRelativeTo(null);
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
}
