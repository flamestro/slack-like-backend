package de.tub.ise.anwsys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Channel")
public class Channel implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Channel_Id")
    private int    id           ;
    private String topic = null ;
    private String name  = null ;
    @JsonIgnore
    @OneToMany(mappedBy = "channel")
    private List<Message> messageList = null ;

    public String getName() {
        return name ;
    }

    public Channel (){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMessage(Message message){
        this.messageList.add(message);
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
