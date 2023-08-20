package pl.dundersztyc.fitnessapp.mealplan.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.dundersztyc.fitnessapp.mealplan.application.port.out.DeleteMealPort;
import pl.dundersztyc.fitnessapp.mealplan.application.port.out.LoadMealPlanProfilePort;
import pl.dundersztyc.fitnessapp.mealplan.application.port.out.UpdateMealPlanProfilePort;
import pl.dundersztyc.fitnessapp.mealplan.domain.Meal;
import pl.dundersztyc.fitnessapp.mealplan.domain.MealPlanProfile;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class MealPlanProfilePersistenceAdapter implements LoadMealPlanProfilePort, UpdateMealPlanProfilePort, DeleteMealPort {

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;
    private final MealPlanProfileMapper mealPlanProfileMapper;

    @Override
    public MealPlanProfile load(User.UserId userId, LocalDate baselineDate, LocalDate finishDate) {
        var meals = mealRepository.findByUserFromTo(userId.value(), baselineDate, finishDate);
        return mealPlanProfileMapper.mapToDomainEntity(userId, meals);
    }

    @Override
    public List<Meal.MealId> updateMeals(MealPlanProfile profile) {
        List<Meal.MealId> updatedIds = new ArrayList<>();
        profile.getMealPlanWindow().getMeals().stream()
                .map(mealMapper::mapToJpaEntity)
                .forEach(meal -> {
                    var updatedMeal = mealRepository.save(meal);
                    updatedIds.add(new Meal.MealId(updatedMeal.getId()));
                });
        return updatedIds;
    }

    @Override
    public void deleteMeal(Meal.MealId mealId) {
        mealRepository.deleteById(mealId.value());
    }
}
