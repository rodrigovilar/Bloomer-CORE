package br.ufpb.dce.bloomer.core.web;

import br.ufpb.dce.bloomer.core.model.Questao;
import br.ufpb.dce.bloomer.core.model.TipoQuestao;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/tipoquestaos")
@Controller
@RooWebScaffold(path = "tipoquestaos", formBackingObject = TipoQuestao.class)
@RooWebJson(jsonObject = TipoQuestao.class)
public class TipoQuestaoController {
}
