package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

public class Revision extends Trabajo {

    private static final float FACTOR_HORA = 35F;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente,vehiculo,fechaInicio);
    }

    public Revision(Revision revision) {
        super(revision);
    }

    public float getPrecioEspecifico() {
        return horas * FACTOR_HORA;
    }

    @Override
    public String toString() {
        if (estaCerrado()) {
            return String.format("Revisión -> %s - %s (%s - %s): %s horas, %.2f € total", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), this.fechaFin.format(FORMATO_FECHA), this.horas, getPrecio());
        } else {
            return String.format("Revisión -> %s - %s (%s - ): %s horas", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), this.horas);
        }
    }
}