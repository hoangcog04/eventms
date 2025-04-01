package com.example.eventms.organizer.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.eventms.mbp.entity.EesVenue;
import com.example.eventms.organizer.dto.VenuePayload;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */
public interface IEesVenueService extends IService<EesVenue> {
    @Transactional
    EesVenue add(VenuePayload venuePayload);

    EesVenue getItem(Long id);

    Page<EesVenue> getList(Integer pageNum, Integer pageSize);
}
