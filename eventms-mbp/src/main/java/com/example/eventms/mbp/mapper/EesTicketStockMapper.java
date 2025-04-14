package com.example.eventms.mbp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.eventms.mbp.entity.EesTicketStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author vicendy04
 * @since 2025-04
 */
@Mapper
public interface EesTicketStockMapper extends BaseMapper<EesTicketStock> {
    int lockStockById(@Param("ticketStockId") Long ticketStockId, @Param("quantity") Integer quantity);

    int commitLockedStockById(@Param("stockId") Long stockId, @Param("reversedQty") Long reversedQty);

    int releaseLockedStockById(@Param("stockId") Long stockId, @Param("reversedQty") Long reversedQty);
}

