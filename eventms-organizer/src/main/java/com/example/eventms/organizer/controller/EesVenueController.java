package com.example.eventms.organizer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.eventms.common.adapter.RestPage;
import com.example.eventms.common.adapter.CommonResult;
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
    IEesVenueService venueService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonResult<EesVenue> add(@RequestBody VenuePayload venuePayload) {
        EesVenue eesVenue = venueService.add(venuePayload);
        return CommonResult.success(eesVenue);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<EesVenue> getItem(@PathVariable Long id) {
        EesVenue eesVenue = venueService.getItem(id);
        if (eesVenue == null) return CommonResult.notFound();
        return CommonResult.success(eesVenue);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public CommonResult<RestPage<EesVenue>> getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        Page<EesVenue> eesVenueList = venueService.getList(pageNum, pageSize);
        return CommonResult.success(RestPage.of(eesVenueList));
    }
}
