package dao;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import models.Medico;
import models.Usuarios;


public class MedicoDAO {
    
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

                Map<String, String> medicoMap =
                        gson.fromJson(linha, Map.class);

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
    
    
    
}
