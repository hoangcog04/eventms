package com.example.eventms.organizer.service.aggregator;


import com.example.eventms.mbp.entity.EesEvent;
import com.example.eventms.organizer.dto.EventDetail;

@FunctionalInterface
public interface ParamAggregator {
    void aggregate(EventDetail data, EesEvent event);
}
