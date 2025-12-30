package dao;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

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
    
    
    
}
