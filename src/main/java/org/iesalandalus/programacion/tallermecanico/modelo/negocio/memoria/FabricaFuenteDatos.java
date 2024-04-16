package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IFuenteDatos;

public enum FabricaFuenteDatos {
    MEMORIA{
        @Override
        public IFuenteDatos crear() {
            return new FuenteDatosMemoria();
        }
    };
    public abstract IFuenteDatos crear();
}