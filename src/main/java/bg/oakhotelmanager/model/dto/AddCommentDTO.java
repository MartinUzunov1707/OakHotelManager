package bg.oakhotelmanager.model.dto;

import jakarta.validation.constraints.Size;

public class AddCommentDTO {
    @Size(max = 200, min = 5)
    private String description;

    private Long creatorId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
