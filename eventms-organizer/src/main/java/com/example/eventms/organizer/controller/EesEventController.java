package com.example.eventms.organizer.controller;

import com.example.eventms.common.adapter.Result;
import com.example.eventms.organizer.dto.EventDetail;
import com.example.eventms.organizer.dto.EventPayload;
import com.example.eventms.organizer.dto.EventResult;
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
    IEesEventService eesEventService;

    @RequestMapping(value = "/auto-create", method = RequestMethod.POST)
    public Result<EventResult> autoCreate(@RequestBody EventPayload eventPayload) {
        EventResult eventResult = eesEventService.autoCreate(eventPayload);
        return Result.success(eventResult);
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
    public Result<EventDetail> detail(@RequestParam(value = "expand", required = false) String expand,
                                      @PathVariable Long eventId) {
        List<String> paramList = ParamUtils.parse(expand);
        EventDetail eventDetail = eesEventService.detail(eventId, paramList);
        return Result.success(eventDetail);
    }

}
