package cm.gelodia.uaa.model;

import cm.gelodia.uaa.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
public class Role extends BaseEntity {
    @Column(nullable = false, unique = true)
    @NotEmpty(message = "role name is required")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")}
    )
    private Set<Permission> permissions = new LinkedHashSet<>();
}
