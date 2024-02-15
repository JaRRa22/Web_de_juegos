package com.springboot.webdejuegos.dto;

import com.springboot.webdejuegos.entity.Image;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private Long id;
    private String name;
    private Image image;
    private String description;
    private String route;
}