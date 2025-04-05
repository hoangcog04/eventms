package com.example.eventms.organizer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.eventms.mbp.entity.EesAttribute;
import com.example.eventms.mbp.entity.EesAttributeCategory;
import com.example.eventms.mbp.mapper.EesAttributeCategoryMapper;
import com.example.eventms.mbp.mapper.EesAttributeMapper;
import com.example.eventms.organizer.dto.EventAttrInfo;
import com.example.eventms.organizer.mapper.AttrConverter;
import com.example.eventms.organizer.service.IEesAttributeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class EesAttributeServiceImpl extends ServiceImpl<EesAttributeMapper, EesAttribute> implements IEesAttributeService {
    EesAttributeCategoryMapper attributeCategoryMapper;
    EesAttributeMapper attributeMapper;
    AttrConverter attrConverter;

    @Override
    public List<EventAttrInfo> getAttrInfo() {
        // sel * from attrCate
        List<EesAttributeCategory> attrCategories = attributeCategoryMapper.selectList(null);

        List<Long> attrCategoryIds = attrCategories.stream().map(EesAttributeCategory::getId).toList();

        // sel * from attr where above `attrCategoryIds`
        var attrWrp = new LambdaQueryWrapper<EesAttribute>();
        attrWrp.in(EesAttribute::getAttributeCategoryId, attrCategoryIds);
        List<EesAttribute> attrs = attributeMapper.selectList(attrWrp);

        return attrConverter.toAttrInfos(attrCategories, attrs);
    }
}
