package controller;

import com.google.gson.Gson;
import static controller.AdministradorController.buscarCpfPorNomeJson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import static models.Medico.buscarProntuariosPaciente;
import static models.Medico.buscarReceitasPaciente;
import static models.Medico.criarProntuarioJson;
import static models.Medico.criarReceitaPaciente;


public class MedicoController {

    public static void validarReceita(JTable tabelaConsultas, JFrame frame, String medico) {

        int linha = tabelaConsultas.getSelectedRow();
        int coluna = tabelaConsultas.getSelectedColumn();

        if (linha == -1 || coluna == -1) {
            JOptionPane.showMessageDialog(frame,"Selecione um paciente na tabela!","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object valor = tabelaConsultas.getValueAt(linha, coluna);

        if (valor == null || valor.toString().equals("-")) {
            JOptionPane.showMessageDialog(frame,"Esse horário está livre!","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(frame,"Deseja criar uma receita para este paciente?","Confirmação",JOptionPane.YES_NO_OPTION);
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
    
    public static void validarProntuario(JTable tabelaConsultas, JFrame frame, String medico) {

        int linha = tabelaConsultas.getSelectedRow();
        int coluna = tabelaConsultas.getSelectedColumn();

        if (linha == -1 || coluna == -1) {
            JOptionPane.showMessageDialog(frame,"Selecione um paciente na tabela!","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object valor = tabelaConsultas.getValueAt(linha, coluna);

        if (valor == null || valor.toString().equals("-")) {
            JOptionPane.showMessageDialog(frame,"Esse horário está livre!","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }

        int opcao = JOptionPane.showConfirmDialog(frame,"Deseja criar um prontuário para este paciente?","Confirmação",JOptionPane.YES_NO_OPTION);
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
        criarProntuarioPaciente(paciente,medico, dia, hora, frame);
    }
    
    public static List<Map<String, String>> carregarProntuarioDoMedico(String cpfPaciente,String medicoLogado) {
        List<Map<String, String>> prontuarios = new ArrayList<>();
        Gson gson = new Gson();
        String cpfNormalizado = cpfPaciente.replaceAll("[^0-9]", "");
        File arquivo = new File("prontuario_" + cpfNormalizado + ".json");
        if (!arquivo.exists()) {
            return prontuarios;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Map<String, String> registro = gson.fromJson(linha,new com.google.gson.reflect.TypeToken<Map<String, String>>() {}.getType());
                if (registro.get("medico").equals(medicoLogado)) {
                    prontuarios.add(registro);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prontuarios;
    }
    
    public static void criarProntuarioPaciente(String nomePaciente,String medico ,String dia,String hora,JFrame frame) {
        String cpfPaciente = buscarCpfPorNomeJson(nomePaciente);
        if (cpfPaciente == null) {
            JOptionPane.showMessageDialog(frame,"CPF do paciente não encontrado!","Erro",JOptionPane.ERROR_MESSAGE);
            return;
        }
        String descricao = JOptionPane.showInputDialog(frame,"Digite no prontuário: ");

        if (descricao == null || descricao.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame,"O prontuário não pode estar vazio!","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        criarProntuarioJson(nomePaciente,medico,dia, hora,descricao);

        JOptionPane.showMessageDialog(frame, "Prontuário criado com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void verHistorico(
            JTable tabelaConsultas,
            JTextPane textPane,
            JFrame frame,
            String medicoLogado
    ) {
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

        String paciente = tabelaConsultas.getValueAt(linha, coluna).toString();

        if (paciente.equals("-")) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Esse horário não possui paciente!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String historico = montarHistoricoPaciente(paciente, medicoLogado);

        textPane.setText(historico);
    }
    
    public static String montarHistoricoPaciente(String paciente, String medicoLogado) {

        StringBuilder sb = new StringBuilder();

        sb.append("===== HISTÓRICO DO PACIENTE =====\n\n");
        sb.append("Paciente: ").append(paciente).append("\n");
        sb.append("Médico: ").append(medicoLogado).append("\n\n");

        sb.append("----- RECEITAS -----\n");
        sb.append(buscarReceitasPaciente(paciente));

        sb.append("\n----- PRONTUÁRIOS -----\n");
        sb.append(buscarProntuariosPaciente(paciente, medicoLogado));

        return sb.toString();
    }
    
    
}
