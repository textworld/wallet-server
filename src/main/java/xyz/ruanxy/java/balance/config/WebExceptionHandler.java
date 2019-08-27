package xyz.ruanxy.java.balance.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.ruanxy.java.balance.exception.BusinessException;
import xyz.ruanxy.java.balance.payload.ResultBean;

@ControllerAdvice
@ResponseBody
public class WebExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler
    public ResultBean businessException(BusinessException e){
        return ResultBean.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler
    public ResultBean unknownException(Exception e){
        logger.error("unknownException: {}", e);
        return ResultBean.error(-99, e.getMessage());
    }
}
