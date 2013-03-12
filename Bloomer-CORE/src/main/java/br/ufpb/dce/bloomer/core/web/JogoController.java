package br.ufpb.dce.bloomer.core.web;

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

@RequestMapping("/jogos")
@Controller
@RooWebScaffold(path = "jogos", formBackingObject = Jogo.class)
@RooWebJson(jsonObject = Jogo.class)
public class JogoController {

	@RequestMapping(value = "/{idJogo}/desempenho/{idUsuario}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> desempenho(@PathVariable("idJogo") Long idJogo,
			@PathVariable("idUsuario") Long idUsuario, Model uiModel) {

		Jogo jogo = Jogo.findJogo(idJogo);
		String json = "{ \"nomeJogo\": \"" + jogo.getNome() + "\"}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		
		return new ResponseEntity<String>(json, headers,
				HttpStatus.OK);
	}
}
