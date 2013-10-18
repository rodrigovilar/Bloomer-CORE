/*
* Bloomer - CRUD - Tipo Questões
*/

var entityQuestoes = "tipoquestaos"; // O string referente a entidade fica armazenado em uma variável global.
var global_id_1 = " "; // O valor do ID atual fica armazenado em uma variável global.
var get_version_1 = " "; // O valor do version atual fica armazenado em uma variável global.
var action_1; // Responsável por definir se o parâmetro action_1 irá chamar CREATE_1 ou UPDATE_1.


function CREATE_1(){
	$.ajax({
		type: "POST",
		url: "http://localhost:8080/Bloomer-CORE/"+ entityQuestoes,
		data: buildJSON_1(),
		dataType: "json",
		contentType: "application/json",
		processData: true,
        headers: {
          Accept: "application/json"
        },
		complete: function(json){
			alert("Tipo Questão Criada com Sucesso.");
			READ_1();
		}
	});
};

function READ_1(){

	//ifExistRemove("#list_table_1");
	ifExistRemove("#list_1");

	$.getJSON("http://localhost:8080/Bloomer-CORE/"+ entityQuestoes,
		function(json){

			var $div = $('<div />').appendTo('.listQuestaos');
			$div.attr('id', 'list_1');

			var table =$('<table></table>').attr('class', 'table table-striped');

			// In buildForm_1 function, 1 its a parameter who means CREATE_1.
			//var title = '<tr><td colspan="6"><a href="#" onclick="buildForm_1(1);"><img src="../../images/create.png"/>Add</a></td></tr>';
			//table.append(title);

			var header = '<tr><th>ID</th><th>Nome</th><th>Descrição</th><th>ID do Tipo Jogo</th><th>Edit</th><th>Delete</th></tr>';
			table.append(header);

			for (i=0; i < json.length; i++){
			    var row = '<tr><td>' + json[i].id + '</td><td>' + json[i].nome + '</td><td>' + json[i].descricao +
			    		  '</td><td>' + json[i].tipojogo + '</td><td><a href="#" onclick="fillForm_1(' + json[i].id + ');"><img src="../../images/update.png"/></a>' +
			    		  '</td><td><a href="#" onclick="DELETE(' + json[i].id + ');"><img src="../../images/delete.png"/></a>' +
			    		  '</td></tr>';

			    table.append(row);
			}

			$("#list_1").append(table);
		}
	);
};

function UPDATE_1(){
	$.ajax({
		type: "PUT",
		url: "http://localhost:8080/Bloomer-CORE/"+ entityQuestoes,
		data: buildJSON_1(),
		dataType: "json",
		contentType: "application/json",
		processData: true,
        headers: {
          Accept: "application/json"
        },
		complete: function(json){
			alert("Tipo Questão Editada com Sucesso.");
			READ_1();
		}
	});
}

function DELETE(id){

	var answer = confirm("Delete register id: "+id+"?");

	if (answer){
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8080/Bloomer-CORE/" +entityQuestoes+ "/" +id,
			dataType: "json",
			success: function(json){
				READ_1();
			}
		});
	}
};


function criarQ(){
	buildJSON_1();
	CREATE_1();
}

function buildJSON_1(){

	
	var id = ''; // Responsável por armazenar o valor do id a ser enviado no JSON.
	var version = ''; // Responsável por armazenar o valor do version a ser enviado no JSON.

	// Condição para verificar se já existe um global_id_1
	if(global_id_1 == " "){
		id = '';
	} else {
		id = '"id":' + global_id_1 + ', ';
	}

	// Caso o usuário esteja editando um registro, esta condição adiciona a variável 'version' ao JSON.
	if(action_1 == "javascript:UPDATE_1()"){
		version = '"version": ' + get_version_1 + ', ';
	}

	var the_json = '{' + id + version + '"nome":"'+ $('input[name="nomeQuestao"]').val() +
	'", "descricao":"'+ $('textarea[name="descricaoQuestao"]').val() +
	'", "tipojogo": 1}';

	return the_json;
}

function buildForm_1(type){

	ifExistRemove("#form_1");

	var submit_button; // Responsável por editar o label do botão.

	if (type == 1){
		action_1 = "javascript:CREATE_1()";
		submit_button = "Save";
	} else if (type == 2){
		action_1 = "javascript:UPDATE_1()";
		submit_button = "Edit";
	}

	var $div = $('<div />').appendTo('body');
	$div.attr('id', 'form_1');

	var form = $('<form action="'+action_1+'"><br>' +
					 '<spam><strong>Formulário de Tipo Questões</strong></spam><br>' +
					 '<input name="nome" type="text" placeholder="Nome" /><br>' +
					 '<input name="descricao" type="text" placeholder="Descrição" /><br>' +
					 '<input value="'+submit_button+'" type="submit" />' +
				 '</form>');

	$("#form_1").append(form);
};

function fillForm_1(id){

	ifExistRemove("#form_1");
	// In buildForm_1 function, 2 its a parameter who means update.
	//buildForm_1(2);

	global_id_1 = id; // Atribui o valor do ID atual a uma variável global.

	$.getJSON("http://localhost:8080/Bloomer-CORE/" +entityQuestoes+ "/" +id,

		function(json){

			$("input[name='nomeQuestao']").val(json.nome);
			$("input[name='descricaoQuestao']").val(json.descricao);
			
			get_version_1 = json.version; // Atribui o valor do version a uma variável global.
		}
	);
};

function ifExistRemove(element){
	if($(element) !== null){
		$(element).remove();
	}
}
