test('tabela do desempenho', function() {

	var json = {
	    "nomeJogo": "Jogo Entrando pelo Cano",
	    "questoes": [
	        {
	            "id": 1,
	            "gabarito": "new Facada();"
	        },
	        {
	            "id": 2,
	            "gabarito": "string"
	        },
	        {
	            "id": 3,
	            "gabarito": "package"
	        },
	        {
	            "id": 4,
	            "gabarito": "method"
	        }
	    ],
	    "partidas": [
	        {
	            "idPartida": 1,
	            "dataHora": "13/03/2012 21:00",
	            "acerto": 100,
	            "concluiu": "TRUE",
	            "escore": 1030,
	            "respostas": [
	                {
	                    "id": 1,
	                    "conteudo": "Fachada",
	                    "idQuestao": 1
	                },
	                {
	                    "id": 2,
	                    "conteudo": "string",
	                    "idQuestao": 2
	                },
	                {
	                    "id": 3,
	                    "conteudo": "private",
	                    "idQuestao": 3
	                },
	                {
	                    "id": 4,
	                    "conteudo": "construct",
	                    "idQuestao": 4
	                }
	            ]
	        }
	    ]
	};

	montarTabela(json);

	equal($('#tabela_desempenho table').length, 1, "tabela adicionada ao DOM");
	var linhas = $('#tabela_desempenho table tbody tr');
	equal(linhas.length, 3, "quantidade de linhas correta");
	var celulas = linhas[2].children;
	equal(celulas.length, 8, "quantidade de colunas correta");
	equal($(celulas[0]).html(), '13/03/2012 21:00', 'conteudo Data/Hora ok');
	equal($(celulas[1]).html(), '100', 'conteudo Acertos (%) ok');
	equal($(celulas[2]).html(), 'TRUE', 'conteudo Concluiu ok');
	equal($(celulas[3]).html(), '1030', 'conteudo Escore ok');
	equal($(celulas[4]).html(), 'Fachada', 'conteudo Questão 1 ok');
	equal($(celulas[5]).html(), 'string', 'conteudo Questão 2 ok');
	equal($(celulas[6]).html(), 'private', 'conteudo Questão 3 ok');
	equal($(celulas[7]).html(), 'construct', 'conteudo Questão 4 ok');
});