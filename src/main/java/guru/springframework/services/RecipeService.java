package guru.springframework.services;

import guru.springframework.command.RecipeCommand;
import guru.springframework.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand findCommandById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    void deleteById(Long id);
}
