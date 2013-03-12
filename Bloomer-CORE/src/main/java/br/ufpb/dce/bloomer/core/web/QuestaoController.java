package br.ufpb.dce.bloomer.core.web;

import br.ufpb.dce.bloomer.core.model.Questao;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/questaos")
@Controller
@RooWebScaffold(path = "questaos", formBackingObject = Questao.class)
@RooWebJson(jsonObject = Questao.class)
public class QuestaoController {
}
