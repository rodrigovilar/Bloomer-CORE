package br.ufpb.dce.bloomer.core.web;

import br.ufpb.dce.bloomer.core.model.Jogo;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/jogoes")
@Controller
@RooWebScaffold(path = "jogoes", formBackingObject = Jogo.class)
public class JogoController {
}
