package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static controller.AdministradorController.buscarCpfPorNomeJson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static models.Administrador.buscarCpfPacientePorNome;
import static models.Consulta.normalizar;


public class Medico extends Usuarios {
    protected String crm;
    protected String especialidade;
 
    public Medico(String nome,String crm,String especialidade,String cpf,String senha) {
        super(nome,cpf, senha);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getCRM() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }
    
    public static void cadastrarMedicoJson(String nome,String crm,String especialidade, String cpf,String senha) {

        Map<String, String> medicoMap = new LinkedHashMap<>();
        medicoMap.put("nome", nome);
        medicoMap.put("cpf", cpf);
        medicoMap.put("especialidade", especialidade);
        medicoMap.put("crm", crm);
        medicoMap.put("senha", senha);

        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter("medicos.json", true)) {
            writer.write(gson.toJson(medicoMap));
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean loginMedico(String cpf, String senha) {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader("medicos.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<String, String> medicoMap =gson.fromJson(linha, Map.class);
                if (medicoMap.get("cpf").equals(cpf) &&
                    medicoMap.get("senha").equals(senha)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public static List<String> listarMedicos() {
        List<String> lista = new ArrayList<>();
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader("medicos.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {lista.add(linha);
            }
        } catch (IOException e) {
        }
        return lista;
    }

    public static void deletarMedico(String cpf) {
        File arquivo = new File("medicos.json");
        List<String> linhasRestantes = new ArrayList<>();
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<?, ?> medico = gson.fromJson(linha, Map.class);
                if (!medico.get("cpf").equals(cpf)) {
                    linhasRestantes.add(linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(arquivo, false)) {
            for (String l : linhasRestantes) {
                writer.write(l);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<String> listarNomesMedicos() {
        List<String> lista = new ArrayList<>();
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader("medicos.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<String, String> medicoMap = gson.fromJson(linha,new com.google.gson.reflect.TypeToken<Map<String, String>>() {}.getType());
                lista.add(medicoMap.get("nome"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public static List<Consulta> carregarAgendaDoMedico(String nomeMedico) {
        List<Consulta> consultas = new ArrayList<>();
        Gson gson = new Gson();
        String medicoNormalizado = normalizar(nomeMedico);
        File arquivo = new File("agenda_" + medicoNormalizado + ".json");
        if (!arquivo.exists()) {
            System.out.println("Arquivo NAO encontrado: " + arquivo.getAbsolutePath());
            return consultas;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Consulta c = gson.fromJson(linha, Consulta.class);
                consultas.add(c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return consultas;
    }
    
    public static void criarReceitaPaciente(String nomePaciente,String medico ,String dia,String hora,JFrame frame) {
        String cpfPaciente = buscarCpfPorNomeJson(nomePaciente);
        if (cpfPaciente == null) {
            JOptionPane.showMessageDialog(
                    frame,
                    "CPF do paciente não encontrado!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        String descricao = JOptionPane.showInputDialog(frame,"Digite a receita médica:");

        if (descricao == null || descricao.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame,"A receita não pode estar vazia!","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        //String medico = buscarNomePorCpf(cpfPaciente);
        criarReceitaPacienteJson(nomePaciente, cpfPaciente,medico,dia, hora,descricao);

        JOptionPane.showMessageDialog(frame, "Receita criada com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void criarReceitaPacienteJson(String nomePaciente,String cpfPaciente,String medico, String dia, String hora,String descricao) {
        Gson gson = new Gson();
        String cpfNormalizado = cpfPaciente.replaceAll("[^0-9]", "");
        String nomeArquivo = "receitas_" + cpfNormalizado + ".json";
        File arquivo = new File(nomeArquivo);

        Map<String, String> receita = new LinkedHashMap<>();
        receita.put("Paciente: ",nomePaciente);
        receita.put("medico", medico);
        receita.put("dia", dia);
        receita.put("hora", hora);
        receita.put("descricao", descricao);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
            bw.write(gson.toJson(receita));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void criarProntuarioJson(String nomePaciente,String medico,String dia,String hora,String observacao) {
        String cpfPaciente = buscarCpfPorNomeJson(nomePaciente);
        Gson gson = new Gson();
        String cpfNormalizado = cpfPaciente.replaceAll("[^0-9]", "");
        File arquivo = new File("prontuario_" + cpfNormalizado + ".json");
        Map<String, String> prontuario = new LinkedHashMap<>();
        prontuario.put("medico", medico);
        prontuario.put("Paciente", nomePaciente);
        prontuario.put("dia", dia);
        prontuario.put("hora", hora);
        prontuario.put("observacao", observacao);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
            bw.write(gson.toJson(prontuario));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String buscarReceitasPaciente(String paciente) {

        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        String cpfPaciente = buscarCpfPacientePorNome(paciente);
        File arquivo = new File("receitas_" + normalizar(cpfPaciente) + ".json");

        if (!arquivo.exists()) {
            sb.append("Nenhuma receita encontrada.\n");
            return sb.toString();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {

                Map<String, String> receita = gson.fromJson(
                        linha,
                        new com.google.gson.reflect.TypeToken<Map<String, String>>() {
                        }.getType()
                );

                sb.append("Dia: ").append(receita.get("dia")).append("\n");
                sb.append("Hora: ").append(receita.get("hora")).append("\n");
                sb.append("Descrição: ").append(receita.get("descricao")).append("\n");
                sb.append("---------------------\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
    
    public static String buscarProntuariosPaciente(String paciente, String medicoLogado) {

        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        String cpfPaciente = buscarCpfPacientePorNome(paciente);
        File arquivo = new File("prontuario_" + normalizar(cpfPaciente) + ".json");

        if (!arquivo.exists()) {
            sb.append("Nenhum prontuário encontrado.\n");
            return sb.toString();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {

                Map<String, String> prontuario = gson.fromJson(
                        linha,
                        new com.google.gson.reflect.TypeToken<Map<String, String>>() {
                        }.getType()
                );

                // 🔐 REGRA DE NEGÓCIO
                if (!prontuario.get("medico").equals(medicoLogado)) {
                    continue;
                }

                sb.append("Dia: ").append(prontuario.get("dia")).append("\n");
                sb.append("Hora: ").append(prontuario.get("hora")).append("\n");
                sb.append("Observação: ").append(prontuario.get("observacao")).append("\n");
                sb.append("---------------------\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
 
    
    
}
