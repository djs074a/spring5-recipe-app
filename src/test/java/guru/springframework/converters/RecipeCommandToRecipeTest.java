package guru.springframework.converters;

import guru.springframework.command.CategoryCommand;
import guru.springframework.command.IngredientCommand;
import guru.springframework.command.NotesCommand;
import guru.springframework.command.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;

    RecipeCommandToRecipe converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void testNullObject(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(RECIPE_ID);
        command.setCookTime(COOK_TIME);
        command.setPrepTime(PREP_TIME);
        command.setDescription(DESCRIPTION);
        command.setDirections(DIRECTIONS);
        command.setDifficulty(DIFFICULTY);
        command.setServings(SERVINGS);
        command.setSource(SOURCE);
        command.setUrl(URL);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);

        command.setNotes(notes);

        CategoryCommand category1 = new CategoryCommand();
        category1.setId(CAT_ID_1);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID2);

        command.getCategories().add(category1);
        command.getCategories().add(category2);

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(INGRED_ID_1);

        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setId(INGRED_ID_2);

        command.getIngredients().add(ingredientCommand1);
        command.getIngredients().add(ingredientCommand2);

        //when
        Recipe recipe = converter.convert(command);

        //then

        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}