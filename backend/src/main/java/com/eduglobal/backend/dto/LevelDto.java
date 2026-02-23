package com.eduglobal.backend.dto;

import com.eduglobal.backend.enums.Level;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LevelDto {
    private Long id;
    private Level name;
}
