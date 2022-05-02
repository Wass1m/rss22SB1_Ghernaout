package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.model.FormField;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class IndexController {

    //   Retourne la page index / home
    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }


    //   Retourne la page d'aide
    @GetMapping("/help")
    public ModelAndView help() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("help");
        return modelAndView;
    }

    //Retourne la page d'insertion des articles
    @GetMapping("/insertArticles")
    public ModelAndView insert(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("formField", new FormField());
        modelAndView.setViewName("insertArticles");
        return modelAndView;
    }


}