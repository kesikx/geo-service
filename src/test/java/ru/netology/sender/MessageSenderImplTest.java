package ru.netology.sender;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageSenderImplTest {
    public static final String MOSCOW_IP_TEST = "172.2.56.5";
    public static final String NEW_YORK_IP_TEST = "96.28.0.1";
    public static final String RUSSIAN_WELCOME_MESSAGE = "Добро пожаловать";
    public static final String ENGLISH_WELCOME_MESSAGE = "Welcome";

    GeoService geoService = Mockito.mock(GeoService.class);
    LocalizationService localizationService = Mockito.mock(LocalizationService.class);
    MessageSender messageSender;

    @BeforeAll
    public void setup() {
        Mockito.when(geoService.byIp(MOSCOW_IP_TEST))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp(NEW_YORK_IP_TEST))
                .thenReturn(new Location("New York", Country.USA, null,  0));
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn(RUSSIAN_WELCOME_MESSAGE);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn(ENGLISH_WELCOME_MESSAGE);
    }

    @BeforeEach
    public void init() {
        messageSender = new MessageSenderImpl(geoService, localizationService);
    }

    @Test
    public void testSend() {
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, MOSCOW_IP_TEST);
        String message = messageSender.send(headers);
        Assertions.assertEquals(message, RUSSIAN_WELCOME_MESSAGE);
    }
}
