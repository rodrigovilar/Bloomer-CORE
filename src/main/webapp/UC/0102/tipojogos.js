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

			var $div = $('<div />').appendTo('.container');
			$div.attr('id', 'list');

			var table =$('<table></table>').attr('class', 'table table-striped');

			// In buildForm function, 1 its a parameter who means CREATE.
			var title = '<tr><td colspan="8"><button class="btn btn-primary" onclick="buildForm(1);"><img src="../../images/create.png"/>Add</button></td></tr>';
			table.append(title);

			var header = '<tr><th>ID</th><th>Nome</th><th>Descrição</th><th>Autor</th><th>Plataforma</th><th>Nível de Taxonomia</th><th>Edit</th><th>Delete</th></tr>';
			table.append(header);

			for (i=0; i < json.length; i++){
			    var row = '<tr><td>' + json[i].id + '</td><td>' + json[i].nome + '</td><td>' + json[i].descricao +
			    		  '</td><td>' + json[i].autor + '</td><td>' + json[i].plataforma + '</td><td>' + json[i].niveisTaxonomia +
			    		  '</td><td><button class="btn btn-info" onclick="fillForm(' + json[i].id + ');"><img src="../../images/update.png"/></button>' +
			    		  '</td><td><button class="btn btn-info" onclick="DELETE(' + json[i].id + ');"><img src="../../images/delete.png"/></button>' +
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

	
	var id = ''; // Responsável por armazenar o valor do id a ser enviado no JSON.
	var version = ''; // Responsável por armazenar o valor do version a ser enviado no JSON.


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
	'", "autor":"'+ $('select[name="autor"]').val() +
	'", "plataforma":"'+ $('select[name="plataforma"]').val() +
	'", "niveisTaxonomia":[' +$('select[name="niveisTaxonomia"]').val() + '", ';

	the_json += ']}';

	return the_json;
}

function buildForm(type){

	ifExistRemove("#form");

	ifExistRemove("#list"); // remove a tabela para gerar os forms 

	var submit_button; // Responsável por editar o label do botão.

	if (type == 1){
		action = "javascript:CREATE()";
		submit_button = "Save";
	} else if (type == 2){
		action = "javascript:UPDATE()";
		submit_button = "Edit";
	}

	var $div = $('<div />').appendTo('.container');
	$div.attr('id', 'form');

	var form = $('<div class="well"><form id="Formulario" action="'+action+'"><br>' +

					'<fieldset>'+ 
					'<legend>Cadastro TipoJOGO</legend>'+

				
					 '<input name="nome" type="text" placeholder="Nome"/><br>' +
					 '<input name="descricao" type="text" placeholder="Descrição" /><br>' +
					 '<select name="autor"><option>Autor</option></select><br>' +
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
					 '<select id="niveisTaxonomia">' +
						 '<option value="Conhecimento"> Conhecimento</option>' +
						 '<option value="Compreensao"> Compreensão</option>' +
						 '<option  value="Aplicacao"> Aplicação</option>' +
						 '<option value="Analise"> Análise</option>' +
						 '<option id="Sintese" value="Sintese"> Síntese</option>' +
						 '<option id="Avaliacao" value="Avaliacao"> Avaliação</option>' +
				 	 '</select>' +
					 
					 '</fieldset>'+
					 '<fieldset>'+
					 '<legend>Cadastro TipoQuestão</legend>'+

	 				
	 				 '<form>' +
					 '<input name="nomeQuestao" type="text" placeholder="Nome" /><br>' +
					 '<textarea name="descricaoQuestao" rows="4" placeholder="Descrição "></textarea><br>' +
					 '<a class="criar" onclick = criarQ()>Criar</a>'+
					 '</form>'+ 
					 '<div class="listQuestaos"></div>'+

					 '<button id="esconder" type="submit" class="btn btn-primary">'+submit_button+'</button>' +
				 	 '</fieldset>'+
			
				 '</form> </div>');

	$("#form").append(form);

	$("#Formulario").formToWizard({ submitButton: 'esconder' });

	// Preenche a tabela com os tiposQuestões
	READ_1(); 

	PreencheUsuario();

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
			$("select[name='autor']").val(json.autor);
			$('select[name="plataforma"]').val(json.plataforma);

			get_version = json.version; // Atribui o valor do version a uma variável global.

		}
	);
};

function ifExistRemove(element){
	if($(element) !== null){
		$(element).remove();
	}
}


function CriarTJ(){
	buildJSON();
	CREATE();
}

function PreencheUsuario(){
			  
	$.getJSON("http://localhost:8080/Bloomer-CORE/usuarios",
		function(json){

			for (i=0; i < json.length; i++){
			    var row = '<option value="'+json[i].nome+'">' + json[i].nome + '</option>';
			    $("select[name='autor']").append(row);
			}

			$("#list").append(table);
		}
	);
};
