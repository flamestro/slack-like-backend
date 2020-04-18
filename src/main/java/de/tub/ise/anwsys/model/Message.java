package de.tub.ise.anwsys.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "Message")
@Getter
@Setter
@NoArgsConstructor
public class Message {
    private String content;
    private String creator;
    private Instant timestamp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Message_Id", unique = true)
    private int id;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

}
