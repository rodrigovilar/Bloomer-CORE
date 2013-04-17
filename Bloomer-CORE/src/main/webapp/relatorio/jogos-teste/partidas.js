$(document).ready(function(){
	alert("front-end dando o GET no json do back-end agora->");
	ajax("http://localhost:8080/Bloomer-CORE/jogos/1/partidas");
	
	
	
});

function ajax(path){

	$.ajax({
		
		//type: "GET",
		url: path,
		//data: {nome: value},
		dataType: "json",
		success: function(json){
			alert("front-end já recebeu o json do back-end, o resultado está no <body>");
			
			var questoes = json.questoes;
			var partidas = json.partidas;
			
			var stringJson = JSON.stringify(json);
			$("#content").html(stringJson);	
		

		}
	});
};
