package com.pizzeria.spring_la_mia_pizzeria_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pizzeria.spring_la_mia_pizzeria_crud.model.Ingredient;

public interface IngredientRepository  extends JpaRepository<Ingredient, Integer> {

    public Ingredient findByName (String name);
}
