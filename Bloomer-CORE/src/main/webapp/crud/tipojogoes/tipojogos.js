/*
* Bloomer - CRUD - Tipo Jogos
*/


var entity = "tipojogoes"; // O string referente a entidade fica armazenado em uma variável global.
var global_id = " "; // O valor do ID atual fica armazenado em uma variável global.
var get_version = " "; // O valor do version atual fica armazenado em uma variável global.
var action; // Responsável por definir se o parâmetro action irá chamar CREATE ou UPDATE.

$(document).ready(function(){
	READ();
});

function CREATE(){
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
			alert("Tipo de Jogo Criado com Sucesso.");
			READ();
		}
	});
};

function READ(){

	ifExistRemove("#list_table");
	ifExistRemove("#form");

	$.getJSON("http://localhost:8080/Bloomer-CORE/"+ entity,
		function(json){

			var $div = $('<div />').appendTo('body');
			$div.attr('id', 'list');

			var table = $('<table border="1" cellspacing="0" style="text-align:center"></table>').attr('id', 'list_table');

			// In buildForm function, 1 its a parameter who means CREATE.
			var title = '<tr><td colspan="8"><a href="#" onclick="buildForm(1);"><img src="../images/create.png"/>Add</a></td></tr>';
			table.append(title);

			var header = '<tr><th>ID</th><th>Nome</th><th>Descrição</th><th>Autor</th><th>Plataforma</th><th>Nível de Taxonomia</th><th>Edit</th><th>Delete</th></tr>';
			table.append(header);

			for (i=0; i < json.length; i++){
			    var row = '<tr><td>' + json[i].id + '</td><td>' + json[i].nome + '</td><td>' + json[i].descricao +
			    		  '</td><td>' + json[i].autor + '</td><td>' + json[i].plataforma + '</td><td>' + json[i].niveisTaxonomia +
			    		  '</td><td><a href="#" onclick="fillForm(' + json[i].id + ');"><img src="../images/update.png"/></a>' +
			    		  '</td><td><a href="#" onclick="DELETE(' + json[i].id + ');"><img src="../images/delete.png"/></a>' +
			    		  '</td></tr>';

			    table.append(row);
			}

			$("#list").append(table);
		}
	);
};

function UPDATE(){
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
			alert("Tipo de Jogo Editado com Sucesso.");
			READ();
		}
	});
}

function DELETE(id){

	var answer = confirm("Delete register id: "+id+"?");

	if (answer){
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8080/Bloomer-CORE/" +entity+ "/" +id,
			dataType: "json",
			success: function(json){
				READ();
			}
		});
	}
};

function buildJSON(){

	var niveis = []; // Responsável por armazenar o(s) valor(es) dos níveis de taxonomia.
	var id = ''; // Responsável por armazenar o valor do id a ser enviado no JSON.
	var version = ''; // Responsável por armazenar o valor do version a ser enviado no JSON.

	$("#niveisTaxonomia :checked").each(function(){
		niveis.push($(this).val());
	});

	// Condição para verificar se já existe um global_id
	if(global_id == " ")
		id = '';
	else
		id = '"id":' + global_id + ', ';

	// Caso o usuário esteja editando um registro, esta condição adiciona a variável 'version' ao JSON.
	if(action == "javascript:UPDATE()"){
		version = '"version": ' + get_version + ', ';
	}

	var the_json = '{' + id + version + '"nome":"'+ $('input[name="nome"]').val() +
	'", "descricao":"'+ $('input[name="descricao"]').val() +
	'", "autor":"'+ $('input[name="autor"]').val() +
	'", "plataforma":"'+ $('select[name="plataforma"]').val() +
	'", "niveisTaxonomia":[';

	for(var i=0; i < niveis.length; i++)
		(i == niveis.length - 1) ? the_json += '"'+niveis[i]+'"' : the_json += '"'+niveis[i] + '", ';

	the_json += ']}';

	return the_json;
}

function buildForm(type){

	ifExistRemove("#form");

	var submit_button; // Responsável por editar o label do botão.

	if (type == 1){
		action = "javascript:CREATE()";
		submit_button = "Save";
	} else if (type == 2){
		action = "javascript:UPDATE()";
		submit_button = "Edit";
	}

	var $div = $('<div />').appendTo('body');
	$div.attr('id', 'form');

	var form = $('<form action="'+action+'"><br>' +
					 '<spam><strong>Formulário de Tipo Jogos</strong></spam><br>' +
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

function fillForm(id){

	ifExistRemove("#form");
	// In buildForm function, 2 its a parameter who means update.
	buildForm(2);

	global_id = id; // Atribui o valor do ID atual a uma variável global.

	$.getJSON("http://localhost:8080/Bloomer-CORE/" +entity+ "/" +id,

		function(json){

			$("input[name='nome']").val(json.nome);
			$("input[name='descricao']").val(json.descricao);
			$("input[name='autor']").val(json.autor);
			$('select[name="plataforma"]').val(json.plataforma);

			get_version = json.version; // Atribui o valor do version a uma variável global.

			// O back-end está retornando um string em vez de um array em niveisTaxonomia,
			// portanto, retiro o primeiro e o último caractere e transformo em array.
			var arrayNiveisTaxonomia = json.niveisTaxonomia.slice(1, json.niveisTaxonomia.length - 1).split(', ');

			for(var i=0; i < arrayNiveisTaxonomia.length; i++){
				if (arrayNiveisTaxonomia[i] == $("#niveisTaxonomia").children("#Conhecimento").val()) { $("#niveisTaxonomia").children("#Conhecimento").attr('checked', true) };
				if (arrayNiveisTaxonomia[i] == $("#niveisTaxonomia").children("#Compreensao").val()) { $("#niveisTaxonomia").children("#Compreensao").attr('checked', true) };
				if (arrayNiveisTaxonomia[i] == $("#niveisTaxonomia").children("#Aplicacao").val()) { $("#niveisTaxonomia").children("#Aplicacao").attr('checked', true) };
				if (arrayNiveisTaxonomia[i] == $("#niveisTaxonomia").children("#Analise").val()) { $("#niveisTaxonomia").children("#Analise").attr('checked', true) };
				if (arrayNiveisTaxonomia[i] == $("#niveisTaxonomia").children("#Sintese").val()) { $("#niveisTaxonomia").children("#Sintese").attr('checked', true) };
				if (arrayNiveisTaxonomia[i] == $("#niveisTaxonomia").children("#Avaliacao").val()) { $("#niveisTaxonomia").children("#Avaliacao").attr('checked', true) };''
			}
		}
	);
};

function ifExistRemove(element){
	if($(element) !== null){
		$(element).remove();
	}
}