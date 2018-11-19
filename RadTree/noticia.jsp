<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title><c:out value="${requestScope.noticia.titulo}"/> - RadTree</title>
	<meta name="description" content="Una de las noticias presentadas por los usuarios de RadTree">
	<meta name="author" content="Grupo A: Gregorio Largo, Alonso Muñoz y Diego Royo">

	<!-- Font -->
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">

	<!-- Stylesheets -->
	<link href="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/fonts/ionicons.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/common/styles.css" rel="stylesheet">
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
						<b>URL de la fuente: </b>
						<a class="link-brdr-btm-primary color-primary" href="<c:out value="${requestScope.noticia.url}"/>"><c:out value="${requestScope.noticia.url}"/></a>
					</p>
					<form name="perfilAutor" action="${pageContext.request.contextPath}/perfil" method="post">
                    	<input type="hidden" name="alias" value="<c:out value='${requestScope.autorAlias}'/>"/>
                    	<p><i><b>Autor: </b>
	                   		<button class="link-brdr-btm-primary color-primary" type="submit"><c:out value='${requestScope.autorCompleto}'/></button>
						</i></p>
                    </form>
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
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>
	
</body>
</html>
