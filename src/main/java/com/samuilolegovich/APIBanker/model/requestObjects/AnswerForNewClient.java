package com.samuilolegovich.APIBanker.model.requestObjects;

import com.samuilolegovich.APIBanker.model.db.Clients;

public class AnswerForNewClient {
    private long client_id;

    public AnswerForNewClient() {
    }

    public AnswerForNewClient(long client_id) {
        this.client_id = client_id;
    }

    public AnswerForNewClient(Clients clients) {
        this.client_id = clients.getId();
    }

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }
}
