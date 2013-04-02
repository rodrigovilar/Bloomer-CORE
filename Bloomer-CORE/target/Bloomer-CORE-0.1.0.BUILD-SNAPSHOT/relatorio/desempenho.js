$(function() {
	var tabela = $('<table border="1">');
	$('body').append(tabela);

	var idUsuario = 1;
	var idJogo = 1;
	
	$.getJSON('../jogos/' + idJogo + '/desempenho/' + idUsuario,
			function(json) {
				alert(json.nomeJogo);
			});
});
