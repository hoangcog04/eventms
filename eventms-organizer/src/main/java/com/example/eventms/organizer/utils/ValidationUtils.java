package com.example.eventms.organizer.utils;

import com.example.eventms.mbp.entity.EesEvent;
import com.example.eventms.mbp.entity.EesTicket;
import com.example.eventms.mbp.entity.UesOrganizer;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean isEventInvalid(EesEvent e) {
        return !isEventValid(e);
    }

    public static boolean isEventValid(EesEvent e) {
        if (e == null) return false;
        else if (e.getOrganizerId() == null) return false;
        else if (e.getVenueId() == null) return false;
        else if (isEmpty(e.getTitle())) return false;
        else if (e.getDate() == null) return false;
        else if (e.getStartTime() == null) return false;
        else if (e.getEndTime() == null) return false;
        else if (e.getStatus() == 0) return false;
        return true;
    }

    public static boolean isOrganizerInValid(UesOrganizer o) {
        return !isOrganizerValid(o);
    }

    public static boolean isOrganizerValid(UesOrganizer o) {
        if (o == null) return false;
        else if (o.getUserId() == null) return false;
        else if (isEmpty(o.getName())) return false;
        return true;
    }

    public static boolean isTicketsInvalid(List<EesTicket> ts) {
        return !isTicketsValid(ts);
    }

    public static boolean isTicketsValid(List<EesTicket> ts) {
        if (CollectionUtils.isEmpty(ts)) return false;
        for (var t : ts) {
            if (isEmpty(t.getName())) return false;
            else if (t.getIsHidden() == 1) return false;
        }
        return true;
    }
}
