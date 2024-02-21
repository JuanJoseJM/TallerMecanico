package org.iesalandalus.programacion.tallermecanico.vista;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum  Opcion {
    INSERTAR_CLIENTE("Insertar cliente",1),
    BUSCAR_CLIENTE("Buscar cliente",2),
    BORRAR_CLIENTE("Borra cliente",3),
    LISTAR_CLIENTES("Listar clientes",4),
    MODIFICAR_CLIENTE("Modificar Clientes",5 ),
    INSERTAR_VEHICULO("Insertar vehiculo",6),
    BUSCAR_VEHICULO("Buscar vehiculo",7),
    BORRAR_VEHICULO("Insertar vehiculo",8),
    LISTAR_VEHICULOS("Listar vehiculos",9),
    INSERTAR_REVISION("Insertar revision",10),
    BUSCAR_REVISION("Buscar revision",11),
    BORRAR_REVISION("Borrar revision",12),
    LISTAR_REVISIONES("Listar revisiones",13),
    LISTAR_REVISIONES_CLIENTES("Listar revisiones por clientes",14),
    LISTAR_REVISIONES_VEHICULOS("Listar revisiones por vehiculos",15),
    ANADIR_HORAS_REVISION("Añadir horas revision ",16),
    ANADIR_PRECIO_MATERIAL_REVISON("Añadir precio material revision ",17),
    CERRAR_REVISION("Cerrar revision",18),
    SALIR("Salir",19);
    private  String nombre;
    private  Integer numeroOpcion;
    private static final Map<Integer, Opcion> opciones = new HashMap<>();
    static {
        for (Opcion opcion:values()) {
            opciones.put(opcion.numeroOpcion, opcion);
        }
    }
    Opcion(String nombre, Integer opcion) {
        this.nombre = nombre;
        this.numeroOpcion = opcion;
    }
    public static boolean esValida(int numeroOpcion) {
        Objects.requireNonNull(numeroOpcion,"El numero no es valido");
        return opciones.containsKey(numeroOpcion);
    }

    public static Opcion get(int numeroOpcion) {
        if (!esValida(numeroOpcion)) {
            throw new IllegalArgumentException("Opción inválida");
        }
        return opciones.get(numeroOpcion);
    }

    @Override
    public String toString() {
        return String.format("Opcion[nombre=%s, numeroOpcion=%s]", this.nombre, this.numeroOpcion);
    }
}
