package com.example.api_v01.dto.entityLike;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ArchingDTO {

    private UUID id_arching;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime end_time;

    private Double init_amount;

    private Double final_amount;

    private Double total_amount;

    private BoxDTO box;

}
