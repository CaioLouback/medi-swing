package models;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


public class Administrador extends Usuarios {

    private int nivelAcesso;

    public Administrador(String nome,String cpf, String senha, int nivelAcesso) {
        super(nome,cpf, senha);
        this.nivelAcesso = nivelAcesso;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
    
    public static void cadastrarAdministradorJson(String nome, String cpf,int nivelAcesso,String senha) {
        Map<String, Object> administradorMap = new LinkedHashMap<>();
        administradorMap.put("nome", nome);
        administradorMap.put("cpf", cpf);
        administradorMap.put("nivelAcesso", nivelAcesso);
        administradorMap.put("senha", senha);

        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter("administradores.json", true)) {
            writer.write(gson.toJson(administradorMap));
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean loginAdministrador(String cpf, String senha) {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader("administradores.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<String, Object> administradorMap= gson.fromJson(linha, Map.class);
                if (administradorMap.get("cpf").equals(cpf) && administradorMap.get("senha").equals(senha)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String buscarNomePorCpf(String cpf) {

        Gson gson = new Gson();

        // MÉDICOS
        try (BufferedReader reader = new BufferedReader(new FileReader("medicos.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {

                Map<String, String> medicoMap = gson.fromJson(
                        linha,
                        new com.google.gson.reflect.TypeToken<Map<String, String>>() {
                        }.getType()
                );

                if (medicoMap.get("cpf").equals(cpf)) {
                    return medicoMap.get("nome");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // PACIENTES
        try (BufferedReader reader = new BufferedReader(new FileReader("pacientes.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {

                Map<String, String> pacienteMap = gson.fromJson(
                        linha,
                        new com.google.gson.reflect.TypeToken<Map<String, String>>() {
                        }.getType()
                );

                if (pacienteMap.get("cpf").equals(cpf)) {
                    return pacienteMap.get("nome");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // RECEPCIONISTAS
        try (BufferedReader reader = new BufferedReader(new FileReader("recepcionistas.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {

                Map<String, String> recepMap = gson.fromJson(
                        linha,
                        new com.google.gson.reflect.TypeToken<Map<String, String>>() {
                        }.getType()
                );

                if (recepMap.get("cpf").equals(cpf)) {
                    return recepMap.get("nome");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // não encontrado
    }
    
    
}
