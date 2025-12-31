package controller;

import javax.swing.JComboBox;
import static models.Medico.listarNomesMedicos;
import static models.Paciente.listarNomesPacientes;


public class RecepController {
    public static void carregarComboMedicos(JComboBox<String> combo) {

    combo.removeAllItems(); // limpa

    for (String nome : listarNomesMedicos()) {
        combo.addItem(nome);
    }
}
    public static void carregarComboPacientes(JComboBox<String> combo) {

    combo.removeAllItems();

    for (String nome : listarNomesPacientes()) {
        combo.addItem(nome);
    }
}
    
    
    
}
