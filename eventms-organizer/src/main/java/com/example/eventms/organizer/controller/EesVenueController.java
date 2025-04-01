package com.example.eventms.organizer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eventms.common.adapter.RestPage;
import com.example.eventms.common.adapter.Result;
import com.example.eventms.mbp.entity.EesVenue;
import com.example.eventms.organizer.dto.VenuePayload;
import com.example.eventms.organizer.service.IEesVenueService;
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
@Tag(name = "EesVenueController", description = "Organizer venue management")
@RequestMapping("/organizations/venues")
public class EesVenueController {
    IEesVenueService eesVenueService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<EesVenue> add(@RequestBody VenuePayload venuePayload) {
        EesVenue eesVenue = eesVenueService.add(venuePayload);
        return Result.success(eesVenue);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<EesVenue> getItem(@PathVariable Long id) {
        EesVenue eesVenue = eesVenueService.getItem(id);
        if (eesVenue == null) return Result.notFound();
        return Result.success(eesVenue);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<RestPage<EesVenue>> getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<EesVenue> eesVenueList = eesVenueService.getList(pageNum, pageSize);
        return Result.success(RestPage.of(eesVenueList));
    }
}
