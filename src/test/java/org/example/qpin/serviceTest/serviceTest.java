package org.example.qpin.serviceTest;

import org.example.qpin.domain.parking.service.ParkingService;
import org.example.qpin.global.common.repository.ParkingRepository;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class serviceTest {
    @MockBean
    private ParkingRepository parkingRepository;

    @InjectMocks
    private ParkingService parkingService;

    @Test
    public void 주변주차장찾기() throws ParseException {
        System.out.println(parkingService.findParkingNearby(35.4816746,129.4085415, 10.0, "11110"));
    }
}
