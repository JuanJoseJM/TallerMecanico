package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface ITrabajos {
    List<Trabajo> get();
    List<Trabajo> get(Cliente cliente);
    List<Trabajo> get(Vehiculo vehiculo);
    Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes);

    void insertar(Trabajo trabajo) ;

    void anadirHoras(Trabajo trabajo, int horas) ;

    void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial)  ;

    void cerrar(Trabajo trabajo, LocalDate fechaFin) ;

    Trabajo buscar(Trabajo trabajo);

    void borrar(Trabajo trabajo) throws OperationNotSupportedException;
}