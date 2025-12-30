package dao;

import com.google.gson.Gson;
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
}
