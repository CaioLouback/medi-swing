package controller;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import models.Consulta;
import static models.Consulta.deletarConsultaJson;
import static models.Consulta.salvarConsultaJson;


public class ConsultaController {
    public static void preencherTabelaAgenda(JTable tabelaConsultas, List<Consulta> consultas) {
        for (Consulta c : consultas) {
            String dia = c.getDia();
            String hora = c.getHora();
            String paciente = c.getPaciente();
            int colunaDia = -1;
            for (int j = 0; j < tabelaConsultas.getColumnCount(); j++) {
                if (tabelaConsultas.getColumnName(j).equalsIgnoreCase(dia)) {
                    colunaDia = j;
                    break;
                }
            }
            String[] HORAS = {
                "08:00", "09:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00", "17:00"
            };
            int linhaHora = -1;
            for (int i = 0; i < HORAS.length; i++) {
                if (HORAS[i].equals(hora)) {
                    linhaHora = i;
                    break;
                }
            }
            if (linhaHora != -1 && colunaDia != -1) {
                tabelaConsultas.setValueAt(paciente, linhaHora, colunaDia);
            }
        }
    }
    
    public static void limparTabelaAgenda(JTable tabelaConsultas) {
        for (int i = 0; i < tabelaConsultas.getRowCount(); i++) {
            for (int j = 0; j < tabelaConsultas.getColumnCount(); j++) {
                tabelaConsultas.setValueAt("-", i, j);
            }
        }
    }

    public static void validarCancelamento(JTable tabelaConsultas,javax.swing.JDialog dialog, JComboBox<String> cbMedico){
        int linha = tabelaConsultas.getSelectedRow();
        int coluna = tabelaConsultas.getSelectedColumn();
        if (linha == -1 || coluna == -1) {
            JOptionPane.showMessageDialog(dialog,"Selecione um horário na tabela!","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object valorAtual = tabelaConsultas.getValueAt(linha, coluna);
        if (valorAtual == null || valorAtual.toString().equals("-")) {
            JOptionPane.showMessageDialog(dialog,"Esse horário já está livre!","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcao = JOptionPane.showConfirmDialog(dialog,"Deseja cancelar a consulta?","Confirmação",JOptionPane.YES_NO_OPTION);
        if (opcao == JOptionPane.YES_OPTION) {
            String medico = cbMedico.getSelectedItem().toString();
            String paciente = tabelaConsultas.getValueAt(linha, coluna).toString();
            String dia = tabelaConsultas.getColumnName(coluna);
            String[] horas = {
                "08:00", "09:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00", "17:00"
            };
            String hora = horas[linha];
            deletarConsultaJson(medico, dia, hora, paciente);
            tabelaConsultas.setValueAt("-", linha, coluna);
        }
    }

    public static void validarAgendamento(JTable tabelaConsultas,javax.swing.JDialog dialog, JComboBox<String> cbPaciente,JComboBox<String> cbMedico ){
        int linha = tabelaConsultas.getSelectedRow();
        int coluna = tabelaConsultas.getSelectedColumn();
        if (linha == -1 || coluna == -1) {
            JOptionPane.showMessageDialog( dialog,"Selecione um horário na tabela!","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String paciente = cbPaciente.getSelectedItem().toString();
        String medico = cbMedico.getSelectedItem().toString();
        if (paciente == null || paciente.isEmpty()) {
            JOptionPane.showMessageDialog(dialog,"Selecione um paciente!","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object valorAtual = tabelaConsultas.getValueAt(linha, coluna);
        if (valorAtual != null && !valorAtual.toString().equals("-")) {
            JOptionPane.showMessageDialog( dialog,"Esse horário já está ocupado!", "Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        String[] HORAS = {
            "08:00", "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00"
        };
        tabelaConsultas.setValueAt(paciente, linha, coluna);
        String dia = tabelaConsultas.getColumnName(coluna);
        String hora = HORAS[linha];

        salvarConsultaJson(medico, dia, hora, paciente);
    }
    
    
    
    
    
}
