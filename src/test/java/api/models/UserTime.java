package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTime {
    private String name;
    private String job;

    public UserTime(@JsonProperty("name") String name, @JsonProperty("job") String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
