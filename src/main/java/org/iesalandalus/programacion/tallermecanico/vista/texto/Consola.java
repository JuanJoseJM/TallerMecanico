package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    private static final String  CADENA_FORMATO_FECHA= "dd/MM/yyyy" ;

    public Consola(){}
    public static void mostrarCabecera(String mensaje){
        System.out.printf("%n%s%n", mensaje);
        String subrayado = String.format(String.format("%%0%dd", mensaje.length()), 0).replace('0', '-');
        System.out.println(subrayado);
    }

    public static void  mostrarMenu(){
        for (Evento opcion: Evento.values()) {
            System.out.println(opcion);
        }
    }

    public static Evento elegirOpcion(){
        int opcion = leerEntero("Dime la opcion que quieres elegir.");
        if(!Evento.esValida(opcion )){
            throw new IllegalArgumentException("La opcion no es válida");
        }
        return Evento.get(opcion);
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
}