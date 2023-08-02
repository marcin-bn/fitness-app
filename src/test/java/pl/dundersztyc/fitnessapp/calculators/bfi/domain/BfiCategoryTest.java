package pl.dundersztyc.fitnessapp.calculators.bfi.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.dundersztyc.fitnessapp.user.domain.Gender;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class BfiCategoryTest {

    @ParameterizedTest
    @MethodSource("provideInputAndResultForDetermineCategoryForMan")
    void determineCategoryForMan(double bfi, BfiCategory expectedCategory) {
        assertThat(BfiCategory.determineCategory(bfi, Gender.MAN)).isEqualTo(expectedCategory);
    }

    @ParameterizedTest
    @MethodSource("provideInputAndResultForDetermineCategoryForWoman")
    void determineCategoryForWoman(double bfi, BfiCategory expectedCategory) {
        assertThat(BfiCategory.determineCategory(bfi, Gender.WOMAN)).isEqualTo(expectedCategory);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-20.0, 100.0})
    void shouldThrowWhenDetermineCategoryWithInvalidBfi(double invalidBfi) {
        assertThrows(IllegalArgumentException.class, () -> {
            BfiCategory.determineCategory(invalidBfi, Gender.MAN);
        });
    }


    private static Stream<Arguments> provideInputAndResultForDetermineCategoryForMan() {
        return Stream.of(
                Arguments.of(1.0, BfiCategory.BELOW_STANDARD),
                Arguments.of(4.0, BfiCategory.ESSENTIAL_FAT),
                Arguments.of(10.0, BfiCategory.ATHLETES),
                Arguments.of(16.0, BfiCategory.FITNESS),
                Arguments.of(20.0, BfiCategory.AVERAGE),
                Arguments.of(30.0, BfiCategory.OBESE)
        );
    }

    private static Stream<Arguments> provideInputAndResultForDetermineCategoryForWoman() {
        return Stream.of(
                Arguments.of(5.0, BfiCategory.BELOW_STANDARD),
                Arguments.of(12.0, BfiCategory.ESSENTIAL_FAT),
                Arguments.of(20.0, BfiCategory.ATHLETES),
                Arguments.of(24.0, BfiCategory.FITNESS),
                Arguments.of(30.0, BfiCategory.AVERAGE),
                Arguments.of(35.0, BfiCategory.OBESE)
        );
    }

}