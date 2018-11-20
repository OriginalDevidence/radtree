package sistinfo.excepciones;

/**
 * No se puede insertar/actualizar el usuario en la base de datos ya que hay problemas con los campos únicos (alias, email)
 */
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
