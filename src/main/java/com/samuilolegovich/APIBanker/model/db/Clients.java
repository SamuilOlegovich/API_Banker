package com.samuilolegovich.APIBanker.model.db;



import com.samuilolegovich.APIBanker.model.inObjects.NewClient;

import javax.persistence.*;


@Entity
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    public Clients() {
    }

    public Clients(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Clients(NewClient newClient) {
        this.firstName = newClient.getFirst_name();
        this.lastName = newClient.getLast_name();
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public long getId() { return id; }
}
