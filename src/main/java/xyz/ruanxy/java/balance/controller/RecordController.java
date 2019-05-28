package xyz.ruanxy.java.balance.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.ruanxy.java.balance.payload.ApiResponse;
import xyz.ruanxy.java.balance.payload.PageResponse;
import xyz.ruanxy.java.balance.payload.PaymentRecordDTO;
import xyz.ruanxy.java.balance.service.RecordService;

@RestController
@RequestMapping(value = "/api/v1/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    @PostMapping()
    @ApiOperation(value = "创建支付记录", notes = "创建支付记录")
    public ResponseEntity<String> create(@RequestBody PaymentRecordDTO dto) {
        recordService.save(dto);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改支付记录", notes = "根据id修改支付记录信息")
    public ResponseEntity<PaymentRecordDTO> update(@PathVariable long id, @RequestBody PaymentRecordDTO dto) {
        dto.setId(id);
        return new ResponseEntity<>(recordService.update(dto), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "获取所有支付记录", notes = "获取所有支付记录")
    public PageResponse<PaymentRecordDTO> list(
        @RequestParam(name = "pageNo", defaultValue = "1") int page,
        @RequestParam(name = "pageSize", defaultValue = "20") int size,
        @RequestParam(name = "status", required = false) Integer status){
        PaymentRecordDTO queryDto = new PaymentRecordDTO();
        if (status != null){
            queryDto.setStatus(status);
        }
            return recordService.list(page, size, queryDto);
    }
}
