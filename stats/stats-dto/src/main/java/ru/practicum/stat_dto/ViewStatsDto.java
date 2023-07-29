package ru.practicum.stat_dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatsDto {
    private String app;
    private String uri;
    private Integer hits;
}
