package com.yuanning.backbug.entity.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddProjectRequest {
    private final String name;
    private final String description;
    private final List<Long> appUserIds;

}
