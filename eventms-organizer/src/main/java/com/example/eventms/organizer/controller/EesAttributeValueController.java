package com.example.eventms.organizer.controller;

import com.example.eventms.common.adapter.CommonResult;
import com.example.eventms.organizer.dto.EventProperty;
import com.example.eventms.organizer.service.IEesAttributeValueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@Tag(name = "EesAttributeValueController", description = "Event's Property Management")
@RequestMapping("/organizations")
public class EesAttributeValueController {
    IEesAttributeValueService attributeValueService;

    @RequestMapping(value = "/events/{eventId}/properties", method = RequestMethod.GET)
    public CommonResult<List<EventProperty>> getEventProperties(@PathVariable Long eventId) {
        List<EventProperty> eventProperty = attributeValueService.getEventProperties(eventId);
        return CommonResult.success(eventProperty);
    }
}
