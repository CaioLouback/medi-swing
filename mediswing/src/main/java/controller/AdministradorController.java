package controller;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static models.Medico.cadastrarMedicoJson;
import static models.Paciente.cadastrarPacienteJson;
import static models.Recepcionista.cadastrarRecepcionistaJson;
import models.Usuarios;
import static models.Usuarios.deletarUsuario;
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
    
    
    private static boolean temNumero(String texto) {
        return texto.matches(".*\\d.*");
    }
    
    public static void validarExclusao(JTable tabelaUsuarios, javax.swing.JDialog dialog){
            int linhaSelecionada = tabelaUsuarios.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(dialog,"Selecione um usuário na tabela!","Aviso",JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nome = tabelaUsuarios.getValueAt(linhaSelecionada, 0).toString();
            String cpf = tabelaUsuarios.getValueAt(linhaSelecionada, 1).toString();
            String tipo = tabelaUsuarios.getValueAt(linhaSelecionada, 2).toString();
            
            int opcao = JOptionPane.showConfirmDialog( dialog,"Deseja realmente excluir o usuário:\n" + nome,"Confirmação",JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                deletarUsuario(cpf, tipo);
                atualizarTabela(tabelaUsuarios);
            }
    }
    
    private static void atualizarTabela(JTable tabelaUsuarios) {
        DefaultTableModel model = (DefaultTableModel) tabelaUsuarios.getModel();
        model.setRowCount(0); 
        List<Object[]> dados = listarUsuariosTabela();
        for (Object[] linha : dados) {
            model.addRow(linha);
        }  
    }
    
    public static List<Object[]> listarUsuariosTabela() {
        List<Object[]> dados = new ArrayList<>();
        Gson gson = new Gson();

        try (BufferedReader reader = new BufferedReader(new FileReader("medicos.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<String, String> m = gson.fromJson(linha, Map.class);
                dados.add(new Object[]{m.getOrDefault("nome", "-"),m.getOrDefault("cpf", "-"),"Médico"});
            }
        } catch (IOException e) {
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("recepcionistas.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<String, String> r = gson.fromJson(linha, Map.class);
                dados.add(new Object[]{ r.getOrDefault("nome", "-"),r.getOrDefault("cpf", "-"),"Recepcionista"});
            }
        } catch (IOException e) {
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader("pacientes.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<String, String> p = gson.fromJson(linha, Map.class);
                dados.add(new Object[]{ p.getOrDefault("nome", "-"), p.getOrDefault("cpf", "-"),"Paciente"});
            }
        } catch (IOException e) {
        }
        return dados;
    }
    
    public static String buscarCpfPorNomeJson(String nome) {
        Gson gson = new Gson();
        File arquivo = new File("pacientes.json");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Usuarios p = gson.fromJson(linha, Usuarios.class);
                if (p.getNome().equalsIgnoreCase(nome)) {
                    return p.getCPF();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
