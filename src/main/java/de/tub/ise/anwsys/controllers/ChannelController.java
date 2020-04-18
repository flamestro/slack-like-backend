package de.tub.ise.anwsys.controllers;

import de.tub.ise.anwsys.model.*;
import de.tub.ise.anwsys.repositories.ChannelRepository;
import de.tub.ise.anwsys.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    ChannelRepository channelRepository;

    @GetMapping( produces = "application/json")
    PagedResources<Channel> get(@PageableDefault Pageable p, PagedResourcesAssembler assembler) {
        Page<Channel> channels = channelRepository.findAll(p);
        return assembler.toResource(channels);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<?> getChannel(@PathVariable("id") int id) {
        ResponseEntity<?> result ;

            if(channelRepository.findById(id).isPresent()) {
                Channel actualChannel = channelRepository.findById(id).get();
                result= (new ResponseEntity<>(actualChannel, HttpStatus.OK));
            }
            else {
                result= (new ResponseEntity<>("", HttpStatus.OK));
            }

        return result;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> postChannel(@RequestBody ChannelJSON c) {
        ResponseEntity<?> result ;

            if(channelRepository.findAll().stream().filter(channel -> channel.getName().equals(c.getName())).count()>=1){
                result= (new ResponseEntity<>("CONFLICT 409", HttpStatus.CONFLICT));
            }
            else {
                Channel channel = mapChannelData(c);
                channelRepository.save(channel);
                result= (new ResponseEntity<>(channel, HttpStatus.OK));
            }

        return result;
    }


    @GetMapping(value="/{id}/users",consumes = "application/json")
    public ResponseEntity<?> getUsers(@PathVariable("id") int id) {
        ResponseEntity<?> result;

            ArrayList<Message> messages = new ArrayList<>();
            ArrayList<String> users     = new ArrayList<>();
            if(channelRepository.findById(id).isPresent()) {
                Channel actualChannel = channelRepository.findById(id).get();
                messages.addAll(actualChannel.getMessageList());
            }

            for(Message m : messages){
                users.add(m.getCreator());
            }
            Set<String> usersSet        = new HashSet<>(users);
            users = new ArrayList<>();
            users.addAll(usersSet);
            result= (new ResponseEntity<>(users, HttpStatus.OK));

        return result;
    }


    protected Channel mapChannelData(ChannelJSON channelJSON){
        Channel channel = new Channel();
        channel.setTopic(channelJSON.getTopic());
        channel.setName(channelJSON.getName());
        channel.setMessageList(null);
        return channel;
    }


}

