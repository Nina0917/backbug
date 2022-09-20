package com.yuanning.backbug.exceptionHandler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        logger.error("未知异常！原因是:",e);
        // return ResultBody.error(CommonEnum.INTERNAL_SERVER_ERROR);
        return MessageUtil.error(MessageEnum.INTERNAL_SERVER_ERROR.getResultCode(), MessageEnum.INTERNAL_SERVER_ERROR.getResultMsg());
    }

    /**
     * 处理自定义的业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Result bizExceptionHandler(BaseException e){
        logger.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return MessageUtil.error(e.getErrorCode(),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public Result exceptionHandler(NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        // return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
        return MessageUtil.error(MessageEnum.NOT_FOUND.getResultCode(), MessageEnum.NOT_FOUND.getResultMsg());
    }



}
