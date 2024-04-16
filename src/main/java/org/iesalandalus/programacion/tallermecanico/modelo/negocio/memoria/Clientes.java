package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes implements IClientes {
    private List<Cliente> coleccionClientes;

    public Clientes() {
        coleccionClientes = new ArrayList<>();
    }

    public List<Cliente> get(){
        return new ArrayList<>(coleccionClientes);
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente,"No se puede insertar un cliente nulo.");
        if (coleccionClientes.contains(cliente)){
            throw new OperationNotSupportedException("Ya existe un cliente con ese DNI.");
        }
        coleccionClientes.add(cliente);
    }

    @Override
    public Cliente buscar(Cliente cliente){
        Objects.requireNonNull(cliente,"No se puede buscar un cliente nulo.");
        int indice = coleccionClientes.indexOf(cliente);
        Cliente clienteComprobar = null;
        if (indice != -1){
            clienteComprobar = coleccionClientes.get(indice);
        }
        return  clienteComprobar;
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente,"No se puede modificar un cliente nulo.");
        if (buscar(cliente)== null){
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }

        boolean  modificado = false;
        int indice = coleccionClientes.indexOf(cliente);

        if ( telefono!= null && !telefono.isBlank()){
            cliente.setTelefono(telefono);
            modificado = true;
        }
        if ( nombre!= null && !nombre.isBlank()){
            cliente.setNombre(nombre);
            modificado  = true;
        }
        coleccionClientes.set(indice,cliente);
        return modificado;
    }

    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente,"No se puede borrar un cliente nulo.");
        if (buscar(cliente)== null){
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
        coleccionClientes.remove(cliente);
    }
}