package de.tub.ise.anwsys.controllers;

import de.tub.ise.anwsys.model.Channel;
import de.tub.ise.anwsys.model.Message;
import de.tub.ise.anwsys.model.MessageJSON;
import de.tub.ise.anwsys.repositories.ChannelRepository;
import de.tub.ise.anwsys.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/channels")
public class MessageController {

    private static int staticSize         = 50;
    private static int staticSizeMessages = 10;



    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    MessageRepository messageRepository;

    @PostMapping(value="/{id}/messages",consumes = "application/json")
    public ResponseEntity<?> postMessage(@PathVariable("id") int id, @RequestBody MessageJSON m) {
        ResponseEntity<?> result ;

        Channel actualChannel = channelRepository.findById(id).get();
        Message msg = mapMessageData(m,actualChannel);
        messageRepository.save(msg);
        actualChannel.addMessage(msg);
        channelRepository.save(actualChannel);

        result= (new ResponseEntity<>(msg, HttpStatus.OK));

        return result;
    }

    @GetMapping(value="/{id}/messages")
    PagedResources<Message> get( PagedResourcesAssembler assembler,@PathVariable("id") int id) {

        Pageable pageable = PageRequest.of(0, 10,new Sort(Sort.Direction.DESC,"id"));
        Channel actualChannel = channelRepository.findById(id).get();
        Page<Message> messages = messageRepository.findByChannel(actualChannel,pageable);
        return assembler.toResource(messages);
    }


    protected Message mapMessageData(MessageJSON messageJSON,Channel channel){
        Message message = new Message();
        message.setTimestamp(Instant.now());
        message.setContent(messageJSON.getContent());
        message.setCreator(messageJSON.getCreator());
        message.setChannel(channel);
        return message;
    }
}
