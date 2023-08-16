package pl.dundersztyc.fitnessapp.food.adapter.in;

import java.util.Optional;

record OptWrapper<T> (
        T value,
        boolean exists
) {

    public OptWrapper(T value, boolean exists) {
        this.value = value;
        this.exists = exists;
    }

    public OptWrapper(Optional<T> value) {
            this(
                    (value == null || value.isEmpty()) ? null : value.get(),
                    (value == null) ? false : true
            );
    }

}
