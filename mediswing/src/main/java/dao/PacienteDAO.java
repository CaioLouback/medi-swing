package dao;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


public class PacienteDAO {
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
}
