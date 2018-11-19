package sistinfo.excepciones;

/**
 * Alguien intenta responder a una pregunta que (según la base de datos) ya había respondido anteriormente
 */
@SuppressWarnings("serial")
public class PreguntaYaRespondidaException extends Exception {
  public PreguntaYaRespondidaException() { super(); }
  public PreguntaYaRespondidaException(String message) { super(message); }
}
