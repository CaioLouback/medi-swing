package dao;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import models.Usuarios;

public class AdministradorDAO {
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

                Map<String, Object> administradorMap
                        = gson.fromJson(linha, Map.class);

                if (administradorMap.get("cpf").equals(cpf)
                        && administradorMap.get("senha").equals(senha)) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public static List<Object[]> listarUsuariosTabela() {

        List<Object[]> dados = new ArrayList<>();
        Gson gson = new Gson();

        // MÉDICOS
        try (BufferedReader reader = new BufferedReader(new FileReader("medicos.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<String, String> m = gson.fromJson(linha, Map.class);
                dados.add(new Object[]{
                    m.getOrDefault("nome", "-"),
                    m.getOrDefault("cpf", "-"),
                    "Médico"
                });
            }
        } catch (IOException e) {
        }

        // RECEPCIONISTAS
        try (BufferedReader reader = new BufferedReader(new FileReader("recepcionistas.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<String, String> r = gson.fromJson(linha, Map.class);
                dados.add(new Object[]{
                    r.getOrDefault("nome", "-"),
                    r.getOrDefault("cpf", "-"),
                    "Recepcionista"
                });
            }
        } catch (IOException e) {
        }

        // PACIENTES
        try (BufferedReader reader = new BufferedReader(new FileReader("pacientes.json"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Map<String, String> p = gson.fromJson(linha, Map.class);
                dados.add(new Object[]{
                    p.getOrDefault("nome", "-"),
                    p.getOrDefault("cpf", "-"),
                    "Paciente"
                });
            }
        } catch (IOException e) {
        }

        return dados;
    }

}
