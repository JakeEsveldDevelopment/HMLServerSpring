package com.jakeesveld.hmlserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String body;

    @ManyToOne
    @JoinColumn(name = "messages")
    @JsonIgnoreProperties("messages")
    private Conversation conversation;

    public Message() {
    }

    public Message(String body, Conversation conversation) {
        this.body = body;
        this.conversation = conversation;
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
