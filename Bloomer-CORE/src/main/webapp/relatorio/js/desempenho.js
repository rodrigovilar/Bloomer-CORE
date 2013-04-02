/*
* Bloomer - Desempenho
*/

/* Selectors */

var nomeJogador; 
var nomeJogo; 


$(document).ready(function(){
	$("#jump_menu_jogos").hide();
	$("#montar_tabela").hide();
	$("#montar_grafico").hide();
	getSeguidores();
});

$("#jump_menu_usuarios").ready(function(){
	$("#jump_menu_usuarios").change(function(){

		var path;

		switch($(this).val()){
				case "1":
					path = "json/rodrigo/jogos.json";
				break;

				case "2":
					path = "json/vandhuy/jogos.json";
				break;

				case "3":
					path = "json/anderson/jogos.json";
				break;
		}

		ajax(path);
	});
});

$("#jump_menu_jogos").ready(function(){
	$("#jump_menu_jogos").change(function(){
		$("#montar_tabela").show('slow');
		$("#montar_grafico").show('slow');
	});
});

$("#montar_tabela").ready(function(){
	$("#montar_tabela").click(function(){
		$.getJSON(getDesempenho($("#jump_menu_jogos").val()), function(json){
			montarTabela(json);

		});
	});
});

$("#montar_grafico").ready(function(){
	$("#montar_grafico").click(function(){
		$.getJSON(getDesempenho($("#jump_menu_jogos").val()), function(json){
			montarGrafico(json);
		});
		
	});
});

/* Functions */

function getSeguidores(){

	$.getJSON('json/usuarios.json', function(json){



		var options = '<option value="null">Escolha um usuário...</option>';

		for (i = 0; i < json.length; i++){
			options += '<option value="' + json[i].id + '">' + json[i].nome + '</option>';
			nomeJogador = json[i].nome; 
		}

		$("#jump_menu_usuarios").html(options);
	});
};

function getDesempenho(value){

	switch(value){
				case "1":
					var path = "json/rodrigo/desempenho.json";
				break;

				case "2":
					var path = "json/vandhuy/desempenho.json";
				break;

				case "3":
					var path = "json/anderson/desempenho.json";
				break;
	}

	return path;
};

function ajax(path){

	$.ajax({
		//type: "GET",
		url: path,
		//data: {nome: value},
		dataType: "json",
		success: function(json){

			var options = '<option value="null">Escolha um jogo...</option>';

			for (i = 0; i < json.length; i++){
				options += '<option value="' + json[i].id + '">' + json[i].nome + '</option>';
				nomeJogo = json[i].nome; 
			}

			$("#jump_menu_jogos").html(options);
		}
	});

	if($('#jump_menu_jogos').is(':hidden')) {
    	$("#jump_menu_jogos").show('slow');
	};
};

function montarTabela(json){

	if(document.getElementById("tabela_usuario") !== null){
		$("#tabela_usuario").remove();
	}

	var table = $('<table widtd="1000" border="1" cellspacing="0" style="text-align:center"></table>').attr('id', 'tabela_usuario');

	var header = '<tr><th rowspan="2">Data/Hora</th><th rowspan="2">Acertos (%)</th><th rowspan="2">Concluiu</th><th rowspan="2">Escore</th>';

	for (h = 0; h < json.questoes.length; h++){
		header += '<th style="line-height:0" rowspan="2"><h5>Questão ' + (h + 1) + '</h4><h5>Gabarito: ' + json.questoes[h].gabarito + '</h4></th>';
	}

	header += '</tr><tr></tr>';
	table.append(header);


	for (j=0; j < json.partidas.length; j++){
	    var row = '<tr><td>' + json.partidas[j].dataHora + '</td><td>' + json.partidas[j].acerto +
	    			'</td><td>' + json.partidas[j].concluiu + '</td><td>' + json.partidas[j].escore + '</td>';

	    for (k=0; k < json.partidas[0].respostas.length; k++){
	    	row += '<td>' + json.partidas[0].respostas[k].conteudo + '</td>';
	    }

	    row += '</tr>';
	    table.append(row);
	}

	$("#tabela_desempenho").append(table);
};



function montarGrafico(json){

	if(document.getElementById("grafico") !== null){
		$("#grafico").remove();
	}

	var $div = $('<div /> <br/><br/>').appendTo('body');
	$div.attr('id', 'grafico');
	$div.css('height', '500px');
	$div.css('width', '700px');


// Alocando os arrays  
	var lineX = new Array(json.partidas.length);
	var lineY = new Array(json.partidas.length); 

		for (var i = 0; i < json.partidas.length; i++) {
			lineX[i] = new Array(2); 
			lineY[i] = new Array(2);
		}

	for (var j=0; j < json.partidas.length; j++){

				lineX [j][0] = json.partidas[j].dataHora; 
				lineX [j][1] = json.partidas[j].escore;
				lineY [j][0] = j + 1;
				lineY [j][1] = json.partidas[j].acerto; 
	}	


	var title = 'Gráfico do Desempenho </br>';
	title += 'Usuário: '+ nomeJogador + '  - Jogo: ' + nomeJogo ;

	var line = [['DataPartida', 7], ['DataPartida1', 9], ['DataPartida2', 15],
    ['DataPartida3', 12], ['DataPartida4', 3], ['DataPartida5', 6], ['DataPartida6', 18]];

    var line2 = [['Nickle', 28], ['Aluminum', 13], ['Xenon', 74], ['Silver', 47],
    ['Sulfer', 16], ['Silicon', 14], ['Vanadium', 23]];

    var plot4 = $.jqplot('grafico', [lineX, lineY], {
        title: title,
        series:[{renderer:$.jqplot.BarRenderer}, {xaxis:'x2axis', yaxis:'y2axis'}],
        axes: {
            xaxis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                label: 'Data das Partidas',
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                tickOptions: {
                    angle: 20
                }
            },
            x2axis: {
                renderer: $.jqplot.CategoryAxisRenderer,
                label: 'Partida',
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                tickOptions: {
                    angle: 0
                }
            },
            yaxis: {
                autoscale:true,
                label: 'Acertos',
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                tickOptions: {
                    angle: 30
                }
            },
            y2axis: {
                autoscale:true,
                label: 'Score',
                labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                tickOptions: {
                    angle: 30
                }
            }
        }
    });
};