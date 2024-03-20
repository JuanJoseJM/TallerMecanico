package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.FabricaFuenteDatos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModeloCascada implements Modelo {
    private final FabricaFuenteDatos fabricaFuenteDatos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        this.fabricaFuenteDatos = fabricaFuenteDatos;
    }
    private IClientes clientes;
    private IVehiculos vehiculos;
    private ITrabajos trabajos;

    @Override
    public void comenzar() {
        clientes = fabricaFuenteDatos.crear().crearClientes();
        vehiculos = fabricaFuenteDatos.crear().crearVehiculo();
        trabajos = fabricaFuenteDatos.crear().crearTrabajo();
    }

    @Override
    public void terminar() {
        System.out.println("Fin");
    }

    @Override
    public  void insertar(Cliente cliente) throws OperationNotSupportedException {
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public  void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        vehiculos.insertar(vehiculo);
    }

    @Override
    public  void insertar(Trabajo trabajo) throws OperationNotSupportedException {
        Cliente cliente = clientes.buscar(trabajo.getCliente());
        Vehiculo vehiculo = vehiculos.buscar(trabajo.getVehiculo());
        if (trabajo instanceof Mecanico ){
            trabajos.insertar(new Mecanico(cliente,vehiculo,trabajo.getFechaInicio()));
        }
        if (trabajo instanceof Revision ){
            trabajos.insertar(new Revision(cliente,vehiculo,trabajo.getFechaInicio()));        }
    }

    @Override
    public  Cliente buscar(Cliente cliente) {
        cliente = Objects.requireNonNull(clientes.buscar(cliente), "No existe un cliente igual.");
        return new Cliente(cliente);
    }

    @Override
    public  Vehiculo buscar(Vehiculo vehiculo) {
        return vehiculos.buscar(vehiculo);
    }

    @Override
    public  Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajos.buscar(trabajo), "No existe un trabajo igual.");
        return Trabajo.copiar(trabajo);

    }

    @Override
    public  boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return clientes.modificar(cliente, nombre, telefono);
    }

    @Override
    public  void anadirHoras(Trabajo trabajo, int horas) throws OperationNotSupportedException {
        trabajos.anadirHoras(trabajo, horas);
    }

    @Override
    public  void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws OperationNotSupportedException {
        trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    @Override
    public  void cerrar(Trabajo trabajo, LocalDate fechaFin) throws OperationNotSupportedException {
        trabajos.cerrar(trabajo, fechaFin);
    }

    @Override
    public  void borrar(Cliente cliente) throws OperationNotSupportedException {
        for (Trabajo trabajo : trabajos.get(cliente)) {
            trabajos.borrar(trabajo);
        }
        clientes.borrar(cliente);
    }

    @Override
    public  void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public  void borrar(Trabajo trabajo) throws OperationNotSupportedException {
        trabajos.borrar(trabajo);
    }

    @Override
    public  List<Cliente> getClientes() {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            listaClientes.add(new Cliente(cliente));
        }
        return listaClientes;
    }

    @Override
    public  List<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos.get());
    }

    @Override
    public  List<Trabajo> getTrabajos() {
        ArrayList<Trabajo> listaTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            listaTrabajos.add(Trabajo.copiar(trabajo));
        }
        return listaTrabajos;
    }

    @Override
    public  List<Trabajo> getTrabajos(Cliente cliente) {
        ArrayList<Trabajo> listaTrabajosIgualCliente = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)) {
            listaTrabajosIgualCliente.add(Trabajo.copiar(trabajo));
        }

        return listaTrabajosIgualCliente;
    }
    public  List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        ArrayList<Trabajo> listaTrabajosIgualVehiculo = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            listaTrabajosIgualVehiculo.add(Trabajo.copiar(trabajo));
        }
        return listaTrabajosIgualVehiculo;
    }
}