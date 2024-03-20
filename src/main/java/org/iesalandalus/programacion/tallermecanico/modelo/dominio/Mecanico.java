package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Mecanico extends Trabajo {
    static final float FACTOR_HORA = 30.0f;
    static final float FACTOR_MATERIAL = 1.5f;
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(fechaInicio, vehiculo, cliente);
    }

    public Mecanico(Mecanico mecanico) {
        super(mecanico);
        this.precioMaterial = mecanico.getPrecioMaterial();
    }


    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) {
        if (estaCerrado()) {
            try {
                throw new OperationNotSupportedException("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
            } catch (OperationNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }

        this.precioMaterial += precioMaterial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mecanico mecanico)) return false;
        if (!super.equals(o)) return false;
        return Float.compare(precioMaterial, mecanico.precioMaterial) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), precioMaterial);
    }

    @Override
    public float getPrecioEspecifico() {
        return getHoras()*FACTOR_HORA + precioMaterial*FACTOR_MATERIAL;
    }

    @Override
    public String toString() {
        String cadena;
        String fechaI = this.fechaInicio.format(FORMATO_FECHA);
        String fechaF = (this.fechaFin != null) ? this.fechaFin.format(FORMATO_FECHA) :"";
        if (!estaCerrado()){
            cadena = String.format("Mecánico -> %s - %s (%s - %s): %s horas, %.2f € en material", this.getCliente(), this.getVehiculo(), fechaI, fechaF, this.horas, this.precioMaterial);
        } else {
            cadena = String.format("Mecánico -> %s - %s (%s - %s): %s horas, %.2f € en material, %.2f € total", this.getCliente(), this.getVehiculo(), fechaI, fechaF, this.horas, this.precioMaterial, getPrecio());
        }
        return cadena;
    }
}