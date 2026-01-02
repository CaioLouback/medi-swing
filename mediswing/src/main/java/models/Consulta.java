package models;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;


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
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("\\s+", "").toLowerCase().trim();
    }
    
    public static void salvarConsultaJson(String nomeMedico,String dia,String hora,String paciente) {
        Gson gson = new Gson();
        String medicoNormalizado = normalizar(nomeMedico);
        String pacienteNormalizado = Normalizer.normalize(paciente, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").trim();
        File arquivo = new File("agenda_" + medicoNormalizado + ".json");
        Consulta consulta = new Consulta(dia, hora, pacienteNormalizado);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, true))) {
            bw.write(gson.toJson(consulta));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void deletarConsultaJson(String nomeMedico,String dia, String hora,String paciente) {
        Gson gson = new Gson();
        String medicoNormalizado = normalizar(nomeMedico);
        File arquivo = new File("agenda_" + medicoNormalizado + ".json");
        if (!arquivo.exists()) {
            return;
        }
        List<Consulta> restantes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Consulta c = gson.fromJson(linha, Consulta.class);
                boolean mesmaConsulta= c.getDia().equals(dia)&& c.getHora().equals(hora)&& c.getPaciente().equals(paciente);
                if (!mesmaConsulta) {
                    restantes.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo, false))) {
            for (Consulta c : restantes) {
                bw.write(gson.toJson(c));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
