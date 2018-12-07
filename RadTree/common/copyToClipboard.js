function copyToClipboard(id) {
  /* Get the text field */
  var copyText = document.getElementById(id);

  /* Select the text field */
  copyText.select();

  /* Copy the text inside the text field */
  document.execCommand("copy");
  
  var tooltip = document.getElementById("tooltip");
  tooltip.innerHTML = "Copiado!"
}

function onMouseOutTooltip() {
  var tooltip = document.getElementById("tooltip");
  tooltip.innerHTML = "Click para copiar"
}