package pl.dundersztyc.fitnessapp.activity.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dundersztyc.fitnessapp.activity.adapter.in.ActivityResponse;
import pl.dundersztyc.fitnessapp.activity.application.port.in.ActivityRequest;
import pl.dundersztyc.fitnessapp.activity.application.port.in.AddActivityUseCase;
import pl.dundersztyc.fitnessapp.activity.application.port.in.GetActivitiesUseCase;
import pl.dundersztyc.fitnessapp.activity.domain.Activity;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/activities/")
@RequiredArgsConstructor
class ActivityController {

    private final AddActivityUseCase addActivityUseCase;
    private final GetActivitiesUseCase getActivitiesUseCase;

    @PostMapping
    Activity.ActivityId addActivity(@RequestBody ActivityRequest activityRequest) {
        return addActivityUseCase.addActivity(activityRequest);
    }

    @GetMapping("/{id}")
    ActivityResponse getActivityById(@PathVariable("id") Long activityId) {
        return ActivityResponse.of(
                getActivitiesUseCase.getActivityById(new Activity.ActivityId(activityId))
        );
    }

    @GetMapping("/history")
    List<ActivityResponse> getActivityFromTo(@RequestParam("userId") Long userId,
                                             @RequestParam("startDate") LocalDateTime startDate,
                                             @RequestParam("finishDate") LocalDateTime finishDate) {
        var activities = getActivitiesUseCase.getActivitiesFromTo(new User.UserId(userId), startDate, finishDate);

        return activities.stream()
                .map(ActivityResponse::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/last")
    List<ActivityResponse> getLastActivities(@RequestParam("userId") Long userId,
                                             @RequestParam("numberOfActivities") int numberOfActivities) {
        var activities = getActivitiesUseCase.getLastActivities(new User.UserId(userId), numberOfActivities);

        return activities.stream()
                .map(ActivityResponse::of)
                .collect(Collectors.toList());
    }
}
