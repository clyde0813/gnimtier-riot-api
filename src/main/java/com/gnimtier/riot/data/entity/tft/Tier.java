package com.gnimtier.riot.data.entity.tft;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tft_tier")
public class Tier {
    @Id
    private String name;
}
