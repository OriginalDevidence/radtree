<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="sistinfo.capadatos.dao.NoticiaDAO" %>
<%@ page import="sistinfo.capadatos.dao.UsuarioDAO" %>
<%@ page import="sistinfo.capadatos.vo.NoticiaVO" %>
<%@ page import="sistinfo.capadatos.vo.UsuarioVO" %>
<%@ page import="sistinfo.excepciones.ErrorInternoException" %>
<%--
	Obtener la noticia del id pasado como parametro y el nombre de su autor
--%>
<%
	// Encontrar un ID de contenido
	Long idContenido = RequestExtractor.getLong(request, "id");
	if (idContenido == null || idContenido <= 0L) {
		// No sabemos qué noticia mostrar
		response.sendRedirect("errorInterno.html");
	} else {
		// Añadirlo a los atributos de la request para comentario
		request.setAttribute("id", idContenido);
		request.setAttribute("redirect", "noticia.jsp");
		// Cargar la noticia con ese ID y el usuario autor
		NoticiaDAO noticiaDAO = new NoticiaDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		try {
			NoticiaVO noticia = noticiaDAO.getNoticiaById(idContenido);
			if (noticia == null) {
	            response.sendRedirect("errorInterno.html");
			} else {
				UsuarioVO usuario = usuarioDAO.getUsuarioById(noticia.getIdAutor());
				if (usuario == null) {
		            response.sendRedirect("errorInterno.html");
				} else {
					request.setAttribute("noticia", noticia);
					request.setAttribute("autor", usuario.getNombre() + " " + usuario.getApellidos() + " (" + usuario.getAlias() + ")");
				}
			}
		} catch (ErrorInternoException e) {
            response.sendRedirect("errorInterno.html");
		}
	}
%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title><c:out value="${requestScope.noticia.titulo}"/> - RadTree</title>
	<meta name="description" content="Página de perfil de usuario">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

	<!-- Stylesheets -->
	<link href="plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="fonts/ionicons.css" rel="stylesheet">
	<link href="common/styles.css" rel="stylesheet">
</head>
<body>

	<%@ include file="WEB-INF/header.jsp" %>

	<section>
	
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30">
						<b><c:out value="${requestScope.noticia.titulo}" /></b>
					</h3>

					<p class="text-justify pr-30 mb-30">
						<c:out value="${requestScope.noticia.cuerpo}" />
					</p>

					<p class="mb-20">
						URL de la fuente:
						<b><a href="<c:out value="${requestScope.noticia.url}"/>"><c:out value="${requestScope.noticia.url}"/></a></b>
					</p>
					<p class="mb-30">
						<i>Autor: <c:out value="${requestScope.autor}" /></i>
					</p>
				</div>

				<div class="col-md-12 col-lg-4">
					<img src="images/Eco-1_900x600.jpg" alt="Imagen de la noticia" />
				</div>
			</div>

		</div>
		
		<%@ include file="WEB-INF/comentarios.jsp" %>
		
	</section>

	
	<%@ include file="WEB-INF/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="plugin-frameworks/tether.min.js"></script>
	<script src="plugin-frameworks/bootstrap.js"></script>
	<script src="common/scripts.js"></script>
	
</body>
</html>
