package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VistaTexto implements org.iesalandalus.programacion.tallermecanico.vista.Vista {
    public static final String DNI_EJEMPLO = "11111111H";
    public static final String MATRICULA_DEFECTO = "1111JKK";
    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    @Override
    public void comenzar() {
        Evento evento;
        do {
            Consola.mostrarMenu();
            evento = Consola.elegirOpcion();
            Consola.mostrarCabecera(evento.name());
            ejecutar(evento);
        } while (evento != Evento.SALIR);
    }

    @Override
    public void terminar() {
        System.out.printf("¡HASTA PRONTO!%n");
    }

    private void ejecutar(Evento opcion) {
        getGestorEventos().notificar(opcion);
    }

    @Override
    public Cliente leerCliente() {
        return new Cliente(Consola.leerCadena("Dime el nombre del cliente: "), Consola.leerCadena("Dime el dni del cliente: "), Consola.leerCadena("Dime el teléfono del cliente: "));
    }

    @Override
    public Cliente leerClienteDni() {
        return new Cliente(Cliente.get(Consola.leerCadena("Dime el DNI del cliente: ")));
    }

    @Override
    public String leerNuevoNombre() {
        String nombre;
        nombre = Consola.leerCadena("Dime el nuevo nombre del cliente: ");
        if (!nombre.isBlank()) {
            new Cliente(nombre, VistaTexto.DNI_EJEMPLO, "600600600");
        }
        return nombre;
    }

    @Override
    public String leerNuevoTelefono() {
        String telefono;
        telefono = Consola.leerCadena("Dime el nuevo telefono del cliente: ");
        if (!telefono.isBlank()) {
            new Cliente("Juan", VistaTexto.DNI_EJEMPLO, telefono);
        }
        return telefono;
    }

    @Override
    public Vehiculo leerVehiculo() {
        return new Vehiculo(Consola.leerCadena("Dime la marca del vehículo: "), Consola.leerCadena("Dime el modelo del vehículo: "), Consola.leerCadena("Dime la matrícula del vehículo: "));
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        return Vehiculo.get(Consola.leerCadena("Dime la matrícula del vehículo: "));
    }

    @Override
    public Trabajo leerRevision() {
        return new Revision(leerClienteDni(), leerVehiculoMatricula(), Consola.leerFecha("Dime la fecha de inicio del trabajo: "));
    }

    @Override
    public Trabajo leerMecanico() {
        return new Mecanico(leerClienteDni(), leerVehiculoMatricula(), Consola.leerFecha("Dime la fecha de inicio del trabajo: "));
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        return Trabajo.get(leerVehiculoMatricula());
    }

    @Override
    public int leerHoras() throws OperationNotSupportedException {
        int horas = Consola.leerEntero("Dime las horas que quieres añadir: ");
        Revision revision = new Revision(Cliente.get(VistaTexto.DNI_EJEMPLO), Vehiculo.get(VistaTexto.MATRICULA_DEFECTO), LocalDate.now());
        revision.anadirHoras(horas);
        return horas;
    }

    @Override
    public float leerPrecioMaterial() throws OperationNotSupportedException {
        float precioMaterial = Consola.leerReal("Dime el precio que quieres añadir: ");
        Mecanico mecanico = new Mecanico(Cliente.get(VistaTexto.DNI_EJEMPLO), Vehiculo.get(VistaTexto.MATRICULA_DEFECTO), LocalDate.now());
        mecanico.anadirPrecioMaterial(precioMaterial);
        return precioMaterial;
    }

    @Override
    public LocalDate leerFechaCierre() throws OperationNotSupportedException {
        LocalDate fechaCierre = Consola.leerFecha("Dime la fecha de cierre: ");
        Revision revision = new Revision(Cliente.get(VistaTexto.DNI_EJEMPLO), Vehiculo.get(VistaTexto.MATRICULA_DEFECTO), LocalDate.of(1900, 1, 1));
        revision.cerrar(fechaCierre);
        return fechaCierre;
    }

    public LocalDate leerMes() {
        return Consola.leerFecha("Dime una fecha para comprobar su mes: ");
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito){
        if (!texto.isBlank()) {
            Consola.mostrarCabecera(evento.name());
            if (exito) {
                System.out.printf("%s%n", texto);
            } else {
                System.out.printf("ERROR: %s%n", texto);
            }
        }
    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        System.out.printf("%n%s%n", cliente);
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehiculo no puede ser nulo.");
        System.out.printf("%n%s%n", vehiculo);
    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El vehiculo no puede ser nulo.");
        System.out.printf("%n%s%n", trabajo);
    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        Objects.requireNonNull(clientes, "Los clientes no pueden ser nulos.");
        if (!clientes.isEmpty()) {
            for (Cliente cliente: clientes) {
                System.out.printf("%s%n", cliente);
            }
        } else {
            System.out.printf("No existe ningún cliente.%n");
        }
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        Objects.requireNonNull(vehiculos, "Los vehículos no pueden ser nulos.");
        if (!vehiculos.isEmpty()) {
            for (Vehiculo vehiculo: vehiculos) {
                System.out.printf("%s%n", vehiculo);
            }
        } else {
            System.out.printf("No existe ningún vehículo.%n");
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        Objects.requireNonNull(trabajos, "Los trabajos no pueden ser nulos.");
        if (!trabajos.isEmpty()) {
            for (Trabajo trabajo: trabajos) {
                System.out.printf("%s%n", trabajo);
            }
        } else {
            System.out.printf("No existe ningún trabajo.%n");
        }
    }
    @Override
    public void mostrarEstadisticasMensuales(Map<TipoTrabajo, Integer> estadisticas) {
        Objects.requireNonNull(estadisticas, "La estadística no puede ser nula.");
        System.out.printf("%nLas estadísticas para este mes son:%n");
        for (TipoTrabajo tipoEstadistica : estadisticas.keySet()) {
            System.out.printf("%s: %s%n", tipoEstadistica.nombre, estadisticas.get(tipoEstadistica));
        }
    }
}