package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.sender.MessageSenderImplTest;

import java.util.stream.Stream;

public class LocalizationServiceImplTest {
    LocalizationService localizationService;

    @BeforeEach
    public void init() {
        localizationService = new LocalizationServiceImpl();
    }

    private static Stream<Country> provideParameters() {
        return Stream.of(Country.values());
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void testSend(Country country) {
        String expectedMessaged = MessageSenderImplTest.ENGLISH_WELCOME_MESSAGE;
        if (country == Country.RUSSIA) {
            expectedMessaged = MessageSenderImplTest.RUSSIAN_WELCOME_MESSAGE;
        }
        String message = localizationService.locale(country);
        Assertions.assertEquals(message, expectedMessaged);
    }
}
