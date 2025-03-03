package org.example.qpin.serviceTest;

import org.example.qpin.domain.parking.service.ParkingService;
import org.example.qpin.global.common.repository.ParkingRepository;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class serviceTest {

    private final ParkingService parkingService;

    @Autowired
    serviceTest(ParkingRepository parkingRepository){
        parkingService=new ParkingService(parkingRepository);
    }

    @Test
    public void 주변주차장찾기() throws ParseException {
        System.out.println(parkingService.findParkingNearby(35.4816746,129.4085415,10, "11110"));
    }
}
