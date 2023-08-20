package pl.dundersztyc.fitnessapp.mealplan.adapter.out.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import pl.dundersztyc.fitnessapp.AbstractTestcontainers;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanProfile;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanWindow;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dundersztyc.fitnessapp.common.MealTestData.defaultMeal;

@DataJpaTest
@Import({MealPlanProfilePersistenceAdapter.class, MealMapper.class, MealPlanProfileMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MealPlanProfilePersistenceAdapterTest extends AbstractTestcontainers {

    @Autowired
    private MealPlanProfilePersistenceAdapter persistenceAdapter;

    @Autowired
    private MealRepository mealRepository;

    @BeforeAll
    static void beforeAll(@Autowired MealRepository mealRepository) {
        mealRepository.deleteAll();
    }

    @AfterEach
    void afterEach() {
        mealRepository.deleteAll();
    }


    @Test
    @Sql(scripts = "/LoadMealPlanProfile.sql")
    void shouldLoadMealPlanProfileFromToWithMealsFromOneDay() {
        LocalDate from = LocalDate.of(2018, 8, 8);
        LocalDate to = LocalDate.of(2018, 8, 8);
        var profile = persistenceAdapter.load(new User.UserId(1L), from, to);
        var mealPlanWindow = profile.getMealPlanWindow();
        var meals = mealPlanWindow.getMeals();

        assertThat(meals).hasSize(3);
        assertThat(meals.stream().map(Meal::getProductId).map(Product.ProductId::value).collect(Collectors.toList()))
                .isEqualTo(List.of("1", "2", "3"));
    }

    @Test
    @Sql(scripts = "/LoadMealPlanProfile.sql")
    void shouldLoadMealPlanProfileFromToWithMealsFromManyDays() {
        LocalDate from = LocalDate.of(2018, 8, 8);
        LocalDate to = LocalDate.of(2018, 8, 9);
        var profile = persistenceAdapter.load(new User.UserId(2L), from, to);
        var mealPlanWindow = profile.getMealPlanWindow();
        var meals = mealPlanWindow.getMeals();

        assertThat(meals).hasSize(6);
        assertThat(meals.stream().map(Meal::getProductId).map(Product.ProductId::value).collect(Collectors.toList()))
                .isEqualTo(List.of("1", "2", "3", "1", "2", "3"));
    }

    @Test
    void shouldAddMeal() {
        final User.UserId userId = new User.UserId(1L);
        var emptyMealPlanWindow = new MealPlanWindow();
        var profile = new MealPlanProfile(userId, emptyMealPlanWindow);
        profile.addMeal(defaultMeal()
                .userId(userId).productId(new Product.ProductId("productId123")).build());

        var updatedIds = persistenceAdapter.updateMeals(profile);

        assertThat(mealRepository.count()).isEqualTo(1);
        assertThat(mealRepository.findAll().get(0).getProductId()).isEqualTo("productId123");
        assertThat(updatedIds).hasSize(1);
    }

    @Test
    void shouldUpdateMeal() {
        mealRepository.save(new MealMapper().mapToJpaEntity(
                defaultMeal().productId(new Product.ProductId("beforeUpdate")).build())
        );
        var mealId = mealRepository.findAll().get(0).getId();

        var updatedMeal = defaultMeal()
                .id(new Meal.MealId(mealId))
                .productId(new Product.ProductId("afterUpdate"))
                .build();
        var profile = new MealPlanProfile(new User.UserId(1L), new MealPlanWindow());

        profile.addMeal(updatedMeal);

        persistenceAdapter.updateMeals(profile);

        assertThat(mealRepository.count()).isEqualTo(1);
        assertThat(mealRepository.findAll().get(0).getProductId()).isEqualTo("afterUpdate");
    }

    @Test
    void shouldDeleteMeal() {
        var meal = defaultMeal().id(new Meal.MealId(1L)).build();
        mealRepository.save(new MealMapper().mapToJpaEntity(meal));

        assertThat(mealRepository.count()).isEqualTo(1);

        var mealId = mealRepository.findAll().get(0).getId();
        persistenceAdapter.deleteMeal(new Meal.MealId(mealId));

        assertThat(mealRepository.count()).isEqualTo(0);
    }

}