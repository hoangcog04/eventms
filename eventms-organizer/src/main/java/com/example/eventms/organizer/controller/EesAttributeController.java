package com.example.eventms.organizer.controller;

import com.example.eventms.common.adapter.CommonResult;
import com.example.eventms.organizer.dto.EventAttrInfo;
import com.example.eventms.organizer.service.IEesAttributeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@Tag(name = "EesAttributeController", description = "Product attribute management")
@RequestMapping("/event_settings/attributes")
public class EesAttributeController {
    IEesAttributeService attributeService;

    @RequestMapping(value = "/attr_info", method = RequestMethod.GET)
    public CommonResult<List<EventAttrInfo>> getAttrInfo() {
        List<EventAttrInfo> eventAttrInfos = attributeService.getAttrInfo();
        return CommonResult.success(eventAttrInfos);
    }
}
