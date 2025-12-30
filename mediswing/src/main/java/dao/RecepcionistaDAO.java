package dao;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


public class RecepcionistaDAO {

    public static void cadastrarRecepcionistaJson(String nome, String cpf, String senha) {

        Map<String, String> recepcionistaMap = new LinkedHashMap<>();
        recepcionistaMap.put("nome", nome);
        recepcionistaMap.put("cpf", cpf);
        recepcionistaMap.put("senha", senha);

        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter("recepcionistas.json", true)) {
            writer.write(gson.toJson(recepcionistaMap));
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean loginRecepcionista(String cpf, String senha) {

        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader("recepcionistas.json"))) {
            String linha;

            while ((linha = reader.readLine()) != null) {

                Map<String, String> recepcionistaMap
                        = gson.fromJson(linha, Map.class);
                if (recepcionistaMap.get("cpf").equals(cpf)
                        && recepcionistaMap.get("senha").equals(senha)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    
}
