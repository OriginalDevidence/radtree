package sistinfo.capadatos.excepciones;

@SuppressWarnings("serial")
public class ErrorInternoException extends Exception {
  public ErrorInternoException() { super(); }
  public ErrorInternoException(String message) { super(message); }
}
