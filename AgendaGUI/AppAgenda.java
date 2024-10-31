package control;

import java.io.File;
import java.io.IOException;
import vista.VistaAgenda;
import modelo.Agenda;
import modelo.Contacto;
import modelo.ES_Agenda;

public class AppAgenda {
	public static String ficheroAgendaCSV = "agenda.csv";
	public static String ficheroAgendaSERIAL = "agenda.ser";
	protected File fichero;
	protected Agenda agenda;
	protected VistaAgenda vista;
	
	
	public AppAgenda() {
		agenda = new Agenda();
	}
	/*
	public AppAgenda(String fileName) {
		fichero = new File(fileName);
		try {
			agenda = ES_Agenda.leeAgendaDeCsv(fichero);
		} catch (IOException e) {
			agenda = new Agenda();
		}
	} // constructor
	*/
	
	public void editarContacto(String nombre, String teléfono) {
		agenda.getContacto(nombre).setTeléfono(teléfono);
	} // editaContacto
	
	public Contacto borrarContacto(String nombre) {
		return agenda.borrarContacto(nombre);
	} // borrarContacto
	
	public Contacto añadirContacto(String nombre, String teléfono) {
		return agenda.addContacto(new Contacto(nombre,teléfono));
	} // añadirContacto
	
	
	
	public void rellenaVista() {
		vista.actualizaListado(agenda.getTodos());
	} // rellenaVista
	
	public boolean guardarFicheroCSV() {
		try {
			ES_Agenda.escribeAgendaEnCsv(new File(ficheroAgendaCSV) , agenda);
			return true;
		} catch (IOException e) {
			return false;
		}
	} // guardaFicheroCSV
	
	public void recargaFicheroCSV() {
		try {
			agenda = ES_Agenda.leeAgendaDeCsv(new File(ficheroAgendaCSV));
		} catch (IOException e) {
			agenda = new Agenda();
		}
		vista.actualizaListado(agenda.getTodos());
	} // recargaFicheroCSV
	
	public void recargaFicheroSerial() {
		try {
			agenda = ES_Agenda.leeAgendaDeSerial(new File(ficheroAgendaSERIAL));
		} catch (Exception e) {
			agenda = new Agenda();
		}
		vista.actualizaListado(agenda.getTodos());
	} // recargaFicheroSerial
	
	public boolean guardarFicheroSerial() {
		try {
			ES_Agenda.escribeAgendaEnSerial(new File(ficheroAgendaSERIAL) , agenda);
			return true;
		} catch (IOException e) {
			return false;
		}
	} // guardaFicheroSerial
	
	
	
	@Override
	public String toString() {
		return agenda.toCSV();
	} // toString
	
	public static void main(String[] args) {
		AppAgenda app = new AppAgenda();
		app.vista = new VistaAgenda(app);
		app.rellenaVista();
	} // main

} // class
