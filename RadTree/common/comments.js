function setRespuestaDe(id, autor) {
	document.getElementById("respuestaDe").value = id;
	document.getElementById("respondiendo").innerText = "Respondiendo a " + autor;
	document.getElementById("respondiendo").scrollIntoView();
}