package br.ufpb.dce.bloomer.core.web;

import java.util.Calendar;
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

import br.ufpb.dce.bloomer.core.model.Usuario;

@RequestMapping("/usuarios")
@Controller
@RooWebScaffold(path = "usuarios", formBackingObject = Usuario.class)
@RooWebJson(jsonObject = Usuario.class)
public class UsuarioController {
	//TODO
	//Jogos configurados por esse cara
	//Seguidores desse cara
	
	
	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Usuario> listaDeUsuarios = Usuario.findAllUsuarios();
        
        ArrayNode arrayDeUsuarios = JsonNodeFactory.instance.arrayNode();

		for (Usuario usuario : listaDeUsuarios) {
			ObjectNode noUsuario = JsonNodeFactory.instance.objectNode();
			
			noUsuario.put("id", usuario.getId());
			noUsuario.put("nome", usuario.getNome());
			noUsuario.put("login", usuario.getLogin());
			noUsuario.put("senha", usuario.getSenha());
			noUsuario.put("sexo", usuario.getSexo().toString());
			
			//Montando o formato da data de resposta no JSON
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(usuario.getDataNascimento().get(Calendar.DATE) + "/");
			strBuilder.append(usuario.getDataNascimento().get(Calendar.MONTH) + "/");
			strBuilder.append(usuario.getDataNascimento().get(Calendar.YEAR));
			
			noUsuario.put("dataNascimento", strBuilder.toString());
			noUsuario.put("version", usuario.getVersion());

			arrayDeUsuarios.add(noUsuario);
		}
        return new ResponseEntity<String>(arrayDeUsuarios.toString(), headers, HttpStatus.OK);
    }
	
	
	
}
