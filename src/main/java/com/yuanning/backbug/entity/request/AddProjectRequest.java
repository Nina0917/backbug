package com.yuanning.backbug.entity.request;

import com.yuanning.backbug.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddProjectRequest {
    private final String name;
    private final String description;
    private final List<Long> appUserIds;

}
