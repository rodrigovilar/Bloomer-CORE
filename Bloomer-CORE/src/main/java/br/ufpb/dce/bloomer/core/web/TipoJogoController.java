package br.ufpb.dce.bloomer.core.web;

import java.util.List;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufpb.dce.bloomer.core.model.TipoJogo;

@RequestMapping("/tipojogoes")
@Controller
@RooWebScaffold(path = "tipojogoes", formBackingObject = TipoJogo.class)
@RooWebJson(jsonObject = TipoJogo.class)
public class TipoJogoController {

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<TipoJogo> listaDeTipoJogos = TipoJogo.findAllTipoJogoes();

		ArrayNode arrayDeTipoJogos = JsonNodeFactory.instance.arrayNode();

		for (TipoJogo tipoJogo : listaDeTipoJogos) {
			ObjectNode noTipoJogo = JsonNodeFactory.instance.objectNode();

			noTipoJogo.put("id", tipoJogo.getId());
			noTipoJogo.put("nome", tipoJogo.getNome());
			noTipoJogo.put("autor", tipoJogo.getAutor());
			noTipoJogo.put("descricao", tipoJogo.getDescricao());
			noTipoJogo.put("version", tipoJogo.getVersion());
			noTipoJogo.put("niveisTaxonomia", tipoJogo.getNiveisTaxonomia()
					.toString());

			arrayDeTipoJogos.add(noTipoJogo);
		}

		return new ResponseEntity<String>(arrayDeTipoJogos.toString(), headers,
				HttpStatus.OK);
	}
	

}
