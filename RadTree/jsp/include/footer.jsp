<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<footer class="bg-191 color-ccc">
	<div class="container">
		<div class="pt-50 pb-20 pos-relative">
			<div class="abs-tblr pt-50 z--1 text-center">
				<div class="h-80 pos-relative"><img class="opacty-1 h-100 w-auto" src="${pageContext.request.contextPath}/images/map.png" alt=""></div>
			</div>
			<div class="row">
			
				<div class="col-sm-4">
					<div class="mb-30">
						<a href="${pageContext.request.contextPath}"><img src="${pageContext.request.contextPath}/images/logo-white.png"></a>
						<p class="mt-20 color-ccc">
							RadTree es un proyecto de la Universidad de Zaragoza que intenta concienciar a la población sobre los peligros medioambientales.
						</p>
						<p class="mb-20"><a class="mt-10 link-brdr-btm-primary color-primary" href="${pageContext.request.contextPath}/quienes-somos">¿Quieres saber más?</a></p>
						<p class="color-ash">
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="ion-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						</p>
					</div><!-- mb-30 -->
				</div><!-- col-md-4 -->
				
				<div class="col-sm-4">
					<div class="mb-30">
						<h5 class="color-primary mb-20"><b>ÚLTIMAS NOTICIAS</b></h5>
						<div class="mb-20">
							<form name="noticiaUltima1" action="${pageContext.request.contextPath}/noticias/ver" method="post">
								<input type="hidden" name="id" value="<c:out value="${requestScope.ultima1.idContenido}"/>"/>
								<button type="submit" class="color-white font-11 font-sans text-left"><b><c:out value="${requestScope.ultima1.titulo}"/></b></button>
								<h6 class="mt-10 font-sans"><c:out value="${requestScope.ultima1.fechaRealizacion}"/></h6>
							</form>
						</div>
						<div class="brdr-ash-1 opacty-2 mr-30"></div>
						<div class="mt-20">
							<form name="noticiaUltima2" action="${pageContext.request.contextPath}/noticias/ver" method="post">
								<input type="hidden" name="id" value="<c:out value="${requestScope.ultima2.idContenido}"/>"/>
								<button type="submit" class="color-white font-11 font-sans text-left"><b><c:out value="${requestScope.ultima2.titulo}"/></b></button>
								<h6 class="mt-10 font-sans"><c:out value="${requestScope.ultima2.fechaRealizacion}"/></h6>
							</form>
						</div>
					</div><!-- mb-30 -->
				</div><!-- col-md-4 -->
				
				<div class="col-sm-4">
					<div class="mb-30">
						<h5 class="color-primary mb-20"><b>NOTICIAS MÁS POPULARES</b></h5>
						<div class="mb-20">
							<form name="noticiaPopular1" action="${pageContext.request.contextPath}/noticias/ver" method="post">
								<input type="hidden" name="id" value="<c:out value="${requestScope.popular1.idContenido}"/>"/>
								<button type="submit" class="color-white font-11 font-sans text-left"><b><c:out value="${requestScope.popular1.titulo}"/></b></button>
								<h6 class="mt-10 font-sans"><c:out value="${requestScope.popular1.fechaRealizacion}"/></h6>
							</form>
						</div>
						<div class="brdr-ash-1 opacty-2 mr-30"></div>
						<div class="mt-20">
							<form name="noticiaPopular2" action="${pageContext.request.contextPath}/noticias/ver" method="post">
								<input type="hidden" name="id" value="<c:out value="${requestScope.popular2.idContenido}"/>"/>
								<button type="submit" class="color-white font-11 font-sans text-left"><b><c:out value="${requestScope.popular2.titulo}"/></b></button>
								<h6 class="mt-10 font-sans"><c:out value="${requestScope.popular2.fechaRealizacion}"/></h6>
							</form>
						</div>
					</div><!-- mb-30 -->
				</div><!-- col-md-4 -->
				
			</div><!-- row -->
		</div><!-- ptb-50 -->
		
		<div class="brdr-ash-1 opacty-2"></div>
		
		<div class="oflow-hidden color-ash font-9 text-sm-center ptb-sm-5">
		
			<ul class="float-right float-sm-none list-a-plr-10 list-a-plr-sm-5 list-a-ptb-15 list-a-ptb-sm-5">
				<li><a class="pl-0 pl-sm-10" href="#"><i class="ion-social-facebook"></i></a></li>
				<li><a href="#"><i class="ion-social-twitter"></i></a></li>
				<li><a href="#"><i class="ion-social-instagram"></i></a></li>
			</ul>
			
		</div><!-- oflow-hidden -->
	</div><!-- container -->
</footer>