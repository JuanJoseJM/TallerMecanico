package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Trabajo {
    private static final float FACTOR_DIA = 10.0f;
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    protected LocalDate fechaInicio;
    protected LocalDate fechaFin;
    protected int horas;
    private Vehiculo vehiculo;
    private Cliente cliente;

    protected Trabajo(LocalDate fechaInicio, Vehiculo vehiculo, Cliente cliente) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }
    protected Trabajo(Trabajo trabajo){
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        this.cliente = new Cliente(trabajo.getCliente());
        this.vehiculo = trabajo.getVehiculo();
        this.fechaInicio = trabajo.getFechaInicio();
        this.fechaFin = trabajo.getFechaFin();
        this.horas = trabajo.getHoras();

    }
    public static Trabajo copiar(Trabajo trabajo){
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        Trabajo trabajoCopiado = null;
        if (trabajo instanceof Mecanico mecanico){
            trabajoCopiado = new Mecanico(mecanico);
        }
        if (trabajo instanceof Revision revision ){
            trabajoCopiado = new Revision(revision);
        }
        return trabajoCopiado;
    }
    public static Trabajo get(Vehiculo vehiculo){
        return new Mecanico(new Cliente("Julia Lopez", "77653531G", "123456789"),vehiculo, LocalDate.now()){
        } ;
    }
    public Vehiculo getVehiculo() {
        return vehiculo;
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

    public Cliente getCliente() {
        return cliente;
    }

    protected void setFechaInicio(LocalDate fechaInicio) {
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

    protected void setCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    protected void setVehiculo(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        this.vehiculo = vehiculo;
    }

    public void anadirHoras(int horas){
        if (estaCerrado()) {
            try {
                throw new OperationNotSupportedException("No se puede añadir horas, ya que el trabajo está cerrado.");
            } catch (OperationNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }

        this.horas += horas;
    }

    public boolean estaCerrado() {
        return fechaFin != null;
    }

    public void cerrar(LocalDate fechaFin)  {
        if (estaCerrado()) {
            try {
                throw new OperationNotSupportedException("El trabajo ya está cerrado.");
            } catch (OperationNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");

        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        setFechaFin(fechaFin);

        this.fechaFin = fechaFin;
    }

    protected float getDias() {
        float dias = 0;
        if (fechaFin != null) {
            dias = fechaInicio.until(fechaFin).getDays();
        }
        return dias;
    }
    public float getPrecio() {
        return getPrecioEspecifico() +getPrecioFijo();
    }

    public abstract float getPrecioEspecifico();

    private float getPrecioFijo(){
        return  getDias() * FACTOR_DIA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trabajo trabajo)) return false;
        return Objects.equals(fechaInicio, trabajo.fechaInicio) && Objects.equals(vehiculo, trabajo.vehiculo) && Objects.equals(cliente, trabajo.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, vehiculo, cliente);
    }
}