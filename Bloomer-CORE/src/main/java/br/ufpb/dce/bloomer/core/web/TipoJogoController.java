package br.ufpb.dce.bloomer.core.web;

import java.util.List;

import br.ufpb.dce.bloomer.core.model.TipoJogo;
import br.ufpb.dce.bloomer.core.model.Usuario;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
			noTipoJogo.put("niveisDaTaxonomia", tipoJogo.getNiveisTaxonomia().toString());

			arrayDeTipoJogos.add(noTipoJogo);
		}	
			
		return new ResponseEntity<String>(arrayDeTipoJogos.toString(),
				headers, HttpStatus.OK);
	}
	
	   @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
	        	      	        
	        TipoJogo novo = TipoJogo.fromJsonToTipoJogo(json);
	        TipoJogo salvo = TipoJogo.findTipoJogo(novo.getId());
	        
	        if (salvo == null) {
	            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
	        }
	        
	        salvo.setNome(novo.getNome());
	        salvo.setAutor(novo.getAutor());
	        salvo.setDescricao(novo.getDescricao());
	        salvo.setNiveisTaxonomia(novo.getNiveisTaxonomia());
	        salvo.flush();
	        return new ResponseEntity<String>(headers, HttpStatus.OK);
	    }
	    
	    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json");
	        for (TipoJogo tipoJogo: TipoJogo.fromJsonArrayToTipoJogoes(json)) {
	            if (tipoJogo.merge() == null) {
	                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
	            }
	        }
	        return new ResponseEntity<String>(headers, HttpStatus.OK);
	    }
	
   

}
