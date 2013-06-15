/*
* Bloomer - CRUD - Usuário
*/

var entity = "usuarios";

$(document).ready(function(){
	List();
});

function List (){

	if($("#list") !== null){
		$("#list").remove();
	}

	$.ajax({
		type: "GET",
		url: "http://localhost:8080/Bloomer-CORE/"+ entity,
		dataType: "json",
		success: function(json){
			
			var $div = $('<div />').appendTo('body');
			$div.attr('id', 'list');

			var table = $('<table border="1" cellspacing="0" style="text-align:center"></table>').attr('id', 'list_table');

			var title = '<tr><td colspan="8"><a href="#" onclick="Create();"><img src="../images/create.png"/>Add</a></td></tr>';
			table.append(title);

			var header = '<tr><th>ID</th><th>Nome</th><th>Data de Nascimento</th><th>Sexo</th><th>Login</th><th>Show</th><th>Edit</th><th>Delete</th></tr>';
			table.append(header);

			for (i=0; i < json.length; i++){
			    var row = '<tr><td>' + json[i].id + '</td><td>' + json[i].nome + '</td><td>' + json[i].dataDeNascimento +
			    		  '</td><td>' + json[i].sexo + '</td><td>' + json[i].login +
			    		  '</td><td><a href="#" onclick="Show(' + json[i].id + ');"><img src="../images/show.png"/></a>' + 
			    		  '</td><td><a href="#" onclick="Update(' + json[i].id + ');"><img src="../images/update.png"/></a>' +
			    		  '</td><td><a href="#" onclick="Delete(' + json[i].id + ');"><img src="../images/delete.png"/></a>' +
			    		  '</td></tr>';

			    table.append(row);
			}

			$("#list").append(table);
		}
	});
};

function Create (){

	alert("create");

	/*$.ajax({
		type: "GET",
		url: "http://localhost:8080/Bloomer-CORE/"+ entity +"/"+ id,
		dataType: "json",
		success: function(json){
			alert(json.id + json.nome + json.sexo);
		}
	});*/
};

function Show (id){

	alert('Backend não aceita um GET em ' +entity+ '/id');

	/*$.ajax({
		type: "GET",
		url: "http://localhost:8080/Bloomer-CORE/"+ entity +"/"+ id,
		dataType: "json",
		success: function(json){
			alert(json.id + json.nome + json.sexo);
		}
	});*/
};

function Update (id){
	alert(id);

	/*$.ajax({
		type: "GET",
		url: "http://localhost:8080/Bloomer-CORE/usuarios",
		dataType: "json",
		success: function(json){
			alert();
		}
	});*/
};

function Delete (id){	
	
	var answer = confirm("Delete user id: "+id+"?");

	if (answer){
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8080/Bloomer-CORE/" +entity+ "/" +id,
			dataType: "json",
			success: function(json){
				List();
			}
		});
	}
};