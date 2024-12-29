package com.gnimtier.riot.data.entity.riot;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "summoner")
public class Summoner {
    @Id
    private String id;

    @Column(name = "accountId")
    private String accountId;

    @Column(name = "profileIconId")
    private int profileIconId;

    @Column(name = "revisionDate")
    private long revisionDate;

    @Column(name = "summonerLevel")
    private long summonerLevel;

    @OneToOne
    @JoinColumn(name = "account_uuid")
    private Account account;
}
