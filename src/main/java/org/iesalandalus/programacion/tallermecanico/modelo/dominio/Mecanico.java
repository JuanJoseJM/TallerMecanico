package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;

public class Mecanico extends Trabajo {

    private static final float FACTOR_HORA = 30F;
    private static final float FACTOR_PRECIO_MATERIAL = 1.5F;

    protected float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
    }

    public Mecanico(Mecanico mecanico) {
        super(mecanico);
        this.precioMaterial = mecanico.precioMaterial;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) throws OperationNotSupportedException {
        if (estaCerrado()) {
            throw new OperationNotSupportedException("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        }
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        this.precioMaterial += precioMaterial;
    }

    public float getPrecioEspecifico() {
        return (horas * FACTOR_HORA) + (precioMaterial * FACTOR_PRECIO_MATERIAL);
    }

    @Override
    public String toString() {
        if (estaCerrado()) {
            return String.format("Mecánico -> %s - %s (%s - %s): %s horas, %.2f € en material, %.2f € total", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), this.fechaFin.format(FORMATO_FECHA), this.horas, this.precioMaterial, getPrecio());
        } else {
            return String.format("Mecánico -> %s - %s (%s - ): %s horas, %.2f € en material", this.cliente, this.vehiculo, this.fechaInicio.format(FORMATO_FECHA), this.horas, this.precioMaterial);
        }
    }
}