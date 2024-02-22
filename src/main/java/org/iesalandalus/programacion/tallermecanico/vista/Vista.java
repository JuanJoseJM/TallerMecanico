package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;

public class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        Objects.requireNonNull(controlador,"EL controlador no puede ser nulo");
        this.controlador = controlador;
    }

    public  void comenzar() throws OperationNotSupportedException {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        }while (opcion != Opcion.SALIR);
    }

    public  void terminar(){
        System.out.println("Fin");
    }

    private void ejecutar(Opcion opcion) throws OperationNotSupportedException {
        switch (opcion){
            case BORRAR_CLIENTE -> borrarCliente();
            case SALIR -> salir();
            case BUSCAR_CLIENTE -> buscarCliente();
            case BORRAR_REVISION -> borrarRevision();
            case BORRAR_VEHICULO -> borrarVehiculo();
            case BUSCAR_REVISION -> buscarRevision();
            case BUSCAR_VEHICULO -> buscarVehiculo();
            case CERRAR_REVISION -> cerrarRevision();
            case LISTAR_CLIENTES -> listarClientes();
            case INSERTAR_CLIENTE -> insertarCliente();
            case LISTAR_VEHICULOS -> listarVehiculo();
            case INSERTAR_REVISION -> insertarRevision();
            case INSERTAR_VEHICULO -> insertarVehiculo();
            case LISTAR_REVISIONES -> listarRevision();
            case MODIFICAR_CLIENTE -> modificarCliente();
            case ANADIR_HORAS_REVISION -> anadirHoras();
            case LISTAR_REVISIONES_CLIENTES -> listarRevisionesCliente();
            case LISTAR_REVISIONES_VEHICULOS -> listarRevisionesVehiculo();
            case ANADIR_PRECIO_MATERIAL_REVISON -> anadirPrecioMaterial();
        }
    }

    private void cerrarRevision(){
        controlador.cerrar(Consola.leerRevision(),Consola.leerFechaCierre());
    }

    private void salir(){
        controlador.terminar();
    }

    private void insertarCliente(){
        controlador.insertar(Consola.leerCliente());
    }

    private void insertarVehiculo(){
        controlador.insertar(Consola.leerVehiculo());
    }

    private void insertarRevision(){
        controlador.insertar(Consola.leerRevision());
    }

    private void borrarCliente() throws OperationNotSupportedException {
        controlador.borrar(Consola.leerCliente());
    }

    private void borrarVehiculo() throws OperationNotSupportedException {
        controlador.borrar(Consola.leerVehiculo());
    }

    private void borrarRevision() throws OperationNotSupportedException {
        controlador.borrar(Consola.leerRevision());
    }

    private void buscarCliente(){
        controlador.buscar(Consola.leerCliente());
    }

    private void buscarVehiculo(){
        controlador.buscar(Consola.leerVehiculo());
    }

    private void buscarRevision(){
        controlador.buscar(Consola.leerRevision());
    }

    private void listarClientes(){
        controlador.getClientes();
    }

    private void listarVehiculo(){
        controlador.getVehiculos();
    }

    private void listarRevision(){
        controlador.getRevisiones();
    }

    private void  modificarCliente() throws OperationNotSupportedException {
        controlador.modificar(Consola.leerClienteDni(),Consola.leerNuevoNombre(),Consola.leerNuevoTelefono());
    }

    private void anadirHoras(){
        controlador.anadirHoras(Consola.leerRevision(),Consola.leerHoras());
    }

    private void anadirPrecioMaterial() throws OperationNotSupportedException {
        controlador.anadirPrecioMaterial(Consola.leerRevision(),Consola.leerPrecioMaterial());
    }

    public void listarRevisionesCliente(){
        controlador.getRevisiones(Consola.leerCliente());
    }

    public void listarRevisionesVehiculo(){
        controlador.getRevisiones(Consola.leerVehiculo());
    }

}