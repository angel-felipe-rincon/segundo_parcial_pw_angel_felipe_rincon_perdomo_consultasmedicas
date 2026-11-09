package co.edu.usco.consultasmedicas.exception;

public class ConflictoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
