package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IFuenteDatosMemoria;

public enum FabricaFuenteDatos {
    MEMORIA{
        @Override
        public IFuenteDatosMemoria crear() {
            return new FuenteDatosMemoria();
        }
    };
    public abstract IFuenteDatosMemoria crear();
}