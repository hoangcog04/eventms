package com.example.eventms.organizer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.mbp.entity.EesAttribute;
import com.example.eventms.mbp.entity.EesAttributeValue;
import com.example.eventms.mbp.mapper.EesAttributeMapper;
import com.example.eventms.mbp.mapper.EesAttributeValueMapper;
import com.example.eventms.organizer.dto.EventAttrPayload;
import com.example.eventms.organizer.dto.EventProperty;
import com.example.eventms.organizer.mapper.AttrConverter;
import com.example.eventms.organizer.service.IEesAttributeValueService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

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
public class EesAttributeValueServiceImpl extends ServiceImpl<EesAttributeValueMapper, EesAttributeValue> implements IEesAttributeValueService {
    EesAttributeMapper attributeMapper;
    EesAttributeValueMapper attributeValueMapper;
    AttrConverter attrConverter;

    @Override
    public List<EventProperty> getEventProperties(Long eventId) {
        var propValueWrp = new LambdaQueryWrapper<EesAttributeValue>();
        propValueWrp.eq(EesAttributeValue::getEventId, eventId);
        List<EesAttributeValue> propValues = attributeValueMapper.selectList(propValueWrp);

        List<Long> attrIds = propValues.stream().map(EesAttributeValue::getAttributeId).toList();

        var propWrp = new LambdaQueryWrapper<EesAttribute>();
        propWrp.in(EesAttribute::getId, attrIds);
        List<EesAttribute> props = attributeMapper.selectList(propWrp);

        return attrConverter.toEventProps(propValues, props);
    }

    @Override
    @Transactional
    public int updateEventProps(Long eventId, List<EventAttrPayload> payloads) {
        var propValueWrp = new LambdaQueryWrapper<EesAttributeValue>();
        propValueWrp.eq(EesAttributeValue::getEventId, eventId);
        List<EesAttributeValue> propValues = attributeValueMapper.selectList(propValueWrp);

        // to update instead of insert when it's already set
        Map<Long, EesAttributeValue> groupByAttrCateId = propValues.stream()
                .collect(toMap(EesAttributeValue::getAttributeCategoryId, Function.identity()));

        List<EesAttributeValue> entities = new ArrayList<>();

        for (EventAttrPayload payload : payloads) {
            Long attrCateId = payload.getType().getAttributeCategoryId();

            EesAttributeValue entity = new EesAttributeValue();
            entity.setEventId(eventId);
            entity.setAttributeCategoryId(attrCateId);
            entity.setAttributeId(payload.getData().getAttributeId());
            entity.setValue(payload.getData().getValue());

            if (groupByAttrCateId.containsKey(attrCateId)) // update
                entity.setId(groupByAttrCateId.get(attrCateId).getId());

            entities.add(entity); // insert
        }

        this.saveOrUpdateBatch(entities);

        return 0;
    }
}
