package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTimeResponse extends UserTime {
    private String updatedAt;
    public UserTimeResponse(@JsonProperty("name") String name, @JsonProperty("job") String job, @JsonProperty("updatedAt") String updatedAt) {
        super(name, job);
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
