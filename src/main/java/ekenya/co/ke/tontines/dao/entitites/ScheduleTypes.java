package ekenya.co.ke.tontines.dao.entitites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedule_types_tbl")
public class ScheduleTypes extends BaseEntity {
    private String name;
    private long frequencyOfPayment;
}
