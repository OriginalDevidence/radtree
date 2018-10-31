package sistinfo.capadatos.vo;
import java.sql.Date;

@SuppressWarnings("serial")
public class ContenidoVO implements java.io.Serializable {
	
	public enum Estado { PENDIENTE, VALIDADO, BORRADO };
	
	private long idContenido;
	private long idAutor;
	private long numVisitas;
	private Date fechaRealizacion;
	private Estado estado;
	
	public ContenidoVO(long idAutor, long numVisitas, Date fechaRealizacion, Estado estado) {
		this.idAutor = idAutor;
		this.numVisitas = numVisitas;
		this.fechaRealizacion = fechaRealizacion;
		this.estado = estado;
	}
	
	public ContenidoVO(long idContenido, long idAutor, long numVisitas, Date fechaRealizacion, Estado estado) {
		this.idContenido = idContenido;
		this.idAutor = idAutor;
		this.numVisitas = numVisitas;
		this.fechaRealizacion = fechaRealizacion;
		this.estado = estado;
	}
	
	public long getIdContenido() {
		return idContenido;
	}
	public void setIdContenido(long idContenido) {
		this.idContenido = idContenido;
	}
	public long getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(long idUsuario) {
		this.idAutor = idUsuario;
	}
	public long getNumVisitas() {
		return numVisitas;
	}
	public void setNumVisitas(long numVisitas) {
		this.numVisitas = numVisitas;
	}
	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}
	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
}
