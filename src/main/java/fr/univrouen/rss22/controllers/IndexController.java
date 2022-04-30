package fr.univrouen.rss22.controllers;

import fr.univrouen.rss22.model.FormField;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/help")
    public ModelAndView help() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("help");
        return modelAndView;
    }


    @GetMapping("/insertArticles")
    public ModelAndView insert(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        model.addAttribute("formField", new FormField());
        modelAndView.setViewName("insertArticles");
        return modelAndView;
    }


}