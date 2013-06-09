/*
* Bloomer - CRUD - Usuário
*/

var entity = "tipojogoes";
var global_id = " ";
var get_version = " ";
var temp = 0;

$(document).ready(function(){
	Read();
});

function Create (){
	$.ajax({
		type: "POST",
		url: "http://localhost:8080/Bloomer-CORE/"+ entity,
		data: buildJSON(),
		dataType: "json",
		contentType: "application/json",
		processData: true,
        headers: {
          Accept: "application/json"
        },
		complete: function(json){
			alert("Tipo de Jogo Criado.");
			Read();
		}
	});
};

function Read (){


	if_exists_remove("#list_table");
	if_exists_remove("#form");

	$.getJSON("http://localhost:8080/Bloomer-CORE/"+ entity,
		function(json){

		
			var $div = $('<div />').appendTo('body');
			$div.attr('id', 'list');

			var table = $('<table border="1" cellspacing="0" style="text-align:center"></table>').attr('id', 'list_table');

			var title = '<tr><td colspan="8"><a href="#" onclick="Form(1);"><img src="../images/create.png"/>Add</a></td></tr>';
			table.append(title);

			var header = '<tr><th>ID</th><th>Nome</th><th>Descrição</th><th>Autor</th><th>Plataforma</th><th>Nível de Taxonomia</th><th>Edit</th><th>Delete</th></tr>';
			table.append(header);

			for (i=0; i < json.length; i++){
			    var row = '<tr><td>' + json[i].id + '</td><td>' + json[i].nome + '</td><td>' + json[i].descricao +
			    		  '</td><td>' + json[i].autor + '</td><td>' + json[i].plataforma + '</td><td>' + json[i].niveisDaTaxonomia +
			    		  '</td><td><a href="#" onclick="fill(' + json[i].id + ');"><img src="../images/update.png"/></a>' +
			    		  '</td><td><a href="#" onclick="Delete(' + json[i].id + ');"><img src="../images/delete.png"/></a>' +
			    		  '</td></tr>';

			    table.append(row);
			}

			$("#list").append(table);
		}
	);
};

function Update(){
	$.ajax({
		type: "PUT",
		url: "http://localhost:8080/Bloomer-CORE/"+ entity,
		data: buildJSON(),
		dataType: "json",
		contentType: "application/json",
		processData: true,
        headers: {
          Accept: "application/json"
        },
		complete: function(json){
			alert("Tipo de Jogo Editado.");
			Read();
		}
	});
}

function Delete (id){	
	
	var answer = confirm("Delete user id: "+id+"?");

	if (answer){
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8080/Bloomer-CORE/" +entity+ "/" +id,
			dataType: "json",
			success: function(json){
				Read();
			}
		});
	}
};

function buildJSON(){

	var niveis = [];

	$("#niveisTaxonomia :checked").each(function(){
		niveis.push($(this).val());
	});

	var id = " ";
	var versao = " ";

	alert('"temp é: "' + temp);

	if(get_version!= " " && temp == 2){
		versao = '"version": ' + get_version + ', ';
	}

	if(global_id != " "){
		id = '"id" : '+global_id+', '; 
	}

	var the_json = '{'+ id + versao + '"nome":"'+ $('input[name="nome"]').val() +
	'", "descricao":"'+ $('input[name="descricao"]').val() +
	'", "autor":"'+ $('input[name="autor"]').val() +
	'", "plataforma":"'+ $('select[name="plataforma"]').val() +
	'", "niveisTaxonomia":[';

	for(var i=0; i<niveis.length; i++)
		(i == niveis.length - 1) ? the_json += '"'+niveis[i]+'"' : the_json += '"'+niveis[i] + '", ';

	the_json += ']}';

	console.log(the_json);

	return the_json;
}

function Form (type){

	if_exists_remove("#form");

	var action;
	var submit_button;

	if (type == 1){
		action = "javascript:Create()";
		submit_button = "Save";
		temp = 1;
	} else if (type == 2){
		action = "javascript:Update()";
		submit_button = "Edit";
		temp = 2;
	}

	var $div = $('<div />').appendTo('body');
	$div.attr('id', 'form');

	var form = $('<form action="'+action+'"><br>' +
					 '<spam><strong>Formulário Tipo Jogos</strong></spam><br>' +
					 '<input name="nome" type="text" placeholder="Nome" /><br>' +
					 '<input name="descricao" type="text" placeholder="Descrição" /><br>' +
					 '<input name="autor" type="text" placeholder="Autor" /><br>' +
					 '<spam><strong>Plataforma</strong></spam><br>' +
					 '<select name="plataforma">' +
		                  '<option value="Unity3D">Unity3D</option>' +
		                  '<option value="Construct2">Construct2</option>' +
		                  '<option value="Flash">Flash</option>' +
		                  '<option value="Flex">Flex</option>' +
		                  '<option value="Pygame">Pygame</option>' +
		                  '<option value="Other">Other</option>' +
		             '</select><br>' +
					 '<spam><strong>Níveis da Taxonomia</strong></spam><br>' +
					 '<div id="niveisTaxonomia">' +
						 '<input id="Conhecimento" type="checkbox" value="Conhecimento"> Conhecimento<br>' +
						 '<input id="Compreensao" type="checkbox" value="Compreensao"> Compreensão<br>' +
						 '<input id="Aplicacao" type="checkbox" value="Aplicacao"> Aplicação<br>' +
						 '<input id="Analise" type="checkbox" value="Analise"> Análise<br>' +
						 '<input id="Sintese" type="checkbox" value="Sintese"> Síntese<br>' +
						 '<input id="Avaliacao" type="checkbox" value="Avaliacao"> Avaliação<br><br>' +
				 	 '</div>' +
					 '<input value="'+submit_button+'" type="submit" />' +
				 '</form>');

	$("#form").append(form);
};

function fill (id){

	if_exists_remove("#form");
	Form(2);

	global_id = id;

	$.getJSON("http://localhost:8080/Bloomer-CORE/" +entity+ "/" +id,

		function(json){

				get_version = json.version;
				alert('"O que vem é "' + get_version);

			$("input[name='nome']").val(json.nome);
			$("input[name='descricao']").val(json.descricao);
			$("input[name='autor']").val(json.autor);
			$('select[name="plataforma"]').val(json.plataforma);

			for(var i=0; i < json.niveisTaxonomia.length; i++){
				(json.niveisTaxonomia[i] == $("#niveisTaxonomia").children("#Conhecimento").val()) ? $("#niveisTaxonomia").children("#Conhecimento").attr('checked', true) : $("#niveisTaxonomia");
				(json.niveisTaxonomia[i] == $("#niveisTaxonomia").children("#Compreensao").val()) ? $("#niveisTaxonomia").children("#Compreensao").attr('checked', true) : $("#niveisTaxonomia");
				(json.niveisTaxonomia[i] == $("#niveisTaxonomia").children("#Aplicacao").val()) ? $("#niveisTaxonomia").children("#Aplicacao").attr('checked', true) : $("#niveisTaxonomia");
				(json.niveisTaxonomia[i] == $("#niveisTaxonomia").children("#Analise").val()) ? $("#niveisTaxonomia").children("#Analise").attr('checked', true) : $("#niveisTaxonomia");
				(json.niveisTaxonomia[i] == $("#niveisTaxonomia").children("#Sintese").val()) ? $("#niveisTaxonomia").children("#Sintese").attr('checked', true) : $("#niveisTaxonomia");
				(json.niveisTaxonomia[i] == $("#niveisTaxonomia").children("#Avaliacao").val()) ? $("#niveisTaxonomia").children("#Avaliacao").attr('checked', true) : $("#niveisTaxonomia");
			}
		}
	);
};

function if_exists_remove(element){
	if($(element) !== null){
		$(element).remove();
	}

}
