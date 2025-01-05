package com.gnimtier.riot.data.entity.tft;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tft_league")
public class League {
    @Id
    private String id;

    @Column(name = "tier", nullable = false)
    private int tier;

    @ManyToOne
    @JoinColumn(name = "tft_queue_type_name")
    private QueueType queueType;

    @Column(name = "name", nullable = true)
    private String name;
}
