package pl.dundersztyc.fitnessapp.mealplan.adapter.out.persistence;

import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.food.domain.Product;
import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;
import pl.dundersztyc.fitnessapp.user.domain.User;

@Component
class MealMapper {

    public Meal mapToMeal(MealJpaEntity mealJpaEntity) {
        return Meal.builder()
                .id(new Meal.MealId(mealJpaEntity.getId()))
                .userId(new User.UserId(mealJpaEntity.getUserId()))
                .day(mealJpaEntity.getDay())
                .productId(new Product.ProductId(mealJpaEntity.getProductId()))
                .weight(Weight.fromKg(mealJpaEntity.getWeightInKg()))
                .build();
    }

    public MealJpaEntity mapToJpaEntity(Meal meal) {
        return MealJpaEntity.builder()
                .id(meal.getId() == null ? null : meal.getId().value())
                .userId(meal.getUserId().value())
                .day(meal.getDay())
                .productId(meal.getProductId().value())
                .weightInKg(meal.getWeight().getKg())
                .build();
    }
}
