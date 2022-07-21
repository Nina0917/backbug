package com.yuanning.backbug.entity.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ProjectInfoResult {

    private final Long userId;
    private final String userLastName;
    private final String projectName;
    private final String projectDescription;

}
