package com.gnimtier.riot.data.entity.riot;

import com.gnimtier.riot.data.dto.riot.AccountDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @Column(name = "puuid")
    private String puuid;

    @Column(name = "game_name", nullable = false)
    private String gameName;

    @Column(name = "tag_line", nullable = false)
    private String tagLine;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public AccountDto toDto() {
        AccountDto accountDto = new AccountDto();
        accountDto.setPuuid(this.puuid);
        accountDto.setGameName(this.gameName);
        accountDto.setTagLine(this.tagLine);
        return accountDto;
    }
}
