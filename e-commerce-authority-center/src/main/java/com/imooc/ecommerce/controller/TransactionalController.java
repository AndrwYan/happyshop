package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.transactional.Transactionallose;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/transactional")
public class TransactionalController {

    @Resource
    private Transactionallose transactionallose;

    @GetMapping("/test")
    public void WrongExceptionTransactional() throws IOException {
        transactionallose.wrongRollbackOn();
        log.info("coming in call wrong rollback for");
    }

    @GetMapping("/test-innner-call")
    public void WrongInnerCallTransactional() throws IOException {
        transactionallose.wrongInnerCall();
        log.info("coming in call wrong rollback for");
    }
}
