package br.ufpb.dce.bloomer.core.web;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufpb.dce.bloomer.core.model.Jogo;
import br.ufpb.dce.bloomer.core.model.Partida;
import br.ufpb.dce.bloomer.core.model.Questao;
import br.ufpb.dce.bloomer.core.model.Resposta;
import br.ufpb.dce.bloomer.core.model.Usuario;

@RequestMapping("/jogos")
@Controller
@RooWebScaffold(path = "jogos", formBackingObject = Jogo.class)
@RooWebJson(jsonObject = Jogo.class)
public class JogoController {

	@RequestMapping(value = "/{idJogo}/desempenho/{idUsuario}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> desempenhoUnico(@PathVariable("idJogo") Long idJogo,
			@PathVariable("idUsuario") Long idUsuario, Model uiModel) {
		
		Jogo jogo = Jogo.findJogo(idJogo);
		Usuario usuario = Usuario.findUsuario(idUsuario);
		
		
		List<Partida> partidas = 
				Partida.findPartidasByJogoAndUsuario(jogo, usuario).getResultList();
		
		ObjectNode noJogo = JsonNodeFactory.instance.objectNode();
		noJogo.put("nomeJogo", jogo.getNome());

		ArrayNode noQuestoes = JsonNodeFactory.instance.arrayNode();
		noJogo.put("questoes", noQuestoes);
		
		ArrayNode noPartidas = JsonNodeFactory.instance.arrayNode();
		noJogo.put("partidas", noPartidas);
		
		
		for (Questao questao : jogo.getQuestoes()) {
			ObjectNode noQuestao = JsonNodeFactory.instance.objectNode();
			noQuestao.put("id", questao.getId());
			noQuestao.put("gabarito", questao.getGabarito());
			noQuestoes.add(noQuestao);
		}
		
		for (Partida partida : partidas) {
			ObjectNode noPartida = JsonNodeFactory.instance.objectNode();
			noPartida.put("idPartida", partida.getId());
			
			String formatoData = "dd/MM/yyyy";  
			String formatoHora = "h:mm - a";  
			String dataFormatada, horaFormatada;  
			  
			dataFormatada = new SimpleDateFormat(formatoData).format(partida.getDataHora().getTime());
			horaFormatada = new SimpleDateFormat(formatoHora).format(partida.getDataHora().getTime());
		
			noPartida.put("dataHora", dataFormatada + " " + horaFormatada);
			noPartida.put("acerto", partida.getAcerto());
			noPartida.put("concluiu", partida.getConcluiu());
			noPartida.put("escore", partida.getEscore());
			
			ArrayNode noRespostas = JsonNodeFactory.instance.arrayNode();
			noPartida.put("respostas", noRespostas);
			
			for (Resposta resposta : partida.getRespostas()){
				ObjectNode noResposta = JsonNodeFactory.instance.objectNode();
				noResposta.put("id",resposta.getId());
				noResposta.put("conteudo", resposta.getConteudo());
				noResposta.put("idQuestao", resposta.getQuestao().getId());
				noRespostas.add(noResposta);
			}
			
			noPartidas.add(noPartida);
			
		}
		//Pesquisar as partidas por idJogo e idUsuario
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		return new ResponseEntity<String>(noJogo.toString(), headers,
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{idJogo}/desempenho", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> desempenhoGeral(@PathVariable("idJogo") Long idJogo, Model uiModel) {
		
		Jogo jogo = Jogo.findJogo(idJogo);
		String json = "{ \"nomeJogo\": \"" + jogo.getNome() + "\"}";

		//Pesquisar as partidas por idJogo

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		return new ResponseEntity<String>(json, headers,
				HttpStatus.OK);
	}
		
}
