package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Revisiones;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Vehiculos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Modelo {
    private Clientes clientes;
    private Vehiculos vehiculos;
    private Revisiones revisiones;

    public void comenzar() {
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        revisiones = new Revisiones();
    }
    public void terminar(){
        System.out.println("Fin");
    }
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        clientes.insertar(new Cliente(cliente));
    }
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        vehiculos.insertar(vehiculo);
    }
    public void insertar(Revision revision) throws OperationNotSupportedException {
        Cliente cliente = clientes.buscar(revision.getCliente());
        Vehiculo vehiculo = vehiculos.buscar(revision.getVehiculo());
        revisiones.insertar(new Revision(cliente,vehiculo,revision.getFechaInicio()));
    }
    public Cliente buscar(Cliente cliente){
       // return new clientes.buscar(cliente);
        cliente = Objects.requireNonNull(clientes.buscar(cliente));
        return new Cliente(cliente);
    }
    public Vehiculo buscar(Vehiculo vehiculo){
        return vehiculos.buscar(vehiculo);
    }
    public Revision buscar(Revision revision){
        return revisiones.buscar(revision);

    }
    public boolean modificar(Cliente cliente,String nombre, String telefono) throws OperationNotSupportedException {
        return clientes.modificar(cliente,nombre,telefono);
    }
    public void anadirHoras(Revision revision, int horas) throws OperationNotSupportedException {
        revisiones.anadirHoras(revision,horas);
    }
    public void anadirPrecioMaterial(Revision revision, float precioMaterial) throws OperationNotSupportedException {
        revisiones.anadirPrecioMaterial(revision,precioMaterial);
    }
    public void cerrar(Revision revision, LocalDate fechaFin) throws OperationNotSupportedException {
        revisiones.cerrar(revision,fechaFin);
    }
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        for (Revision revision: revisiones.get(cliente)){
            revisiones.borrar(revision);
        }
        clientes.borrar(cliente);
    }
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        for (Revision revision: revisiones.get(vehiculo)){
            revisiones.borrar(revision);
        }
        vehiculos.borrar(vehiculo);
    }
    public void borrar(Revision revision) throws OperationNotSupportedException {
        revisiones.borrar(revision);
    }
    public List<Cliente> getClientes(){
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            listaClientes.add(new Cliente(cliente));
        }
        return listaClientes;
    }
    public List<Vehiculo> getVehiculos(){
        return new ArrayList<>(vehiculos.get());
    }


    public List<Revision> getRevisiones(){
        ArrayList<Revision> listaRevisones = new ArrayList<>();
        for (Revision revision : revisiones.get()) {
            listaRevisones.add(new Revision (revision));
        }
        return listaRevisones;
    }
    public List<Revision> getRevisiones(Cliente cliente){
        ArrayList<Revision> listaRevisonesIgualCliente = new ArrayList<>();
        for (Revision revision : revisiones.get(cliente)) {
            listaRevisonesIgualCliente.add(new Revision (revision));
        }

        return listaRevisonesIgualCliente;
    }
    public List<Revision> getRevisiones(Vehiculo vehiculo){
        ArrayList<Revision> listaRevisonesIgualVehiculo = new ArrayList<>();
        for (Revision revision : revisiones.get(vehiculo)) {
            listaRevisonesIgualVehiculo.add(new Revision (revision));
        }

        return listaRevisonesIgualVehiculo;
    }
}