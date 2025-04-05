package com.example.eventms.organizer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.mbp.entity.EesVenue;
import com.example.eventms.mbp.mapper.EesVenueMapper;
import com.example.eventms.organizer.dto.VenuePayload;
import com.example.eventms.organizer.mapper.VenueConverter;
import com.example.eventms.organizer.service.IEesVenueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author vicendy04
 * @since 2025-03
 */

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class EesVenueServiceImpl extends ServiceImpl<EesVenueMapper, EesVenue> implements IEesVenueService {
    EesVenueMapper venueMapper;
    VenueConverter venueConverter;

    @Override
    public EesVenue add(VenuePayload venuePayload) {
        // Todo: add organizerId
        EesVenue eesVenue = venueConverter.toEntity(venuePayload);
        venueMapper.insert(eesVenue);
        return eesVenue;
    }

    @Override
    public EesVenue getItem(Long id) {
        return venueMapper.selectById(id);
    }

    @Override
    public Page<EesVenue> getList(Integer pageNum, Integer pageSize) {
        var wrp = new LambdaQueryWrapper<EesVenue>();
        wrp.eq(EesVenue::getOrganizerId, 4);
        Page<EesVenue> page = new Page<>(pageNum, pageSize);
        return page(page, wrp);
    }
}
