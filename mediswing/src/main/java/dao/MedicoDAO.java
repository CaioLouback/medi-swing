package dao;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import models.Medico;


public class MedicoDAO {
    public static void cadastrarMedicoJson(String nome,String crm,String especialidade,String cpf,String senha) {

        Medico medico = new Medico(nome, crm, especialidade, cpf, senha);
        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter("medicos.json", true)) {
            writer.write(gson.toJson(medico));
            writer.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
