package sistinfo.capadatos.vo;
import java.sql.Date;

@SuppressWarnings("serial")
public class ComentarioVO implements java.io.Serializable {
	
	private long idComentario;	
	private long idAutor;
	private long idContenido;
	private String cuerpo;
	private int numLikes;
	private Date fecha;
	private long respuestaDe;
	
	public ComentarioVO(long idAutor, long idContenido, String cuerpo, int numLikes, Date fecha,
			long respuestaDe) {
		this.idAutor = idAutor;
		this.idContenido = idContenido;
		this.cuerpo = cuerpo;
		this.numLikes = numLikes;
		this.fecha = fecha;
		this.respuestaDe = respuestaDe;
	}
	
	public ComentarioVO(long idComentario, long idAutor, long idContenido, String cuerpo, int numLikes, Date fecha,
			long respuestaDe) {
		this.idComentario = idComentario;
		this.idAutor = idAutor;
		this.idContenido = idContenido;
		this.cuerpo = cuerpo;
		this.numLikes = numLikes;
		this.fecha = fecha;
		this.respuestaDe = respuestaDe;
	}
	
	public long getIdComentario() {
		return idComentario;
	}
	public void setIdComentario(long idComentario) {
		this.idComentario = idComentario;
	}
	public long getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(long idAutor) {
		this.idAutor = idAutor;
	}
	public long getIdContenido() {
		return idContenido;
	}
	public void setIdContenido(long idContenido) {
		this.idContenido = idContenido;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public int getNumLikes() {
		return numLikes;
	}
	public void setNumLikes(int numLikes) {
		this.numLikes = numLikes;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public long getRespuestaDe() {
		return respuestaDe;
	}
	public void setRespuestaDe(long respuestaDe) {
		this.respuestaDe = respuestaDe;
	}
	
}
