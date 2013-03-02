package br.ufpb.dce.bloomer.core.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufpb.dce.bloomer.core.model.Questao;

@RequestMapping("/questaos")
@Controller
@RooWebScaffold(path = "questaos", formBackingObject = Questao.class)
public class QuestaoController {
}
