package controller;

import static dao.MedicoDAO.listarNomesMedicos;
import static dao.PacienteDAO.listarNomesPacientes;
import javax.swing.JComboBox;


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
