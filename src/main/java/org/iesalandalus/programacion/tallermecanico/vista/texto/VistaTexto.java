package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class VistaTexto implements Vista {
    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());

    @Override
    public LocalDate leerFechaCierre() {
        return Consola.leerFecha("Dime la fecha fin de la revisión");
    }

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    @Override
    public void comenzar() {
        Evento aux;
        Consola.mostrarCabecera("------------Taller Mecánico------------");
        do {
            Consola.mostrarMenu();
            aux = Consola.elegirOpcion();
            ejecutar(aux);
        } while (aux != Evento.SALIR);
    }

    @Override
    public void terminar() {
        System.out.println("Proceso terminado; ¡Hasta la próxima!");
    }

    private void ejecutar(Evento evento) {
        Consola.mostrarCabecera("---Taller Mecánico---");
        gestorEventos.notificar(evento);
    }

    @Override
    public Cliente leerCliente() {

        return new Cliente(leerNuevoNombre(), Consola.leerCadena("Dime el dni del cliente"), leerNuevoTelefono());
    }

    @Override
    public Cliente leerClieneDni() {
        return Cliente.get(Consola.leerCadena("Dime el dni del cliente"));
    }

    @Override
    public String leerNuevoNombre() {
        return Consola.leerCadena("Dime el nombre del cliente");
    }

    @Override
    public String leerNuevoTelefono() {
        System.out.println("Dime el nuevo telefono del cliente");
        return Entrada.cadena();
    }

    @Override
    public Vehiculo leerVehiculo() {
        System.out.println("Dime la marca del vehiculo");
        String marca = Entrada.cadena();
        System.out.println("Dime la matricula del vehiculo");
        String matricula = Entrada.cadena();
        System.out.println("Dime el modelo del vehiculo");
        String modelo = Entrada.cadena();
        return new Vehiculo(marca, modelo, matricula);
    }

    @Override
    public Vehiculo leerMatriculaVehiculo() {
        System.out.println("Dime la matricula del vehiculo");
        String matricula = Entrada.cadena();
        return Vehiculo.get(matricula);
    }

    @Override
    public Trabajo leerRevision() {
        System.out.print("Dime una revisión");
        return new Revision(leerClieneDni(), leerMatriculaVehiculo(), Consola.leerFecha("Introduce la fecha de inicio"));
    }

    @Override
    public Trabajo leerMecanico() {
        System.out.print("Dime un trabajo mecánico ");
        return new Mecanico(leerClieneDni(), leerMatriculaVehiculo(), Consola.leerFecha("Introduce la fecha de inicio"));
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        System.out.println("Dime un trabajo de un vehiculo");
        return new Mecanico(leerClieneDni(), leerVehiculo(), Consola.leerFecha("Dime la fecha de inciio."));
    }

    @Override
    public int leerHoras() {
        return Consola.leerEntero("Dime la cantidad de horas");
    }

    @Override
    public float leerPrecioMaterial() {
        return Consola.leerReal("Dime el precio material");
    }

    @Override
    public void notificarEvento(Evento evento, String texto, boolean existo) {
        if (existo) {
            System.out.println(texto);
        } else {
            System.out.println(evento + "ERROR:" + texto);
        }
    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        System.out.println(cliente);
    }

    @Override
    public void mostrarVehiculos(Vehiculo vehiculo) {
        System.out.println(vehiculo);
    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {
        System.out.println(trabajo);
    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            mostrarCliente(cliente);
        }
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        for (Vehiculo vehiculo : vehiculos) {
            mostrarVehiculos(vehiculo);
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        for (Trabajo trabajo : trabajos) {
            System.out.println(trabajo);
        }
    }

    @Override
    public LocalDate leerMes(){
        int mes = Consola.leerEntero("Introduzca el mes");
        int ano = Consola.leerEntero("Introduzca el año");
        //LocalDate.of(2021, 1, 1)
        return LocalDate.of(ano, mes, 1);
    }

    public void mostrarEstadisticasMensales(Map<TipoTrabajo, Integer> estadisticas) {
        System.out.println("Estadísticas Mensuales");
        for (Map.Entry<TipoTrabajo, Integer> it : estadisticas.entrySet()) {
            System.out.println(it.getKey() + " -> " + it.getValue());
        }
    }
}