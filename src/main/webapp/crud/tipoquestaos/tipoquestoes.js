/*
* Bloomer - CRUD - Tipo Questões
*/


var entity = "tipoquestaos"; // O string referente a entidade fica armazenado em uma variável global.
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
			alert("Tipo Questão Criada com Sucesso.");
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
			var title = '<tr><td colspan="6"><a href="#" onclick="buildForm(1);"><img src="../images/create.png"/>Add</a></td></tr>';
			table.append(title);

			var header = '<tr><th>ID</th><th>Nome</th><th>Descrição</th><th>ID do Tipo Jogo</th><th>Edit</th><th>Delete</th></tr>';
			table.append(header);

			for (i=0; i < json.length; i++){
			    var row = '<tr><td>' + json[i].id + '</td><td>' + json[i].nome + '</td><td>' + json[i].descricao +
			    		  '</td><td>' + json[i].tipojogo + '</td><td><a href="#" onclick="fillForm(' + json[i].id + ');"><img src="../images/update.png"/></a>' +
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
			alert("Tipo Questão Editada com Sucesso.");
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
	if(global_id == " "){
		id = '';
	} else {
		id = '"id":' + global_id + ', ';
	}

	// Caso o usuário esteja editando um registro, esta condição adiciona a variável 'version' ao JSON.
	if(action == "javascript:UPDATE()"){
		version = '"version": ' + get_version + ', ';
	}

	var the_json = '{' + id + version + '"nome":"'+ $('input[name="nome"]').val() +
	'", "descricao":"'+ $('input[name="descricao"]').val() +
	'", "tipojogo":"'+ $('input[name="tipojogo"]').val() + '"}';

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
					 '<spam><strong>Formulário de Tipo Questões</strong></spam><br>' +
					 '<input name="nome" type="text" placeholder="Nome" /><br>' +
					 '<input name="descricao" type="text" placeholder="Descrição" /><br>' +
					 '<input name="tipojogo" type="text" placeholder="ID do Tipo Jogo" /><br>' +
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
			$("input[name='tipojogo']").val(json.tipojogo);

			get_version = json.version; // Atribui o valor do version a uma variável global.
		}
	);
};

function ifExistRemove(element){
	if($(element) !== null){
		$(element).remove();
	}
}