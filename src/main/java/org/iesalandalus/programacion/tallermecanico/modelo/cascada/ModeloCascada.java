package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.*;

public class ModeloCascada implements Modelo {
    private IClientes clientes;
    private ITrabajos trabajos;
    private IVehiculos vehiculos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        clientes = fuenteDatos.crearClientes();
        trabajos = fuenteDatos.crearTrabajos();
        vehiculos = fuenteDatos.crearVehiculos();
    }
    @Override
    public void comenzar() {
        clientes.comenzar();
        vehiculos.comenzar();
        trabajos.comenzar();
        System.out.printf("El modelo ha comenzado.%n");
    }

    @Override
    public void terminar() {
        clientes.terminar();
        vehiculos.terminar();
        trabajos.terminar();
        System.out.printf("El modelo ha terminado.%n");
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws OperationNotSupportedException {
        if (trabajo instanceof Revision revision) {
            trabajos.insertar(new Revision(clientes.buscar(revision.getCliente()), vehiculos.buscar(revision.getVehiculo()), revision.getFechaInicio()));
        } else if (trabajo instanceof Mecanico mecanico) {
            trabajos.insertar(new Mecanico(clientes.buscar(mecanico.getCliente()), vehiculos.buscar(mecanico.getVehiculo()), mecanico.getFechaInicio()));
        }
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Cliente clienteEncontrado = Objects.requireNonNull(clientes.buscar(cliente), "El cliente no se encuentra en la lista.");
        return new Cliente(clienteEncontrado);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        return Objects.requireNonNull(vehiculos.buscar(vehiculo), "El vehiculo no se encuentra en la lista.");
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        trabajo = Objects.requireNonNull(trabajos.buscar(trabajo), "El trabajo no se encuentra en la lista.");
        return Trabajo.copiar(trabajo);
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        return clientes.modificar(cliente, nombre, telefono);
    }

    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws OperationNotSupportedException {
        trabajos.anadirHoras(trabajo, horas);
    }
    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws OperationNotSupportedException {
        trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws OperationNotSupportedException {
        trabajos.cerrar(trabajo, fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        for (Trabajo trabajo : trabajos.get(cliente)) {
            borrar(trabajo);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws OperationNotSupportedException {
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> nuevosClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            nuevosClientes.add(new Cliente(cliente));
        }
        return nuevosClientes;
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos.get());
    }

    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> nuevosTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get()) {
            if (trabajo instanceof Revision revision) {
                nuevosTrabajos.add(new Revision(revision));
            } else if (trabajo instanceof Mecanico mecanico) {
                nuevosTrabajos.add(new Mecanico(mecanico));
            }
        }
        return nuevosTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        List<Trabajo> nuevosTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)) {
            if (trabajo instanceof Revision revision) {
                nuevosTrabajos.add(new Revision(revision));
            } else if (trabajo instanceof Mecanico mecanico) {
                nuevosTrabajos.add(new Mecanico(mecanico));
            }
        }
        return nuevosTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> nuevosTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)) {
            if (trabajo instanceof Revision revision) {
                nuevosTrabajos.add(new Revision(revision));
            } else if (trabajo instanceof Mecanico mecanico) {
                nuevosTrabajos.add(new Mecanico(mecanico));
            }
        }
        return nuevosTrabajos;
    }

    @Override
    public Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes) {
        return trabajos.getEstadisticasMensuales(mes);
    }
}