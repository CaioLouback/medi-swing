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


public class Paciente {
    private String nome;
    private String cpf;
    private String dataNascimento;
    private String endereco;
    private String telefone;

    public Paciente(String nome,String cpf, String dataNascimento,String endereco, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    
    public static void cadastrarPacienteJson(String nome, String cpf,String dataNascimento, String endereco,String telefone) {
        Map<String, String> pacienteMap = new LinkedHashMap<>();
        pacienteMap.put("nome", nome);
        pacienteMap.put("cpf", cpf);
        pacienteMap.put("dataNascimento", dataNascimento);
        pacienteMap.put("endereco", endereco);
        pacienteMap.put("telefone", telefone);

        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter("pacientes.json", true)) {
            writer.write(gson.toJson(pacienteMap));
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<String> listarPacientes() {
        List<String> lista = new ArrayList<>();
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader("pacientes.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                lista.add(linha);
            }
        } catch (IOException e) {
        }

        return lista;
    }
    
    public static void deletarPaciente(String cpf) {
        File arquivo = new File("pacientes.json");
        List<String> linhasRestantes = new ArrayList<>();
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<?, ?> paciente = gson.fromJson(linha, Map.class);
                if (!paciente.get("cpf").equals(cpf)) {
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
    
    public static List<String> listarNomesPacientes() {
        List<String> lista = new ArrayList<>();
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader("pacientes.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<String, String> pacienteMap = gson.fromJson(linha,new com.google.gson.reflect.TypeToken<Map<String, String>>() {}.getType());
                lista.add(pacienteMap.get("nome"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
