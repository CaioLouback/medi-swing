package models;

import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;


public class Consulta {

    private String dia;
    private String hora;
    private String paciente;

    public Consulta(String dia, String hora, String paciente) {
        this.dia = dia;
        this.hora = hora;
        this.paciente = paciente;
    }

    public String getDia() {
        return dia;
    }

    public String getHora() {
        return hora;
    }

    public String getPaciente() {
        return paciente;
    }
    
    public static String normalizar(String texto) {
        return Normalizer
                .normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("\\s+", "")
                .toLowerCase()
                .trim();
    }
    
    
    
    public static void salvarConsultaJson(
            String nomeMedico,
            String dia,
            String hora,
            String paciente
    ) {

        Gson gson = new Gson();

        String medicoNormalizado = normalizar(nomeMedico);
        String pacienteNormalizado = Normalizer
                .normalize(paciente, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .trim();

        File arquivo = new File("agenda_" + medicoNormalizado + ".json");

        Consulta consulta = new Consulta(dia, hora, pacienteNormalizado);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
            bw.write(gson.toJson(consulta));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
}
