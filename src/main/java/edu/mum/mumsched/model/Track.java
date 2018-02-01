package edu.mum.mumsched.model;

import javax.persistence.Embeddable;

@Embeddable
public class Track {
    String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
