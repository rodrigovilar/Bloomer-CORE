$(document).ready(function(){
	ajax("http://localhost:8080/Bloomer-CORE/jogos/1/desempenho/1");
});

function ajax(path){

	$.ajax({
		//type: "GET",
		url: path,
		//data: {nome: value},
		dataType: "json",
		success: function(json){

			var options = json.nomeJogo;

			$("#content").html(options);
		}
	});
};
