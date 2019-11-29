package cm.gelodia.uaa.model;

import cm.gelodia.uaa.model.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class Permission extends BaseEntity {
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "permission name is required")
    private String name;
}
