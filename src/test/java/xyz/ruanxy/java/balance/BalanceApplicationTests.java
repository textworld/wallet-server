package xyz.ruanxy.java.balance;

import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ruanxy.java.balance.payload.PaymentRecordDTO;
import xyz.ruanxy.java.balance.service.RecordService;
import static org.mockito.Mockito.mock;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceApplicationTests {

	@Test
	public void contextLoads() {
        RecordService recordService = mock(RecordService.class);
		PaymentRecordDTO dto = new PaymentRecordDTO();
		dto.setMoney(11.1);
		dto.setSource("alipay");
		dto.setDate(LocalDateTime.now());
		recordService.save(dto);
	}

}
