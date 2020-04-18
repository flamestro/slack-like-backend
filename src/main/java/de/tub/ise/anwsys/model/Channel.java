package de.tub.ise.anwsys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Channel")
@Getter
@Setter
@NoArgsConstructor
public class Channel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Channel_Id")
    private int id;
    @JsonIgnore
    @OneToMany(mappedBy = "channel")
    private List<Message> messageList;
    private String topic;
    private String name;
}
