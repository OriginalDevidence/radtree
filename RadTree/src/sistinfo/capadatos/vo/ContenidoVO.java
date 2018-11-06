package sistinfo.capadatos.vo;
import java.sql.Date;

@SuppressWarnings("serial")
public class ContenidoVO implements java.io.Serializable {
	
	public enum Estado { PENDIENTE, VALIDADO, BORRADO };
	
	private Long idContenido;
	private Long idAutor;
	private Long numVisitas;
	private Date fechaRealizacion;
	private Estado estado;
	
	public ContenidoVO(Long idAutor, Long numVisitas, Date fechaRealizacion, Estado estado) {
		this.idAutor = idAutor;
		this.numVisitas = numVisitas;
		this.fechaRealizacion = fechaRealizacion;
		this.estado = estado;
	}
	
	public ContenidoVO(Long idContenido, Long idAutor, Long numVisitas, Date fechaRealizacion, Estado estado) {
		this.idContenido = idContenido;
		this.idAutor = idAutor;
		this.numVisitas = numVisitas;
		this.fechaRealizacion = fechaRealizacion;
		this.estado = estado;
	}
	
	public Long getIdContenido() {
		return idContenido;
	}
	public void setIdContenido(Long idContenido) {
		this.idContenido = idContenido;
	}
	public Long getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(Long idUsuario) {
		this.idAutor = idUsuario;
	}
	public Long getNumVisitas() {
		return numVisitas;
	}
	public void setNumVisitas(Long numVisitas) {
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
