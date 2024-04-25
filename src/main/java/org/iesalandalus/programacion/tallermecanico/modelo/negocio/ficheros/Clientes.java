package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes implements IClientes {

    private static final String FICHERO_CLIENTES = String.format("%s%s%s", "datos", File.separator, "clientes.xml");
    private static final String RAIZ = "clientes";
    private static final String CLIENTE = "cliente";
    private static final String NOMBRE = "nombre";
    private static final String DNI = "dni";
    private static final String TELEFONO = "telefono";
    private List<Cliente> coleccionClientes;
    private static Clientes instancia;

    private Clientes() {
        coleccionClientes = new ArrayList<>();
    }

    static Clientes getInstancia() {
        if (instancia == null) {
            instancia = new Clientes();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_CLIENTES);
        procesarDocumentoXml(documentoXml);
    }

    private void procesarDocumentoXml(Document documentoXml) {
        if (documentoXml != null) {
            System.out.println("Fichero leído correctamente.");
            NodeList clientes = documentoXml.getElementsByTagName(CLIENTE);
            for (int i = 0; i < clientes.getLength(); i++) {
                Node cliente = clientes.item(i);
                if (cliente.getNodeType() == Node.ELEMENT_NODE) {
                    coleccionClientes.add((getCliente((Element) cliente)));
                }
            }
        } else {
            throw new IllegalArgumentException("El fichero no se ha leído correctamente.");
        }
    }

    private Cliente getCliente(Element elemento) {
        Cliente cliente;
        String dni = elemento.getAttribute(DNI);
        String nombre = elemento.getAttribute(NOMBRE);
        String telefono = elemento.getAttribute(TELEFONO);
        cliente = new Cliente(nombre, dni, telefono);
        return cliente;
    }

    @Override
    public void terminar() {
        Document documentoXml = crearDocumentoXml();
        documentoXml.appendChild(documentoXml.createElement(RAIZ));
        for (Cliente cliente : coleccionClientes) {
            documentoXml.getDocumentElement().appendChild(getElemento(documentoXml, cliente));
        }
        UtilidadesXml.escribirDocumentoXml(documentoXml, FICHERO_CLIENTES);
    }

    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        return constructor.newDocument();
    }

    private Element getElemento(Document documentoXml, Cliente cliente) {
        Element elementoCliente = documentoXml.createElement(CLIENTE);
        elementoCliente.setAttribute(DNI, cliente.getDni());
        elementoCliente.setAttribute(NOMBRE, cliente.getNombre());
        elementoCliente.setAttribute(TELEFONO, cliente.getTelefono());
        return elementoCliente;
    }

    @Override
    public List<Cliente> get() {
        return new ArrayList<>(coleccionClientes);
    }

    @Override
    public void insertar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if (coleccionClientes.contains(cliente)) {
            throw new OperationNotSupportedException("Ya existe un cliente con ese DNI.");
        }
        coleccionClientes.add(cliente);
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");
        boolean modificado = false;
        if (coleccionClientes.contains(cliente)) {
            if (nombre != null && !nombre.isBlank()){
                buscar(cliente).setNombre(nombre);
                modificado = true;
            }
            if (telefono != null && !telefono.isBlank()){
                buscar(cliente).setTelefono(telefono);
                modificado = true;
            }
        } else {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
        return modificado;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        int indice = coleccionClientes.indexOf(cliente);
        return indice == -1 ? null : coleccionClientes.get(indice);
    }

    @Override
    public void borrar(Cliente cliente) throws OperationNotSupportedException {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        if (coleccionClientes.contains(cliente)) {
            coleccionClientes.remove(cliente);
        } else {
            throw new OperationNotSupportedException("No existe ningún cliente con ese DNI.");
        }
    }
}