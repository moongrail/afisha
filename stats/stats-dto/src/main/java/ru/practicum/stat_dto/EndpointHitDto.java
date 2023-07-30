package ru.practicum.stat_dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitDto {
    @NotEmpty
    private String app;
    @NotEmpty
    private String uri;
    private String ip;
    @NotEmpty
    private String timestamp;
}
