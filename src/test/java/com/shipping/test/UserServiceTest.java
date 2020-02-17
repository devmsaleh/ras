package com.shipping.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.charity.service.EmailService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private EmailService emailService;

    @Before
    public void beforeTestRuns() {

    }

    @Test
    public void test() {
        System.out.println("####### send email test ########");
        emailService.sendMail("dev.mahmoud.saleh@gmail.com", "test2", "test2");
        // emailService.sendRegistrationApprovedEmail("dev.mahmoud.saleh@gmail.com", new Locale("ar"));
        // emailService.sendRegistrationRejectedEmail("dev.mahmoud.saleh@gmail.com", "عدم اكتمال البيانات", new Locale("ar"));
    }

}
