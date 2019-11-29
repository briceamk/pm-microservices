package cm.gelodia.uaa.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@JsonIgnoreProperties(
        value = {"createAt", "createBy", "updateBy", "updateAt"}
)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    @Id
    @GenericGenerator(strategy = "uuid2", name = "uuid2")
    @GeneratedValue(generator = "uuid2")
    protected String id;
    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createAt;
    @CreatedBy
    @Column(updatable = false)
    protected String createBy;
    @LastModifiedDate
    @Column(insertable = false)
    protected LocalDateTime updateAt;
    @LastModifiedBy
    @Column(insertable = false )
    protected String updateBy;
}
