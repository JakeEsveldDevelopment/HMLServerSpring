package com.jakeesveld.hmlserver.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private List<User> participants;

    @OneToMany(mappedBy = "conversation")
    @JsonIgnoreProperties("conversation")
    List<Message> messages;

    public Conversation() {
    }

    public Conversation(List<User> participants, List<Message> messages) {
        this.participants = participants;
        this.messages = messages;
    }

    public long getId() {
        return id;
    }


    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void addParticipant(User user){
        this.participants.add(user);
    }

    public void deleteParticipant(User user){
        this.participants.remove(user);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
