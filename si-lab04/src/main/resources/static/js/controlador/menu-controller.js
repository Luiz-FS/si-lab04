var acc = document.getElementsByClassName('alteraLista');

for (var i = 0; i < acc.length; i++) {
	
	acc[i].onclick = function() {

		this.classList.toggle("ativo");

		var painel = this.nextElementSibling;

		if (painel.style.maxHeight) {
			painel.style.maxHeight = null;
		} else {
			painel.style.maxHeight = painel.scrollHeight + "px";
		}
	}
	
}