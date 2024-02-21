package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Controlador {
    private Modelo modelo;
    private Vista vista;


    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo,"El modelo es nulo");
        Objects.requireNonNull(vista,"La vista es nulo");
        this.modelo = modelo;
        this.vista = vista;
        vista.setControlador(this);
    }
    public void comenzar() throws OperationNotSupportedException {
        modelo.comenzar();
        /* inicializa cosas*/
        vista.comenzar();
    }

    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }
    public void insertar(Cliente cliente){
        try {
            modelo.insertar(cliente);
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }

    }
    public void insertar(Vehiculo vehiculo){
        try {
            modelo.insertar(vehiculo);
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    public void  insertar(Revision revision){
        try {
            modelo.insertar(revision);
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    public void buscar(Cliente cliente){
        modelo.buscar(cliente);
    }
    public void buscar(Vehiculo vehiculo){
        modelo.buscar(vehiculo);
    }
    public void buscar(Revision revision){
        try {
            modelo.borrar(revision);
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return modelo.modificar(cliente,nombre,telefono);
    }
    public void anadirHoras(Revision revision,int horas){
        try {
            modelo.anadirHoras(revision,horas);
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        modelo.anadirPrecioMaterial(revision,precioMaterial);
    }
    public void cerrar(Revision revision, LocalDate fechaFin){
        try {
            modelo.cerrar(revision, fechaFin);
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        modelo.borrar(cliente);

    }
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        modelo.borrar(vehiculo);
    }
    public void borrar(Revision revision) throws OperationNotSupportedException {
        modelo.borrar(revision);
    }
    public List<Cliente> getClientes(){

        return modelo.getClientes();
    }
    public List<Vehiculo> getVehiculos(){
        return modelo.getVehiculos();
    }
    public List<Revision> getRevisiones(){
        return modelo.getRevisiones();
    }
    public List<Revision> getRevisiones(Cliente cliente){
        return modelo.getRevisiones(cliente);
    }
    public List<Revision> getRevisiones(Vehiculo vehiculo){
        return modelo.getRevisiones(vehiculo);
    }
}