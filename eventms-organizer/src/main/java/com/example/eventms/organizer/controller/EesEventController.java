package com.example.eventms.organizer.controller;

import com.example.eventms.common.adapter.CommonResult;
import com.example.eventms.organizer.dto.*;
import com.example.eventms.organizer.service.IEesEventService;
import com.example.eventms.organizer.utils.ParamUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "EesEventController", description = "Event Management")
@RequestMapping("/organizations/events")
public class EesEventController {
    IEesEventService eventService;

    @RequestMapping(value = "/auto-create", method = RequestMethod.POST)
    public CommonResult<EventResult> autoCreate(@RequestBody EventPayload eventPayload) {
        EventResult eventResult = eventService.autoCreate(eventPayload);
        return CommonResult.success(eventResult);
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public CommonResult<EventDetail> detail(@RequestParam(value = "expand", required = false) String expand,
                                            @PathVariable Long eventId) {
        List<String> paramList = ParamUtils.parse(expand);
        EventDetail eventDetail = eventService.detail(eventId, paramList);
        return CommonResult.success(eventDetail);
    }

    @RequestMapping(value = "/{eventId}/content", method = RequestMethod.POST)
    public CommonResult<?> content(@PathVariable Long eventId,
                                   @RequestBody EventContent eventContent) {
        eventService.content(eventId, eventContent);
        return CommonResult.success(null);
    }

    @RequestMapping(value = "/{eventId}/publish", method = RequestMethod.POST)
    public CommonResult<EventPublish> publish(@PathVariable Long eventId) {
        EventPublish published = eventService.publish(eventId);
        if (published.getErrorMessage() == null) return CommonResult.success(published);
        else return CommonResult.validateFailed(published);
    }

    @RequestMapping(value = "/{eventId}/unpublish", method = RequestMethod.POST)
    public CommonResult<EventPublish> unpublish(@PathVariable Long eventId) {
        EventPublish unpublished = eventService.unpublish(eventId);
        if (unpublished.getErrorMessage() == null) return CommonResult.success(unpublished);
        else return CommonResult.validateFailed(unpublished);
    }
}
