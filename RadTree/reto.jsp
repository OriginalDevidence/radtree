<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="sistinfo.capadatos.dao.RetoDAO" %>
<%@ page import="sistinfo.capadatos.dao.UsuarioDAO" %>
<%@ page import="sistinfo.capadatos.vo.RetoVO" %>
<%@ page import="sistinfo.capadatos.vo.UsuarioVO" %>
<%@ page import="sistinfo.excepciones.ErrorInternoException" %>
<%@ page import="sistinfo.utils.RequestExtractor" %>
<%--
	Obtener el reto del id pasado como parametro y el nombre de su autor
--%>
<%
	// Encontrar un ID de contenido
	Long idContenido = RequestExtractor.getLong(request, "id");
	if (idContenido == null || idContenido <= 0L) {
		// No sabemos qué reto mostrar
		response.sendRedirect("errorInterno.html");
	} else {
		// Añadirlo a los atributos de la request para comentario
		request.setAttribute("id", idContenido);
		request.setAttribute("redirect", "reto.jsp");
		// Cargar el reto con ese ID y el usuario autor
		RetoDAO retoDAO = new RetoDAO();
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		try {
			RetoVO reto = retoDAO.getRetoById(idContenido);
			if (reto == null) {
	            response.sendRedirect("errorInterno.html");
			} else {
				UsuarioVO usuario = usuarioDAO.getUsuarioById(reto.getIdAutor());
				if (usuario == null) {
		            response.sendRedirect("errorInterno.html");
				} else {
					request.setAttribute("reto", reto);
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

	<title><c:out value="${requestScope.reto.titulo}"/> - RadTree</title>
	<meta name="description" content="Descripción">
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

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="index.jsp"><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10" href="listaDeRetos.jsp">Retos<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#"><c:out value="${requestScope.reto.titulo}"/></a>
		</div><!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30"><b><c:out value="${requestScope.reto.titulo}"/></b></h3>

                    <p class="text-justify mb-20">
                        <c:out value="${requestScope.reto.cuerpo}"/>
                    </p>

                    <p><i>Autor: <c:out value="${requestScope.autor}"/></i></p>
                </div>
			</div>

		</div><!-- container -->
		
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
