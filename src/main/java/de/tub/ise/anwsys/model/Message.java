package de.tub.ise.anwsys.model;
import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="Message")
public class Message {

    private String  content ;
    private String  creator ;
    private Instant timestamp ;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Message_Id", unique = true)
    private int id ;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel ;

    public Message(){
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
