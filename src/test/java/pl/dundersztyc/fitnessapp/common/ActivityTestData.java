package pl.dundersztyc.fitnessapp.common;

import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.activity.domain.ActivityRecord;
import pl.dundersztyc.fitnessapp.calculators.caloriesburned.domain.ActivityType;
import pl.dundersztyc.fitnessapp.common.weight.Weight;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.util.ArrayList;
import java.util.Arrays;

public class ActivityTestData {

    public static Activity activityWithRecords(ActivityRecord... records) {
        return Activity.withId(
                new Activity.ActivityId(1L),
                new User.UserId(1L),
                ActivityType.CYCLING,
                Weight.fromKg(90),
                new ArrayList<>(Arrays.asList(records))
        );
    }

}
