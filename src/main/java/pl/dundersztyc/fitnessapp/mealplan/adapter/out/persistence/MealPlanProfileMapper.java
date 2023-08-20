package pl.dundersztyc.fitnessapp.mealplan.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanProfile;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanWindow;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class MealPlanProfileMapper {

    private final MealMapper mealMapper;

    public MealPlanProfile mapToDomainEntity(User.UserId userId, List<MealJpaEntity> meals) {

        var mappedMeals = meals.stream()
                .map(mealMapper::mapToMeal)
                .collect(Collectors.toList());

        return new MealPlanProfile(userId, new MealPlanWindow(mappedMeals));
    }
}
