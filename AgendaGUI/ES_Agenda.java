package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

public class ES_Agenda {
	public static Agenda leeAgendaDeCsv(File fichero) throws IOException {
		Agenda agenda = new Agenda();
		FileReader fr = new FileReader(fichero,StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(fr);
		String línea;
		while (  (línea=br.readLine()) != null) {
			String campos[] = línea.split(",");
			if (campos.length>1) {
				Contacto c = new Contacto(campos[0],campos[1]);
				agenda.addContacto(c);
			}
			else System.err.println("Línea de entrada inválida: " + línea);
		}
		br.close();
		return agenda;
	} // leeAgendaDeCsv
	
	public static void escribeAgendaEnCsv(File fichero, Agenda agenda) throws IOException {
		FileWriter salida = new FileWriter(fichero);
		salida.write(agenda.toCSV());
		salida.close();
	} // guardaClaseEnCsv
	
	
	public static Agenda leeAgendaDeSerial(File fichero) throws Exception {
		Agenda agenda;
	
		try ( FileInputStream file = new FileInputStream(fichero);
				ObjectInputStream input = new ObjectInputStream(file);)
		
		{
			agenda = (Agenda)input.readObject();
		} catch (ClassNotFoundException | IOException e) {
			agenda = new Agenda();
			System.err.println("Error leyendo agenda de fichero: " + fichero.getAbsolutePath());
			System.err.println(e.getMessage());
			throw e;
		}
		return agenda;
	} // leeAgendaDeSerial
	
	public static void escribeAgendaEnSerial(File fichero, Agenda agenda) throws IOException {
		try (
				FileOutputStream file = new FileOutputStream(fichero);
				ObjectOutputStream output = new ObjectOutputStream(file);
		)
		{
			output.writeObject(agenda);
		} catch (IOException e) {
			System.err.println("Error guardando agenda a fichero: " + fichero.getAbsolutePath());
			System.err.println(e.getMessage());
			throw e;
		}
		
		
	}
	
	
	
} // ES_Agenda
