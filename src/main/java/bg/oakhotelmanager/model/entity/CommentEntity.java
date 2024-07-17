package bg.oakhotelmanager.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity{
    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false)
    private UserEntity creator;

}
