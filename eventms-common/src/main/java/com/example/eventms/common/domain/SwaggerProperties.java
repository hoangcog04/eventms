package com.example.eventms.common.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SwaggerProperties {
    String title;
    String description;
    String version;
    String contactName;
    String contactEmail;
    boolean enableSecurity;
}
