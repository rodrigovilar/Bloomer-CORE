package br.ufpb.dce.bloomer.core.web;

import br.ufpb.dce.bloomer.core.model.Resposta;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/respostas")
@Controller
@RooWebScaffold(path = "respostas", formBackingObject = Resposta.class)
public class RespostaController {
}
