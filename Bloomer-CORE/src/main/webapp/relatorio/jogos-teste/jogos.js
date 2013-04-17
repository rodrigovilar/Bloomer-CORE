$(document).ready(function(){
	alert("front-end dando o GET no json do back-end agora->");
	ajax("http://localhost:8080/Bloomer-CORE/jogos/");
	


	//esse GET é pra pegar por id, retornei o método responsável por esse GET para o .aj
	//ajax("http://localhost:8080/Bloomer-CORE/jogos/1");
});

function ajax(path){

	$.ajax({
		
		//type: "GET",
		url: path,
		//data: {nome: value},
		dataType: "json",
		success: function(json){
			alert("front-end já recebeu o json do back-end, o resultado está no <body>");
			
			var primeiroJogo = json[0];
			var id = primeiroJogo.id;
			var nome = primeiroJogo.nome;
			
			var stringJson = JSON.stringify(json);
			$("#content").html(stringJson);
			
		}
	});
};
