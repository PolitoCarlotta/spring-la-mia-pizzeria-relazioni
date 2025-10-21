package com.pizzeria.spring_la_mia_pizzeria_crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pizzeria.spring_la_mia_pizzeria_crud.model.Ingredient;
import com.pizzeria.spring_la_mia_pizzeria_crud.model.Pizza;
import com.pizzeria.spring_la_mia_pizzeria_crud.repository.IngredientRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository repository;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("list", repository.findAll());
        model.addAttribute("ingredientObj", new Ingredient());
        return "ingredients/index";
    }

    @PostMapping("/create")
    public String postMethodName(@Valid @ModelAttribute("ingredientObj") Ingredient ingredient, BindingResult bindingResult, Model model) {
        Ingredient ingr = repository.findByName(ingredient.getName());

        if(ingr == null) {
        } else {
            bindingResult.addError(new ObjectError("ingredient", "Ingredient already present"));
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("list", repository.findAll());
            return "ingredients/index";
        }

        repository.save(ingredient);

        return "redirect:/ingredients";
    }

    @PostMapping("/delete/{id}")
    public String requestMethodName(@PathVariable Integer id, Model model) {
        Ingredient ingr = repository.findById(id).get();
        for( Pizza pizza: ingr.getPizze()) {
            pizza.getIngredients().remove(ingr);
        }

        repository.deleteById(id);

        return "redirect:/ingredients";
    }
}
