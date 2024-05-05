package pl.dundersztyc.fitnessapp.bodyweight.application;

import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.LoadBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.application.port.out.UpdateBodyWeightProfilePort;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurement;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightMeasurementWindow;
import pl.dundersztyc.fitnessapp.bodyweight.domain.BodyWeightProfile;
import pl.dundersztyc.fitnessapp.user.domain.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class InMemoryBodyWeightMeasurementRepository implements LoadBodyWeightProfilePort, UpdateBodyWeightProfilePort {

    Map<User.UserId, List<BodyWeightMeasurement>> measurements = new HashMap<>();


    @Override
    public BodyWeightProfile load(User.UserId userId, LocalDateTime baselineDate) {
        var toLoad = measurements.get(userId).stream()
                .filter(isInRange(baselineDate))
                .collect(Collectors.toList());
        return new BodyWeightProfile(userId,
                new BodyWeightMeasurementWindow(toLoad));
    }


    @Override
    public BodyWeightProfile load(User.UserId userId, LocalDateTime baselineDate, LocalDateTime finishDate) {
        var toLoad = measurements.get(userId).stream()
                .filter(isInRange(baselineDate, finishDate))
                .collect(Collectors.toList());
        return new BodyWeightProfile(userId,
                new BodyWeightMeasurementWindow(toLoad));
    }

    @Override
    public void updateMeasurements(BodyWeightProfile profile) {
        var profileMeasurements = profile.getMeasurementWindow().getMeasurements();
        var toAdd = profileMeasurements.stream()
                .filter(measurement -> measurement.getId() == null)
                .collect(Collectors.toList());
        measurements.put(profile.getUserId(), toAdd);
    }

    private static Predicate<BodyWeightMeasurement> isInRange(LocalDateTime baselineDate) {
        return measurement ->  {
            var timestamp = measurement.getTimestamp();
            return timestamp.isEqual(baselineDate) || timestamp.isAfter(baselineDate);
        };
    }

    private static Predicate<BodyWeightMeasurement> isInRange(LocalDateTime baselineDate, LocalDateTime finishDate) {
        return measurement ->  {
            var timestamp = measurement.getTimestamp();
            return (timestamp.isEqual(baselineDate) || timestamp.isAfter(baselineDate)) &&
                    (timestamp.isEqual(finishDate) || timestamp.isBefore(finishDate));
        };
    }

}
