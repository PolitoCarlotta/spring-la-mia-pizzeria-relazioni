package com.pizzeria.spring_la_mia_pizzeria_crud.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pizzeria.spring_la_mia_pizzeria_crud.model.Offer;
import com.pizzeria.spring_la_mia_pizzeria_crud.model.Pizza;
import com.pizzeria.spring_la_mia_pizzeria_crud.repository.PizzaRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

        @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());

        return "/pizze/create";
    }

    @PostMapping("/create")
    public String save(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {
        Optional<Pizza> optPizza = repository.findByName(formPizza.getName());
        if (optPizza.isPresent()) {
            bindingResult.addError(new ObjectError("name", "Name already present"));
        }

        if (bindingResult.hasErrors()) {
            return "/pizze/create";
        }

        repository.save(formPizza);
        redirectAttributes.addFlashAttribute("successMessage", "Pizza created successifully");
        return "redirect:/pizze";
    }
    
     @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<Pizza> optPizza = repository.findById(id);
        Pizza pizza = optPizza.get();
        model.addAttribute("pizza", pizza);

        return "/pizze/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult,
            Model model) {

        Pizza oldPizza = repository.findById(formPizza.getId()).get();

        if (!oldPizza.getName().equals(formPizza.getName())) {
            bindingResult.addError(new FieldError("pizza","name", "Cannot change the name of pizza!"));
        }


        if (bindingResult.hasErrors()) {
            return "/pizze/edit";
        }

        repository.save(formPizza);

        return "redirect:/pizze";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Pizza pizza = repository.findById(id).get();

        repository.deleteById(id);

        return "redirect:/pizze";
    }

    @GetMapping("/{id}/offer")
    public String offer(@PathVariable("id") Integer id, Model model) {
        Offer offer = new Offer();
        offer.setPizza(repository.findById(id).get());

        model.addAttribute("offer", offer);
        model.addAttribute("editMode", false);
        return "/offers/edit";
    }
}
