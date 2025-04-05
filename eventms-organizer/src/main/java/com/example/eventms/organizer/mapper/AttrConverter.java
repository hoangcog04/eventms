package com.example.eventms.organizer.mapper;

import com.example.eventms.mbp.entity.EesAttribute;
import com.example.eventms.mbp.entity.EesAttributeCategory;
import com.example.eventms.mbp.entity.EesAttributeValue;
import com.example.eventms.organizer.dto.EventAttrInfo;
import com.example.eventms.organizer.dto.EventProperty;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

/**
 * @author vicendy04
 * @since 2025-03
 */
@Mapper(componentModel = "spring")
public interface AttrConverter {
    EventAttrInfo.AttrCategoryDto toDto(EesAttributeCategory entity);

    EventAttrInfo.AttrDto toAttrInfoDto(EesAttribute entity);

    List<EventAttrInfo.AttrCategoryDto> toAttrCateDtos(List<EesAttributeCategory> entities);

    List<EventAttrInfo.AttrDto> toAttrDtos(List<EesAttribute> entities);

    EventProperty.PropDto toPropDto(EesAttribute entity);

    EventProperty.PropValueDto toValueDto(EesAttributeValue entity);

    List<EventProperty.PropDto> toPropDtos(List<EesAttribute> entities);

    List<EventProperty.PropValueDto> toValueDtos(List<EesAttributeValue> entities);

    default List<EventAttrInfo> toAttrInfos(List<EesAttributeCategory> acs, List<EesAttribute> as) {
        if (acs == null || as == null) return Collections.emptyList();

        List<EventAttrInfo.AttrCategoryDto> attrCateDtos = toAttrCateDtos(acs);
        List<EventAttrInfo.AttrDto> attrDtos = toAttrDtos(as);

        Map<Long, List<EventAttrInfo.AttrDto>> attrDtoByCate = attrDtos.stream()
                .collect(groupingBy(EventAttrInfo.AttrDto::getAttributeCategoryId));

        return attrCateDtos.stream()
                .map(a -> {
                    EventAttrInfo e = new EventAttrInfo();
                    e.setAttrCategory(a);
                    e.setAttrs(attrDtoByCate.getOrDefault(a.getId(), Collections.emptyList()));
                    return e;
                })
                .toList();
    }

    default List<EventProperty> toEventProps(List<EesAttributeValue> avs, List<EesAttribute> as) {
        if (avs == null || as == null) return Collections.emptyList();

        List<EventProperty.PropValueDto> propValueDtos = toValueDtos(avs);
        List<EventProperty.PropDto> propDtos = toPropDtos(as);

        Map<Long, EventProperty.PropValueDto> modelAIdMap = propValueDtos.stream()
                .collect(toMap(EventProperty.PropValueDto::getAttributeId, Function.identity()));

        return propDtos.stream()
                .map(a -> {
                    EventProperty e = new EventProperty();
                    e.setProp(a);
                    e.setValue(modelAIdMap.get(a.getId()));
                    return e;
                })
                .toList();
    }
}
