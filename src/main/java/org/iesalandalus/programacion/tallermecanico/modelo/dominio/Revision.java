package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

public class Revision extends Trabajo {
    private static final float FACTOR_HORA = 35.0f;


    public Revision(Cliente cliente, Vehiculo vehiculo,LocalDate fechaInicio ) {
        super(fechaInicio, vehiculo, cliente);
    }

    @Override
    public float getPrecioEspecifico() {
        return getHoras()*FACTOR_HORA;
    }


    public Revision(Revision revision) {
        super(revision);

    }
    public String toString() {
        String cadena;
        String fechaI = this.fechaInicio.format(FORMATO_FECHA);
        String fechaF = (this.fechaFin != null) ? this.fechaFin.format(FORMATO_FECHA) :"";
        if (!estaCerrado()){
            cadena = String.format("Revisión -> %s - %s (%s - %s): %s horas", this.getCliente(), this.getVehiculo(), fechaI, fechaF, this.horas);
        } else {
            cadena = String.format("Revisión -> %s - %s (%s - %s): %s horas, %.2f € total", this.getCliente(), this.getVehiculo(), fechaI, fechaF, this.horas, getPrecio());
        }
        return cadena;
    }
}