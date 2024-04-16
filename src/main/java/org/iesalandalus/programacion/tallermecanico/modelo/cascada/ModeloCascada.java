package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.FabricaFuenteDatos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
        List<Cliente> copiaClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copiaClientes.add(new Cliente(cliente));
        }
        return copiaClientes.stream().sorted(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni)).collect(Collectors.toList());
    }

    @Override
    public  List<Vehiculo> getVehiculos() {
        return vehiculos.get().stream().sorted(Comparator.comparing(Vehiculo::marca).thenComparing(Vehiculo::modelo).thenComparing(Vehiculo::matricula)).collect(Collectors.toList());
    }

    @Override
    public  List<Trabajo> getTrabajos() {
        ArrayList<Trabajo> copiaTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            copiaTrabajos.add(Trabajo.copiar(trabajo));
        }
        return copiaTrabajos.stream().sorted(Comparator.comparing(Trabajo::getFechaInicio).thenComparing(trabajo -> trabajo.getCliente().getNombre()).thenComparing(trabajo -> trabajo.getCliente().getDni())).collect(Collectors.toList());
    }

    @Override
    public  List<Trabajo> getTrabajos(Cliente cliente) {
        ArrayList<Trabajo> listaTrabajosIgualCliente = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)) {
            listaTrabajosIgualCliente.add(Trabajo.copiar(trabajo));
        }

        return listaTrabajosIgualCliente;
    }

    @Override
    public  List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        ArrayList<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            trabajosCliente.add(Trabajo.copiar(trabajo));
        }
        return trabajosCliente;
    }

    public Map<TipoTrabajo, Integer> getEstadisticasMensales(LocalDate mes) {
        return trabajos.getEstadisticasMensuales(mes);
    }
}