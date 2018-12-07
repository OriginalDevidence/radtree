function showdivDisplay() {
		var mydiv = document.getElementById("mydiv");
		var mybutton = document.getElementById("mybutton");

		if (mydiv != null && mybutton != null) {
			mybutton.onclick = hidedivDisplay;
			mydiv.style.display = "";
		}

	}
	function hidedivDisplay() {
		var mydiv = document.getElementById("mydiv");
		var mybutton = document.getElementById("mybutton");

		if (mydiv != null && mybutton != null) {
			mybutton.onclick = showdivDisplay;
			mydiv.style.display = "none";
		}
	}
