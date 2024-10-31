package gestionAlumnos.Controller;

import java.awt.event.ActionEvent;
import gestionAlumnos.Model.ModeloAlumnosJDBC;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gestionAlumnos.Model.Alumno;
import gestionAlumnos.Model.IModeloAlumnos;
import gestionAlumnos.UI.VentanaAlumnos;

public class ControladorGestionAlumnos implements ActionListener, ListSelectionListener {

	private IModeloAlumnos model;
	private VentanaAlumnos view;

	public ControladorGestionAlumnos(IModeloAlumnos model, VentanaAlumnos view) {
		this.model = model;
		this.view = view;
		anadirListeners(this);

		this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.view.pack();
		this.view.setLocationRelativeTo(null);
		this.view.setVisible(true);
	}

	private void anadirListeners(ControladorGestionAlumnos controladorGestionAlumnos) {
		view.btnCargarTodos.addActionListener(controladorGestionAlumnos);
		view.btnCrear.addActionListener(controladorGestionAlumnos);
		view.btnModificar.addActionListener(controladorGestionAlumnos);
		view.btnEliminar.addActionListener(controladorGestionAlumnos);

		view.jListaAlumnos.addListSelectionListener(controladorGestionAlumnos);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String actionCommand = event.getActionCommand();
		System.out.println("estoy en actionPerformed con la opcion " + actionCommand);
		String DNI = view.textFieldDNI.getText();
		String nombre = view.textFieldNombre.getText();
		String apellidos = view.textFieldApellidos.getText();
		String CP = view.textFieldCP.getText();
		switch (actionCommand) {

		case "Cargar Todos":
			DefaultListModel<String> modelo = new DefaultListModel<String>();
		    List<String> alumnos = model.getAll();
		    for (String alumnoCompleto : alumnos) {
		        modelo.addElement(alumnoCompleto);
		    }
		    System.out.println(modelo);
			view.jListaAlumnos.setModel(modelo);
			break;
		case "Crear Nuevo":
			Alumno alumno = new Alumno();
			alumno.setDNI(DNI);
			System.out.println(DNI.length());
			alumno.setApellidos(apellidos);
			alumno.setCP(CP);
			alumno.setNombre(nombre);
			model.crear(alumno);

			break;
		case "Modificar":
			Alumno alumnoModificar = new Alumno();
			alumnoModificar.setDNI(DNI);
			System.out.println(DNI.length());
			alumnoModificar.setApellidos(apellidos);
			alumnoModificar.setCP(CP);
			alumnoModificar.setNombre(nombre);
			model.modificarAlumno(alumnoModificar);

			break;
		case "Eliminar":
			model.eliminarAlumno(view.textFieldDNI.getText());
			break;
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO implementar el pinchar de una lista
		System.out.println("estoy en valueChanged");
		if (!e.getValueIsAdjusting()) {// This line prevents double events

			// TODO
			// view.jListaAlumnos

		}

	}

	private void limpiarCampos() {
		view.textFieldDNI.setText("");
		view.textFieldNombre.setText("");
		view.textFieldApellidos.setText("");
		view.textFieldCP.setText("");

	}

	private void cargarAlumno(Alumno alumno) {
		if (alumno == null) {
			limpiarCampos();
		}

		view.textFieldDNI.setText(alumno.getDNI());
		view.textFieldNombre.setText(alumno.getNombre());
		view.textFieldApellidos.setText(alumno.getApellidos());
		view.textFieldCP.setText(alumno.getCP());
	}

}
