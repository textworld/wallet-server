package xyz.ruanxy.java.balance.service;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.ruanxy.java.balance.exception.ResourceNotFoundException;
import xyz.ruanxy.java.balance.model.PaymentRecord;
import xyz.ruanxy.java.balance.payload.PageResponse;
import xyz.ruanxy.java.balance.payload.PaymentRecordDTO;
import xyz.ruanxy.java.balance.repository.PaymentRecordRepository;

@Service
public class RecordService {

    private static Logger logger = LoggerFactory.getLogger(RecordService.class);

    @Autowired
    PaymentRecordRepository recordRepository;

    @Transactional
    public PaymentRecordDTO update(PaymentRecordDTO dto){
        if (dto == null) {
            throw new IllegalArgumentException("No PaymentRecordDTO supplied.");
        }

        if (!recordRepository.existsById(dto.getId())){
            throw new ResourceNotFoundException("No PaymentRecord found.");
        }

        PaymentRecord model = new PaymentRecord();
        BeanUtils.copyProperties(dto, model);

        PaymentRecord newOne = recordRepository.save(model);

        BeanUtils.copyProperties(newOne, dto);
        return dto;
    }

    @Transactional
    public void save(PaymentRecordDTO dto) {
        logger.info("save paymentrecord1");

        if (dto.getOrderId() == null) {
            throw new IllegalArgumentException("Order Id must be provided");
        }

        logger.info("save paymentrecord2");

        if (!recordRepository.existsByOrderId(dto.getOrderId())){
            logger.info("save paymentrecord3");
            PaymentRecord model = new PaymentRecord();
            BeanUtils.copyProperties(dto, model);

            recordRepository.save(model);
        } else {
            PaymentRecord paymentRecord = recordRepository.findByOrderId(dto.getOrderId());
            paymentRecord.setFundStatus(dto.getFundStatus());
            paymentRecord.setTradeAccount(dto.getTradeAccount());
            recordRepository.save(paymentRecord);
            logger.info("Id: {}, FundStatus: {}", paymentRecord.getId(), paymentRecord.getFundStatus());
        }
    }

    public PageResponse<PaymentRecordDTO> list(int page, int size, PaymentRecordDTO status){
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("date").descending());

        PaymentRecord query = new PaymentRecord();

        BeanUtils.copyProperties(status, query);

        Example<PaymentRecord> example = Example.of(query);

        Page<PaymentRecord> records =  recordRepository.findAll(example, pageable);

        PageResponse<PaymentRecordDTO> pageResponse = new PageResponse<>();

        List<PaymentRecordDTO> data = records.getContent().stream()
            .map(m -> {
                PaymentRecordDTO dto = new PaymentRecordDTO();
                BeanUtils.copyProperties(m, dto);
                return dto;
            }).collect(Collectors.toList());

        pageResponse.setData(data);
        pageResponse.setTotalPage(records.getTotalPages());
        pageResponse.setTotalCount(records.getTotalElements());
        pageResponse.setPageNo(records.getNumber() + 1);
        pageResponse.setPageSize(records.getSize());

        return pageResponse;
    }
}
