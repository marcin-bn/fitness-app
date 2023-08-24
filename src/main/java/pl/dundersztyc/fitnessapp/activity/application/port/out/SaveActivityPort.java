package pl.dundersztyc.fitnessapp.activity.application.port.out;

import pl.dundersztyc.fitnessapp.activity.domain.Activity;

public interface SaveActivityPort {
    Activity.ActivityId save(Activity activity);
}
