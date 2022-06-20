package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.sender.MessageSenderImplTest;

import java.util.stream.Stream;


public class GeoServiceImplTest {
    GeoService geoService;

    @BeforeEach
    public void init() {
        geoService = new GeoServiceImpl();
    }

    private static Stream<Arguments> providePositiveParameters() {
        return Stream.of(
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location(
                        "Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location(
                        "New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of(MessageSenderImplTest.MOSCOW_IP_TEST, new Location(
                        "Moscow", Country.RUSSIA, null, 0)),
                Arguments.of(MessageSenderImplTest.NEW_YORK_IP_TEST, new Location(
                        "New York", Country.USA, null, 0)),
                Arguments.of(GeoServiceImpl.LOCALHOST, new Location(
                        null, null, null, 0))
        );
    }

    private static Stream<Arguments> provideNegativeParameters() {
        return Stream.of(
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location(
                        "New York", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location(
                        "Moscow", Country.USA, " 10th Avenue", 33)),
                Arguments.of(MessageSenderImplTest.MOSCOW_IP_TEST, new Location(
                        "Moscow", Country.RUSSIA, "Some Street", 0)),
                Arguments.of(MessageSenderImplTest.NEW_YORK_IP_TEST, new Location(
                        "New York", Country.USA, null, 5)),
                Arguments.of(GeoServiceImpl.LOCALHOST, new Location(
                        null, Country.USA, null, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("providePositiveParameters")
    public void testPositiveParseCSV(String ipAddress, Location expectedLocation) {
        Location location = geoService.byIp(ipAddress);
        Assertions.assertEquals(location, expectedLocation);
    }

    @ParameterizedTest
    @MethodSource("provideNegativeParameters")
    public void testNegativeParseCSV(String ipAddress, Location expectedLocation) {
        Location location = geoService.byIp(ipAddress);
        Assertions.assertNotEquals(location, expectedLocation);
    }
}
