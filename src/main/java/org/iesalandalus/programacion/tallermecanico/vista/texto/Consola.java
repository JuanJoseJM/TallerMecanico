package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Consola {
    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola(){}

    static void mostrarCabecera(String mensaje) {
        Objects.requireNonNull(mensaje);
        System.out.printf("%n%s%n", mensaje);
        System.out.printf(String.format("%s%n", ("-").repeat(mensaje.length())));
    }

    static void mostrarMenu() {
        mostrarCabecera("MENÚ");
        for (Evento evento : Evento.values()) {
            System.out.printf("%s%n", evento);
        }
        System.out.println();
    }

    static int leerEntero(String mensaje) {
        Objects.requireNonNull(mensaje);
        System.out.print(mensaje);
        return Entrada.entero();
    }

    static float leerReal(String mensaje) {
        Objects.requireNonNull(mensaje);
        System.out.print(mensaje);
        return Entrada.real();
    }

    static String leerCadena(String mensaje) {
        Objects.requireNonNull(mensaje);
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    static LocalDate leerFecha(String mensaje) {
        LocalDate fecha = null;
        boolean fechaCorrecta = false;
        do {
            try {
                fecha = LocalDate.parse(leerCadena(mensaje), DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA));
                fechaCorrecta = true;
            } catch (DateTimeParseException ignored){
                System.out.printf("La fecha introducida tiene un formato inválido (dd/MM/yyyy).%n");
            }
        } while (!fechaCorrecta);
        return fecha;
    }

    static Evento elegirOpcion(){
        Evento evento = null;
        do {
            try {
                evento = Evento.get(leerEntero("Elige una opción: "));
            } catch (IllegalArgumentException e) {
                System.out.printf("%s%n%n", e.getMessage());
            }
        } while (evento == null);
        return evento;
    }
}