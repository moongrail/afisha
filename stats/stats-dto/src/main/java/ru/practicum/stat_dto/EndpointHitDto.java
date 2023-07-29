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
    private Integer id;
    @NotEmpty
    private String app;
    @NotEmpty
    private String uri;
    @NotEmpty
    private String ip;
    @NotEmpty
    private String timestamp;
}
