package sistinfo.capadatos.vo;
import java.sql.Date;

@SuppressWarnings("serial")
public class NoticiaVO extends ContenidoVO {
	
	private String titulo;
	private String cuerpo;
	private String url;
	
	private String urlImagen;
	
	public NoticiaVO(Long idAutor, Long numVisitas, Date fechaRealizacion, Estado estado,
			String titulo, String cuerpo, String url) {
		super(idAutor, numVisitas, fechaRealizacion, estado);
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.url = url;
	}
	
	public NoticiaVO(Long idContenido, Long idAutor, Long numVisitas, Date fechaRealizacion, Estado estado,
			String titulo, String cuerpo, String url) {
		super(idContenido, idAutor, numVisitas, fechaRealizacion, estado);
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.url = url;
	}
	
	public NoticiaVO(Long idContenido, Long idAutor, Long numVisitas, Date fechaRealizacion, Estado estado,
			String titulo, String cuerpo, String url, String urlImagen) {
		super(idContenido, idAutor, numVisitas, fechaRealizacion, estado);
		this.titulo = titulo;
		this.cuerpo = cuerpo;
		this.url = url;
		this.urlImagen = urlImagen;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlImagen() {
		return urlImagen;
	}
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	
}
