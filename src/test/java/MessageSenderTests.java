import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;
import static ru.netology.geo.GeoServiceImpl.NEW_YORK_IP;


public class MessageSenderTests {
    @Test
    public void testMessageSendRUS() {
        GeoServiceImpl mockGeoServiceImpl = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(mockGeoServiceImpl.byIp("172."))
                .thenReturn(new Location("Moscow", RUSSIA, null, 0));

        LocalizationServiceImpl mockLocalizationServiceImpl = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(mockLocalizationServiceImpl.locale(RUSSIA))
                .thenReturn("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(mockGeoServiceImpl, mockLocalizationServiceImpl);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.");
        String expected = "Добро пожаловать";
        String text = messageSender.send(headers);

        Assertions.assertEquals(expected, text);
    }

    @Test
    public void testMessageSendUSA() {
        GeoServiceImpl mockGeoServiceImpl = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(mockGeoServiceImpl.byIp("96."))
                .thenReturn(new Location("New York", USA, null, 0));

        LocalizationServiceImpl mockLocalizationServiceImpl = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(mockLocalizationServiceImpl.locale(USA))
                .thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(mockGeoServiceImpl, mockLocalizationServiceImpl);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.");
        String expected = "Welcome";
        String text = messageSender.send(headers);

        Assertions.assertEquals(expected, text);
    }

    @Test
    public void testLocationByIP() {
        GeoServiceImpl mockGeoServiceImpl = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(mockGeoServiceImpl.byIp("96.44.183.149"))
                .thenReturn(new Location("New York", USA, " 10th Avenue", 32));

        Location actual = mockGeoServiceImpl.byIp(NEW_YORK_IP);
        Location expected = new Location("New York", USA, " 10th Avenue", 32);

        Assertions.assertEquals(expected.getCountry(), actual.getCountry());
    }

    @Test
    public void testSendsText() {
        LocalizationServiceImpl mockLocalizationServiceImpl = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(mockLocalizationServiceImpl.locale(USA))
                .thenReturn("Welcome");

        String actual = mockLocalizationServiceImpl.locale(USA);
        String expected = "Welcome";

        Assertions.assertEquals(expected, actual);
    }
}

