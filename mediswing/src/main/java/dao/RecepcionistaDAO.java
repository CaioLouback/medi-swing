package dao;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


public class RecepcionistaDAO {
    public static void cadastrarRecepcionistaJson(String nome, String cpf,String senha) {

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
}
