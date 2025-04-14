package com.example.eventms.mbp.mapper;

import com.example.eventms.mbp.domain.TicketDetail;
import com.example.eventms.mbp.entity.EesTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author vicendy04
 * @since 2025-04
 */
@Mapper
public interface EesTicketMapper extends BaseMapper<EesTicket> {
    List<TicketDetail> getTicketDetail(@Param("ids") List<Long> ids);

}

