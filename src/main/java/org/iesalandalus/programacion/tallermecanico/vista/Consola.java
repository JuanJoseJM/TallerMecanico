package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    private static String CADENA_FORMATO_FECHA= "\\d{2}/\\d/\\d{4}" ;

    public Consola() {
    }

    public static void mostrarCabecera(String mensaje){
        System.out.println(mensaje);
        String subrayado = String.format(String.format("%%0%dd", mensaje.length()), 0).replace('0', '-');
        System.out.println(subrayado);
    }

    public static void  mostrarMenu(){
        for (Opcion opcion:Opcion.values()) {
            System.out.println(opcion);
        }
    }

    public static Opcion elegirOpcion(){
        int opcion = leerEntero("Elige una opción.");

        if(!Opcion.esValida(opcion )){
            throw new IllegalArgumentException("La opción no es válida");
        }
        return Opcion.get(opcion);
    }

    public static float leerReal(String mensaje){
        System.out.println(mensaje);
        return Entrada.real();
    }

    public static int leerEntero(String mensaje){
        System.out.println(mensaje);
        return Entrada.entero();
    }

    public static String leerCadena(String mensaje){
        System.out.println(mensaje);
        return Entrada.cadena();
    }

    public static LocalDate leerFecha(String mensaje){
        System.out.println(mensaje);
        String fecha = Entrada.cadena();
        return LocalDate.parse(fecha, DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA));
    }

    public static Cliente leerCliente( ){
        return new Cliente(leerNuevoNombre(), leerCadena("Dime el dni del cliente"), leerNuevoTelefono());
    }

    public static Cliente leerClienteDni(){
        return Cliente.get(leerCadena("Dime el dni del cliente"));
    }

    public static String leerNuevoNombre(){
        return leerCadena("Dime el nombre del cliente");
    }

    public static String leerNuevoTelefono(){
        System.out.println("Dime el nuevo telefono del cliente");
        return Entrada.cadena();
    }

    public static Vehiculo leerVehiculo(){
        System.out.println("Dime la marca del vehiculo");
        String marca = Entrada.cadena();
        System.out.println("Dime la matricula del vehiculo");
        String matricula= Entrada.cadena();
        System.out.println("Dime el modelo del vehiculo");
        String modelo = Entrada.cadena();
        return new Vehiculo(marca,modelo,matricula);
    }
    public static Vehiculo leerMatriculaVehiculo(){
        System.out.println("Dime la matricula del vehiculo");
        String matricula= Entrada.cadena();
        return Vehiculo.get(matricula);
    }

    public static Revision leerRevision(){
        return new Revision(leerClienteDni(),leerMatriculaVehiculo(),leerFecha("Introduce la fecha de inicio"));
    }

    public static int leerHoras(){
        return leerEntero("Dime la cantidad de horas");
    }

    public static float leerPrecioMaterial(){
        return leerReal("Dime el precio del material");
    }

    public static LocalDate leerFechaCierre(){
        return leerFecha("Dime la fecha fin de la revisión");
    }
}
