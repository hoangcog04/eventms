package com.example.eventms.organizer.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.eventms.common.adapter.CommonResult;
import com.example.eventms.common.adapter.RestPage;
import com.example.eventms.organizer.dto.TicketItem;
import com.example.eventms.organizer.dto.TicketPayload;
import com.example.eventms.organizer.service.IEesTicketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "EesTicketController", description = "Ticket Management")
@RequestMapping("/organizations/events/{eventId}/tickets")
public class EesTicketController {
    IEesTicketService ticketService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommonResult<TicketItem> add(@PathVariable Long eventId,
                                        @RequestBody TicketPayload ticketPayload) {
        TicketItem ticketItem = ticketService.add(eventId, ticketPayload);
        return CommonResult.success(ticketItem);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public CommonResult<RestPage<TicketItem>> getList(@PathVariable Long eventId,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        IPage<TicketItem> ticketResult = ticketService.getList(eventId, pageNum, pageSize);
        return CommonResult.success(RestPage.of(ticketResult));
    }
}
