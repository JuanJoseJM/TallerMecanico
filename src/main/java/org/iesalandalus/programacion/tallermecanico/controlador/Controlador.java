package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;

public class Controlador implements IControlador {
    private final Vista vista;
    private final Modelo modelo;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo, "El modelo no puede ser nulo.");
        Objects.requireNonNull(vista, "La vista no puede ser nula.");
        this.modelo = modelo;
        this.vista = vista;
        this.vista.getGestorEventos().suscribir(this, Evento.values());
    }

    @Override
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    @Override
    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    @Override
    public void actualizar(Evento evento) {
        Objects.requireNonNull(evento, "El evento no puede ser nulo.");
        String resultado = "";
        boolean exito = false;
        try {
            switch (evento) {
                case INSERTAR_CLIENTE -> {
                    modelo.insertar(vista.leerCliente());
                    resultado = "Se ha insertado el cliente correctamente.";
                }
                case BUSCAR_CLIENTE -> vista.mostrarCliente(modelo.buscar(vista.leerClienteDni()));
                case BORRAR_CLIENTE -> {
                    modelo.borrar(vista.leerClienteDni());
                    resultado = "Se ha borrado el cliente.";
                }
                case LISTAR_CLIENTES -> vista.mostrarClientes(modelo.getClientes());

                case MODIFICAR_CLIENTE -> {
                    boolean modificado = modelo.modificar(vista.leerClienteDni(), vista.leerNuevoNombre(), vista.leerNuevoTelefono());
                    if (modificado) {
                        resultado = "Se ha modificado el cliente.";
                    } else {
                        resultado = "El cliente no se ha modificado.";
                    }
                }
                case INSERTAR_VEHICULO -> {
                    modelo.insertar(vista.leerVehiculo());
                    resultado = "Se ha insertado el vehículo correctamente.";
                }
                case BUSCAR_VEHICULO -> vista.mostrarVehiculo(modelo.buscar(vista.leerVehiculoMatricula()));
                case BORRAR_VEHICULO -> {
                    modelo.borrar(vista.leerVehiculoMatricula());
                    resultado = "Se ha borrado el vehiculo.";
                }
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());

                case INSERTAR_REVISION -> {
                    modelo.insertar(vista.leerRevision());
                    resultado = "Se ha insertado la revisión correctamente.";
                }
                case INSERTAR_MECANICO -> {
                    modelo.insertar(vista.leerMecanico());
                    resultado = "Se ha insertado el trabajo mecánico correctamente.";
                }
                case BUSCAR_TRABAJO -> vista.mostrarTrabajo(modelo.buscar(vista.leerRevision()));
                case BORRAR_TRABAJO -> {
                    modelo.borrar(modelo.buscar(vista.leerRevision()));
                    resultado = "Se ha borrado el trabajo.";
                }
                case LISTAR_TRABAJOS -> vista.mostrarTrabajos(modelo.getTrabajos());

                case LISTAR_TRABAJOS_CLIENTE -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerClienteDni()));

                case LISTAR_TRABAJOS_VEHICULO -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerVehiculoMatricula()));

                case ANADIR_HORAS_TRABAJO -> {
                    modelo.anadirHoras((vista.leerTrabajoVehiculo()), vista.leerHoras());
                    resultado = "Se han añadido las horas al trabajo.";
                }
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> {
                    modelo.anadirPrecioMaterial((vista.leerTrabajoVehiculo()), vista.leerPrecioMaterial());
                    resultado = "Se ha añadido el precio material al trabajo.";
                }
                case CERRAR_TRABAJO -> {
                    modelo.cerrar((vista.leerTrabajoVehiculo()), vista.leerFechaCierre());
                    resultado = "Se ha cerrado el trabajo correctamente.";
                }
                case MOSTRAR_ESTADISTICAS_MENSUALES -> vista.mostrarEstadisticasMensuales(modelo.getEstadisticasMensuales(vista.leerMes()));
                case SALIR -> terminar();
            }
            exito = true;
        } catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
            resultado = e.getMessage();
        }
        vista.notificarResultado(evento,resultado,exito);
    }
}