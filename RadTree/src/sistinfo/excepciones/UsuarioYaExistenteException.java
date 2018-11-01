package sistinfo.excepciones;

@SuppressWarnings("serial")
public class UsuarioYaExistenteException extends Exception {
  boolean aliasExistente;
  boolean emailExistente;
  public UsuarioYaExistenteException(boolean aliasExistente, boolean emailExistente) {
	  super();
	  this.aliasExistente = aliasExistente;
	  this.emailExistente = emailExistente;
  }
  public boolean isAliasExistente() {
	  return aliasExistente;
  }
  public boolean isEmailExistente() {
	  return emailExistente;
  }
}
