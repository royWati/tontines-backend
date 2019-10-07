package ekenya.co.ke.tontines.dao.entitites;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/***
 *
 * @version 2.0
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "softDelete", columnDefinition = "boolean default false")
    private boolean softDelete;

    @PrePersist
    public void addData() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZoneId zoneId = ZoneId.of("Africa/Nairobi");
        ZonedDateTime kenya_zone = zonedDateTime.withZoneSameInstant(zoneId);
        this.createdOn = kenya_zone.toLocalDateTime();
        this.softDelete = false;
    }
}
