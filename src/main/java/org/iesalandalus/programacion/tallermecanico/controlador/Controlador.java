package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import java.util.Objects;

public class Controlador implements IControlador {
    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        Objects.requireNonNull(modelo,"El modelo es nulo");
        Objects.requireNonNull(vista,"La vista es nulo");
        this.modelo = modelo;
        this.vista = vista;
        vista.getGestorEventos().suscribir(this, Evento.values());
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
    public void actualizarEvento(Evento evento){
        try {
            String resultado = "";
            switch (evento){
                case INSERTAR_CLIENTE ->{ modelo.insertar(vista.leerCliente()); resultado = "Cliente insertado correctamente." ;}
                case INSERTAR_MECANICO -> { modelo.insertar(vista.leerMecanico()); resultado = "Trabajo de mecánico insertado correctamente." ;}
                case INSERTAR_REVISION -> {modelo.insertar(vista.leerRevision()); resultado = "Trabajo de revisión insertado correctamente." ;}
                case INSERTAR_VEHICULO -> { modelo.insertar(vista.leerVehiculo()); resultado = "Vehiculo insertado correctamente. ";}
                case BUSCAR_CLIENTE ->  vista.mostrarCliente(modelo.buscar(vista.leerClieneDni()));
                case BUSCAR_VEHICULO -> vista.mostrarVehiculos(modelo.buscar(vista.leerMatriculaVehiculo()));
                case BUSCAR_TRABAJO -> vista.mostrarTrabajo(modelo.buscar(vista.leerRevision()));
                case MODIFICAR_CLIENTE -> resultado = (modelo.modificar(vista.leerClieneDni(),vista.leerNuevoNombre(),vista.leerNuevoTelefono())) ? "EL cliente ha sido modificado correctamente.": "EL cliente no ha sido modificado correctamente." ;

                case ANADIR_PRECIO_MATERIAL_TRABAJO -> {
                    modelo.anadirPrecioMaterial(vista.leerMecanico(), vista.leerPrecioMaterial());
                    resultado = "Precio añadido correctamente.";
                }
                case ANADIR_HORAS_TRABAJO -> {
                    modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras());
                    resultado = "Horas añadidas correctamente.";
                }
                case CERRAR_TRABAJO -> {
                    modelo.cerrar(vista.leerTrabajoVehiculo(),vista.leerFechaCierre());
                    resultado = "Trabajo cerrado correctamente.";
                }
                case BORRAR_CLIENTE -> {
                    modelo.borrar(vista.leerClieneDni());
                    resultado  = "Cliente borrado correctamente";
                }
                case BORRAR_TRABAJO ->{
                    modelo.borrar(vista.leerRevision());
                    resultado  = "Trabajo borrado correctamente";
                }
                case BORRAR_VEHICULO -> {
                    modelo.borrar(vista.leerVehiculo());
                    resultado = "Vehiculo borrado correctamente.";
                }
                case LISTAR_CLIENTES -> vista.mostrarClientes(modelo.getClientes());
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());
                case LISTAR_TRABAJOS -> vista.mostrarTrabajos(modelo.getTrabajos());
                case LISTAR_TRABAJOS_CLIENTES -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerClieneDni()));
                case LISTAR_TRABAJOS_VEHICULOS -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerMatriculaVehiculo()));
                case SALIR -> terminar();
            }
            if (!resultado.isBlank()){
                vista.notificarEvento(evento, resultado, true);
            }
        } catch (Exception e) {
            vista.notificarEvento(evento, e.getMessage(), true);
        }
    }
}

