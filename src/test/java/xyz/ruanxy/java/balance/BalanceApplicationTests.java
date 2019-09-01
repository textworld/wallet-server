package xyz.ruanxy.java.balance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.ruanxy.java.balance.model.AccountModel;
import xyz.ruanxy.java.balance.model.WalletType;
import xyz.ruanxy.java.balance.payload.AccountDTO;
import xyz.ruanxy.java.balance.payload.PaymentRecordDTO;
import xyz.ruanxy.java.balance.service.RecordService;
import static org.mockito.Mockito.mock;

import org.junit.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceApplicationTests {
    @Autowired
    ObjectMapper objectMapper;
	@Test
	public void contextLoads() {
        RecordService recordService = mock(RecordService.class);
		PaymentRecordDTO dto = new PaymentRecordDTO();
		dto.setMoney(11.1);
		dto.setSource("alipay");
		dto.setDate(LocalDateTime.now());
		recordService.save(dto);
	}

	@Test
	public void walletType(){
		WalletType type = WalletType.parse("debt");
		System.out.println(type.getValue());
	}

	@Test
	public void bean(){
		AccountDTO dto = new AccountDTO();
		dto.setType(WalletType.DEBT);
		AccountModel model = new AccountModel();
		BeanUtils.copyProperties(dto,model);
		System.out.println(model.getType());
	}

	@Test
    public void walletJson(){
	    AccountDTO accountDTO = new AccountDTO();
	    accountDTO.setType(WalletType.ASSET);
        try {
            String str = objectMapper.writeValueAsString(accountDTO);
            System.out.println(str);
            AccountDTO parsed = objectMapper.readValue(str, AccountDTO.class);
            Assert.assertEquals(WalletType.ASSET,parsed.getType());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
