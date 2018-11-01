package sistinfo.excepciones;

@SuppressWarnings("serial")
public class AliasYaExistenteException extends Exception {
  public AliasYaExistenteException() { super(); }
  public AliasYaExistenteException(String message) { super(message); }
}
