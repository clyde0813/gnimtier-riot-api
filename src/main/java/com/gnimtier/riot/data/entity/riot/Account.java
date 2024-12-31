package com.gnimtier.riot.data.entity.riot;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "account")
public class Account {
    @Id
    private String puuid;

    @Column(name = "gameName", nullable = false)
    private String gameName;

    @Column(name = "tagLine", nullable = false)
    private String tagLine;

    private LocalDateTime lastModifiedDate;
}
