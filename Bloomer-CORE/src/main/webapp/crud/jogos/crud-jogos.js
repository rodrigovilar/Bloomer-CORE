/*
* Bloomer - CRUD - Jogos
*/

var entity = "jogos";
var global_id = " ";
var version_json = " ";

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
			alert("Jogo Criado.");
			Read();
		}
	});
};

function Read (){

	if_exists_remove("#list_table");
	if_exists_remove("#form");


	$.getJSON("http://localhost:8080/Bloomer-CORE/"+ entity,


		function(json){

			console.log(json);

			var $div = $('<div />').appendTo('body');
			$div.attr('id', 'list');

			var table = $('<table border="1" cellspacing="0" style="text-align:center"></table>').attr('id', 'list_table');

			var title = '<tr><td colspan="8"><a href="#" onclick="Form(1);"><img src="../images/create.png"/>Add</a></td></tr>';
			table.append(title);

			var header = '<tr><th>ID</th><th>Nome</th><th>Edit</th><th>Delete</th></tr>';
			table.append(header);

			for (i=0; i < json.length; i++){
			    var row = '<tr><td>' + json[i].id + '</td><td>' + json[i].nome +
			    		  '</td><td><a href="#" onclick="fill(' + json[i].id + ','+ json[i].version +');"><img src="../images/update.png"/></a>' +
			    		  '</td><td><a href="#" onclick="Delete(' + json[i].id + ');"><img src="../images/delete.png"/></a>' +
			    		  '</td></tr>';

			    table.append(row);
			}

			$("#list").append(table);
		}
	);
};

function Update(){

	alert($('select[name="autor"]').val());

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
			alert("Jogo Editado.");
			Read();
		}
	});
}

function Delete (id){

	var answer = confirm("Delete Jogo id: "+id+"?");

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

	var id = " ";
	var ver = " ";

	if(version_json != " "){
		ver = ',"version" : '+ver;
	}

	if(global_id != " "){
		id = '"id" : '+global_id+', ';
	}

	var the_json = '{'+id +'"nome":"'+ $('input[name="nome"]').val() +
	'", "autor":"'+ $('select[name="autor"]').val() +
	'", "plataforma":"'+ $('select[name="tipo"]').val()+'"'+ ver + '}';

	alert(the_json); // testando formato do json

	//return the_json;
}

function Form (type){

	if_exists_remove("#form");

	var action;
	var submit_button;
	if (type == 1){
		action = "javascript:Create()";
		submit_button = "Save";
	} else if (type == 2){
		action = "javascript:Update()";
		submit_button = "Edit";
	}

	$.getJSON("http://localhost:8080/Bloomer-CORE/tipojogoes",
		function(json){
			var options = '<option value="null">Escolha um Tipo...</option>';
			for (i = 0; i < json.length; i++){
				options += '<option value="' + json[i].id + '">' + json[i].nome + '</option>';
			}
			$("#tipo").html(options);
	});

	$.getJSON("http://localhost:8080/Bloomer-CORE/usuarios",
		function(json){
			var options = '<option value="null">Escolha um Autor...</option>';
			for (i = 0; i < json.length; i++){
				options += '<option value="' + json[i].id + '">' + json[i].nome + '</option>';
			}
			$("#autor").html(options);
		});

	var $div = $('<div />').appendTo('body');
	$div.attr('id', 'form');

	var form = $('<form action="'+action+'"><br>' +
					 '<spam><strong>Formulário de Jogos</strong></spam><br>' +
					 '<input name="nome" type="text" placeholder="Nome" /><br>' +
					 '<spam><strong>Tipo</strong></spam><br>' +
					 '<select id="tipo" name="tipo"></select><br>' +
					 '<spam><strong>Autor</strong></spam><br>' +
					 '<select id="autor" name="autor"></select><br>' +
					 '<input value="'+submit_button+'" type="submit" />' +
				 '</form>');

	$("#form").append(form);

};

function fill (id, version){

	if_exists_remove("#form");
	Form(2);

	global_id = id;
	version_json = version; // teste passando o version

	$.getJSON("http://localhost:8080/Bloomer-CORE/" +entity+ "/" +id,

		function(json){
			$('select[name="tipo"]').val(json.tipo); // não vai preencher pois não vem nd do back
			$('select[name="autor"]').val(json.autor); // mesma coisa não vai preencher
		}
	);
};

function if_exists_remove(element){
	if($(element) !== null){
		$(element).remove();
	}

}
