package com.gnimtier.riot.data.entity.riot;

import com.gnimtier.riot.data.dto.riot.AccountDto;
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
    @Column(name = "puuid")
    private String puuid;

    @Column(name = "game_name", nullable = false)
    private String gameName;

    @Column(name = "tag_line", nullable = false)
    private String tagLine;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date", nullable = true)
    private LocalDateTime modifiedDate;

    public AccountDto toDto() {
        AccountDto accountDto = new AccountDto();
        accountDto.setPuuid(this.puuid);
        accountDto.setGameName(this.gameName);
        accountDto.setTagLine(this.tagLine);
        return accountDto;
    }
}
