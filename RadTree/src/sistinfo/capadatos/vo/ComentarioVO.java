package sistinfo.capadatos.vo;
import java.sql.Date;

@SuppressWarnings("serial")
public class ComentarioVO implements java.io.Serializable {
	
	private Long idComentario;	
	private Long idAutor;
	private Long idContenido;
	private String cuerpo;
	private Integer numLikes;
	private Date fecha;
	private Long respuestaDe;
	
	/* No se almacenan en la BDD pero son útiles para evitar muchas consultas */
	private String autor;
	private String autorPadre;
	
	public ComentarioVO(Long idAutor, Long idContenido, String cuerpo, Integer numLikes, Date fecha,
			Long respuestaDe) {
		this.idAutor = idAutor;
		this.idContenido = idContenido;
		this.cuerpo = cuerpo;
		this.numLikes = numLikes;
		this.fecha = fecha;
		this.respuestaDe = respuestaDe;

		this.autor = null;
		this.autorPadre = null;
	}
	
	public ComentarioVO(Long idComentario, Long idAutor, Long idContenido, String cuerpo, Integer numLikes, Date fecha,
			Long respuestaDe) {
		this.idComentario = idComentario;
		this.idAutor = idAutor;
		this.idContenido = idContenido;
		this.cuerpo = cuerpo;
		this.numLikes = numLikes;
		this.fecha = fecha;
		this.respuestaDe = respuestaDe;
		this.autor = null;
		this.autorPadre = null;
	}
	
	public ComentarioVO(Long idComentario, Long idAutor, Long idContenido, String cuerpo, Integer numLikes, Date fecha,
			 Long respuestaDe, String nombreAutor) {
		this.idComentario = idComentario;
		this.idAutor = idAutor;
		this.idContenido = idContenido;
		this.cuerpo = cuerpo;
		this.numLikes = numLikes;
		this.fecha = fecha;
		this.respuestaDe = respuestaDe;
		this.autor = nombreAutor;
		this.autorPadre = null;
	}

	public Long getIdComentario() {
		return idComentario;
	}
	public void setIdComentario(Long idComentario) {
		this.idComentario = idComentario;
	}
	public Long getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(Long idAutor) {
		this.idAutor = idAutor;
	}
	public Long getIdContenido() {
		return idContenido;
	}
	public void setIdContenido(Long idContenido) {
		this.idContenido = idContenido;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public Integer getNumLikes() {
		return numLikes;
	}
	public void setNumLikes(Integer numLikes) {
		this.numLikes = numLikes;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getRespuestaDe() {
		return respuestaDe;
	}
	public void setRespuestaDe(Long respuestaDe) {
		this.respuestaDe = respuestaDe;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String nombreAutor) {
		this.autor = nombreAutor;
	}
	public String getAutorPadre() {
		return autorPadre;
	}
	public void setAutorPadre(String nombreRespuestaDe) {
		this.autorPadre = nombreRespuestaDe;
	}
	
}
