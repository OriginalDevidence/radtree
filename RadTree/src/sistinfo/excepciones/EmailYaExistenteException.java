package sistinfo.excepciones;

@SuppressWarnings("serial")
public class EmailYaExistenteException extends Exception {
  public EmailYaExistenteException() { super(); }
  public EmailYaExistenteException(String message) { super(message); }
}
