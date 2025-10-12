package com.pizzeria.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pizzeria.spring_la_mia_pizzeria_crud.model.Pizza;
import com.pizzeria.spring_la_mia_pizzeria_crud.repository.PizzaRepository;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;

    @GetMapping
    public String index(Model model, @RequestParam (name= "keyword", required = false)String keyword) {
        List<Pizza> result = null;
        if(keyword == null || keyword.isBlank()){
            result = repository.findAll();
        } else {
            result = repository.findByNameContainingIgnoreCase(keyword);
        }

        model.addAttribute("list", result);
        return "pizze/index";
    }
    
    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Optional<Pizza> optionalPizza = repository.findById(id);
        if(optionalPizza.isPresent()){
            model.addAttribute("pizza", optionalPizza.get());
            model.addAttribute("empty", false);
        }else {
            model.addAttribute("empty", true);
        }
        return "/pizze/show";
        
    }
    
}
