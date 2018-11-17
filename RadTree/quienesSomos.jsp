<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Quienes somos - RadTree</title>
	<meta name="description" content="Descripción">
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

	<section class="ptb-0">
		<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
			<a class="mt-10" href="."><i class="mr-5 ion-ios-home"></i>Inicio<i class="mlr-10 ion-chevron-right"></i></a>
			<a class="mt-10 color-ash" href="#">¿Quienes somos?</a>
		</div><!-- container -->
	</section>

	<section>
		<div class="container">

			<div class="row">
				<div class="col-md-12 col-lg-8">
					<h3 class="mb-30"><b>¿Quienes somos?</b></h3>
                    <p class="mb-20 text-justify">
                        El departamento de Ingeniería Química y Tecnologías del Medio Ambiente
                        está realizando un proyecto cuyo objetivo es informar a la población
                        sobre los peligros medioambientales y sus mitos relacionados, y cómo
                        llegar a un mundo sostenible. Además busca recopilar información acerca
                        de la concienciación que tienen las personas sobre la importancia de este tema.
                    </p>
                    <p class="mb-20 text-justify">
                        Estos objetivos se intentan llevar a cabo mediante tres formas diferentes:
                        Noticias, preguntas y retos. Para generar estos contenidos se ha decidido
                        involucrar a alumnos universitarios, de la asignatura de ingeniería del medio
                        ambiente, que deberán presentar un cartel por grupos al profesor encargado
                        de la asignatura. Este cartel contendrá diferentes secciones: una noticia,
                        una pregunta (ya sea de conocimiento o de opinión) y un reto.
                    </p>
                    <p class="mb-20 text-justify">
                        Las noticias tratan de sucesos o casos de actualidad relacionados con el
                        tema del medioambiente. Por otro lado, las preguntas son de opción múltiple y
                        son de conocimiento o de opinión. Finalmente se plantean retos a los participantes
                        que intentan que el usuario haga un pequeño esfuerzo para hacer una buena labor
                        por el medio ambiente.
                    </p>
                    <p class="mb-20 text-justify">
                        Al finalizar el proyecto, los alumnos entregan los carteles a sus respectivos
                        profesores, que se encargan de elegir los mejores contenidos entre todos los
                        carteles que han sido presentados y crear uno con las mejores secciones de estos.
                        En el apartado de bibliografía se pueden ver varios ejemplos de carteles completados.
                    </p>
                </div>
            </div> 
		</div><!-- container -->
	</section>

	<%@ include file="WEB-INF/footer.jsp" %>

	<!-- SCRIPTS -->
	<script src="${pageContext.request.contextPath}/plugin-frameworks/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/tether.min.js"></script>
	<script src="${pageContext.request.contextPath}/plugin-frameworks/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath}/common/scripts.js"></script>

</body>
</html>
