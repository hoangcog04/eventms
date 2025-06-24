package com.example.eventms.hub;

import com.example.eventms.hub.controller.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest
public class EventmsHubApplicationTests {
    @Autowired
    OesOrderController oesOrderController; // real bean
    @Autowired
    OesOrderItemController oesOrderItemController;
    @Autowired
    OesRefundItemController oesRefundItemController;
    @Autowired
    OesRefundRequestController oesRefundRequestController;
    @Autowired
    UesUserController uesUserController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(oesOrderController);
        Assertions.assertNotNull(oesOrderItemController);
        Assertions.assertNotNull(oesRefundItemController);
        Assertions.assertNotNull(oesRefundRequestController);
        Assertions.assertNotNull(uesUserController);
    }
}