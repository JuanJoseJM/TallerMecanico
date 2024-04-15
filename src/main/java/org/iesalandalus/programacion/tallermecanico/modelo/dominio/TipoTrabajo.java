package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

public enum TipoTrabajo {
    MECANICO("Mecánico"), REVISION("Revisión");
    private String nombre;
    private TipoTrabajo(String nombre) {
        this.nombre = nombre;
    }

    public static TipoTrabajo get(Trabajo trabajo) {
        if (trabajo instanceof Revision) {
            return REVISION;
        }
        return MECANICO;
    }

    public String ToString() {
        return this.nombre;
    }
}


