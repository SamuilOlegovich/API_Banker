package com.samuilolegovich.APIBanker.model.db;



import com.samuilolegovich.APIBanker.model.inObjects.NewClient;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String first_name;
    private String last_name;

    public Clients() {
    }

    public Clients(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Clients(NewClient newClient) {
        this.first_name = newClient.getFirst_name();
        this.last_name = newClient.getLast_name();
    }

    public void setFirst_name(String first_name) { this.first_name = first_name; }
    public void setLast_name(String last_name) { this.last_name = last_name; }
    public String getFirst_name() { return first_name; }
    public String getLast_name() { return last_name; }
    public void setId(long id) { this.id = id; }
    public long getId() { return id; }
}
