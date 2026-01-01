package controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static models.Medico.criarReceitaPaciente;


public class MedicoController {

    public static void validarReceita(JTable tabelaConsultas, JFrame frame, String medico) {

        int linha = tabelaConsultas.getSelectedRow();
        int coluna = tabelaConsultas.getSelectedColumn();

        if (linha == -1 || coluna == -1) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Selecione um paciente na tabela!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Object valor = tabelaConsultas.getValueAt(linha, coluna);

        if (valor == null || valor.toString().equals("-")) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Esse horário está livre!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(
                frame,
                "Deseja criar uma receita para este paciente?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (opcao != JOptionPane.YES_OPTION) {
            return;
        }

        String paciente = valor.toString();
        String dia = tabelaConsultas.getColumnName(coluna);

        String[] horas = {
            "08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00"
        };
        String hora = horas[linha];

        criarReceitaPaciente(paciente,medico, dia, hora, frame);
    }
}
