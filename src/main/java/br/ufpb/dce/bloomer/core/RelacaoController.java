package br.ufpb.dce.bloomer.core;

import br.ufpb.dce.bloomer.core.model.Relacao;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = Relacao.class)
@Controller
@RequestMapping("/relacaos")
public class RelacaoController {
}
