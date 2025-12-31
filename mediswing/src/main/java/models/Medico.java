package models;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

}
