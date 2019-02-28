function showFiltros() {
	var mydiv = document.getElementById("filtrosDiv");
	var mybutton = document.getElementById("filtrosButton");

	if (mydiv != null && mybutton != null) {
		mybutton.onclick = hideFiltros;
		mydiv.style.display = "";
	}

}

function hideFiltros() {
	var mydiv = document.getElementById("filtrosDiv");
	var mybutton = document.getElementById("filtrosButton");

	if (mydiv != null && mybutton != null) {
		mybutton.onclick = showFiltros;
		mydiv.style.display = "none";
	}
}
