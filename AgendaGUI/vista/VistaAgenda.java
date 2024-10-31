package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.Contacto;
import control.AppAgenda;

public class VistaAgenda implements ActionListener {
    private AppAgenda controlador;

    // Componentes visuales:
    private JFrame ventana;
    private JTable tablaListado;
    private JButton botónAñadir, botónBorrar, botónEditar, botónMostrar, botónMostrarTodos;
    private DefaultTableModel modeloTabla;

    public VistaAgenda(AppAgenda controlador) {
        this.controlador = controlador;
        construyeVentanaAgenda();
    }

    private void construyeVentanaAgenda() {
        ventana = new JFrame("Agenda");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Teléfono"}, 0);
        tablaListado = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaListado);
        ventana.add(scrollPane, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        botónAñadir = new JButton("Añadir");
        botónBorrar = new JButton("Borrar");
        botónEditar = new JButton("Editar");
        botónMostrar = new JButton("Mostrar Contactos");
        botónMostrarTodos = new JButton("Mostrar Todos");

        panelInferior.add(botónAñadir);
        panelInferior.add(botónBorrar);
        panelInferior.add(botónEditar);
        panelInferior.add(botónMostrar);
        panelInferior.add(botónMostrarTodos);
        ventana.add(panelInferior, BorderLayout.SOUTH);
        botónAñadir.addActionListener(this);
        botónBorrar.addActionListener(this);
        botónEditar.addActionListener(this);
        botónMostrar.addActionListener(this);
        botónMostrarTodos.addActionListener(this);

        ventana.setSize(500, 400);
        ventana.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Añadir":
                añadirContacto();
                break;
            case "Borrar":
                borrarContacto();
                break;
            case "Editar":
                editarContacto();
                break;
            case "Mostrar Contactos":
                mostrarContactos();
                break;
            
        }
    }

    private void añadirContacto() {
        String nombre = JOptionPane.showInputDialog("Nombre del contacto:");
        String telefono = JOptionPane.showInputDialog("Teléfono del contacto:");
        if (!nombre.isEmpty() && !telefono.isEmpty()) {
            controlador.añadirContacto(new Contacto(nombre, telefono));
            mostrarContactos();
        }
        else {
        	JOptionPane.showMessageDialog(ventana,"Introduzca los dos campos");
        }
    }

    private void borrarContacto() {
        int filaSeleccionada = tablaListado.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            controlador.borrarContacto(nombre);
            mostrarContactos();
        } else {
            JOptionPane.showMessageDialog(ventana, "Selecciona un contacto para borrar.");
        }
    }

    private void editarContacto() {
        int filaSeleccionada = tablaListado.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String nombreActual = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String telefonoActual = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

            String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre para " + nombreActual + ":", nombreActual);
            String nuevoTelefono = JOptionPane.showInputDialog("Nuevo teléfono para " + nombreActual + ":", telefonoActual);

            if (nuevoNombre != null && nuevoTelefono != null) {
                controlador.actualizarContacto(nombreActual,nuevoNombre, nuevoTelefono);
                mostrarContactos();
            }
        } else {
            JOptionPane.showMessageDialog(ventana, "Selecciona un contacto para editar.");
        }
    }
    private void mostrarContactos() {
        List<Contacto> contactos = controlador.obtenerContactos();
        actualizaListado(contactos);
    }

    public void actualizaListado(List<Contacto> contactos) {
        modeloTabla.setRowCount(0);

        for (Contacto contacto : contactos) {
            modeloTabla.addRow(new Object[]{contacto.getNombre(), contacto.getTeléfono()});
        }
    }


    public static void main(String[] args) {
        AppAgenda controlador = new AppAgenda();
        new VistaAgenda(controlador);
    }
}
