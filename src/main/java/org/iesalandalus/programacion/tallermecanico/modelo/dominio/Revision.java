package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Revision {
    private static final float PRECIO_HORA = 30.00f;
    private static final float PRECIO_DIA = 10.0f;
    private static final float PRECIO_MATERIAL = 1.5f;
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private float precioMaterial;

    private Cliente cliente;
    private Vehiculo vehiculo;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }

    public Revision(Revision revision) {
        Objects.requireNonNull(revision, "La revisión no puede ser nula.");
        this.cliente = new Cliente(revision.getCliente());
        this.vehiculo = revision.getVehiculo();
        this.fechaInicio = revision.getFechaInicio();
        this.fechaFin = revision.getFechaFin();
        this.horas = revision.getHoras();
        this.precioMaterial = revision.getPrecioMaterial();
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public int getHoras() {
        return horas;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
        this.fechaInicio = fechaInicio;
    }

    private void setFechaFin(LocalDate fechaFin) {
        Objects.requireNonNull(fechaFin, "La fecha final no puede ser nula.");
        if (fechaFin.equals(fechaInicio) || fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("ERROR:");
        }
        if (fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        this.fechaFin = fechaFin;
    }

    private void setCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    private void setVehiculo(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        this.vehiculo = vehiculo;
    }

    public void anadirHoras(int horas) throws OperationNotSupportedException {
        if (estaCerrada()) {
            throw new OperationNotSupportedException("No se puede añadir horas, ya que la revisión está cerrada.");
        }

        Objects.requireNonNull(horas, "Las horas no pueden ser nulas.");
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }

        this.horas += horas;
    }


    public void anadirPrecioMaterial(float precioMaterial) throws OperationNotSupportedException {
        if (estaCerrada()) {
            throw new OperationNotSupportedException("No se puede añadir precio del material, ya que la revisión está cerrada.");
        }

        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }

        this.precioMaterial += precioMaterial;
    }


    public boolean estaCerrada() {
        return fechaFin != null;
    }

    public void cerrar(LocalDate fechaFin) throws OperationNotSupportedException {
        if (estaCerrada()) {
            throw new OperationNotSupportedException("La revisión ya está cerrada.");
        }

        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        if (fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }

        this.fechaFin = fechaFin;
    }

    public float getPrecio() {
        float precioHoras = horas * PRECIO_HORA;
        float precioDias = getDias() * PRECIO_DIA;
        return precioHoras + precioDias + precioMaterial*PRECIO_MATERIAL;

    }

    private float getDias() {
        float dias = 0;
        if (fechaFin != null) {
            dias= (float) fechaInicio.until(fechaFin).getDays();
        }
        return dias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revision revision = (Revision) o;
        return horas == revision.horas && Float.compare(precioMaterial, revision.precioMaterial) == 0 && Objects.equals(fechaInicio, revision.fechaInicio) && Objects.equals(cliente, revision.cliente) && Objects.equals(vehiculo, revision.vehiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, horas, precioMaterial, cliente, vehiculo);
    }

    @Override
    public String toString() {
        String clienteString = cliente.getNombre() + " - " + cliente.getDni() + " (" + cliente.getTelefono() + ")";
        String vehiculoString = vehiculo.marca() + " " + vehiculo.modelo() + " - " + vehiculo.matricula();
        String fechaInicioString = fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String fechaFinString = fechaFin != null ? fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";

        String cadena = String.format("%s - %s: (%s - %s), %d horas, %.2f € en material", clienteString, vehiculoString, fechaInicioString, fechaFinString, horas, precioMaterial);

        if (fechaFin != null) {
            float precioTotal = getPrecio();
            cadena += String.format(", %.2f € total", precioTotal);
        }

        return cadena;
    }
}