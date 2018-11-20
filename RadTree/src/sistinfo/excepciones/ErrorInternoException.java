package sistinfo.excepciones;

/**
 * Evento interno (ya sea de la base de datos o una causa externa) que no debería pasar
 */
@SuppressWarnings("serial")
public class ErrorInternoException extends Exception {
  public ErrorInternoException() { super(); }
  public ErrorInternoException(String message) { super(message); }
}
