package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {

    private static final String FICHERO_VEHICULOS = String.format("%s%s%s", "datos", File.separator, "vehiculos.xml");
    private static final String RAIZ = "vehiculos";
    private static final String VEHICULO = "vehiculo";
    private static final String MARCA = "marca";
    private static final String MODELO = "modelo";
    private static final String MATRICULA = "matricula";
    private List<Vehiculo> coleccionVehiculos;
    private static Vehiculos instancia;
    private Vehiculos() {
        coleccionVehiculos = new ArrayList<>();
    }

    static Vehiculos getInstancia() {
        if (instancia == null) {
            instancia = new Vehiculos();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_VEHICULOS);
        procesarDocumentoXml(documentoXml);
    }

    private void procesarDocumentoXml(Document documentoXml) {
        if (documentoXml != null) {
            System.out.println("Fichero leído correctamente.");
            NodeList vehiculos = documentoXml.getElementsByTagName(VEHICULO);
            for (int i = 0; i < vehiculos.getLength(); i++) {
                Node vehiculo = vehiculos.item(i);
                if (vehiculo.getNodeType() == Node.ELEMENT_NODE) {
                    coleccionVehiculos.add((getVehiculo((Element) vehiculo)));
                }
            }
        } else {
            throw new IllegalArgumentException("El fichero no se ha leído correctamente.");
        }
    }

    private Vehiculo getVehiculo(Element elemento) {
        Vehiculo vehiculo;
        String modelo = elemento.getAttribute(MODELO);
        String marca = elemento.getAttribute(MARCA);
        String matricula = elemento.getAttribute(MATRICULA);
        vehiculo = new Vehiculo(marca, modelo, matricula);
        return vehiculo;
    }

    @Override
    public void terminar() {
        Document documentoXml = crearDocumentoXml();
        documentoXml.appendChild(documentoXml.createElement(RAIZ));
        for (Vehiculo vehiculo : coleccionVehiculos) {
            documentoXml.getDocumentElement().appendChild(getElemento(documentoXml, vehiculo));
        }
        UtilidadesXml.escribirDocumentoXml(documentoXml, FICHERO_VEHICULOS);
    }

    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        return constructor.newDocument();
    }

    private Element getElemento(Document documentoXml, Vehiculo vehiculo) {
        Element elementoVehiculo = documentoXml.createElement(VEHICULO);
        elementoVehiculo.setAttribute(MODELO, vehiculo.modelo());
        elementoVehiculo.setAttribute(MARCA, vehiculo.marca());
        elementoVehiculo.setAttribute(MATRICULA, vehiculo.matricula());
        return elementoVehiculo;
    }

    @Override
    public List<Vehiculo> get() {
        return new ArrayList<>(coleccionVehiculos);
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        if (!coleccionVehiculos.contains(vehiculo)) {
            coleccionVehiculos.add(vehiculo);
        } else {
            throw new OperationNotSupportedException("Ya existe un vehículo con esa matrícula.");
        }
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        int indice = coleccionVehiculos.indexOf(vehiculo);
        return indice == -1 ? null : coleccionVehiculos.get(indice);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
        Objects.requireNonNull(vehiculo, "No se puede borrar un vehículo nulo.");
        if (coleccionVehiculos.contains(vehiculo)) {
            coleccionVehiculos.remove(vehiculo);
        } else {
            throw new OperationNotSupportedException("No existe ningún vehículo con esa matrícula.");
        }
    }
}