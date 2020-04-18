package de.tub.ise.anwsys.controllers;

import de.tub.ise.anwsys.errors.ChannelNotFoundException;
import de.tub.ise.anwsys.model.Message;
import de.tub.ise.anwsys.dto.MessageDTO;
import de.tub.ise.anwsys.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channels")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(value = "/{id}/messages", consumes = "application/json")
    public ResponseEntity<?> postMessage(@PathVariable("id") int channelId, @RequestBody MessageDTO rawMessage) {
        try {
            return new ResponseEntity<>(messageService.postMessage(channelId, rawMessage), HttpStatus.OK);
        } catch (ChannelNotFoundException e) {
            return new ResponseEntity<>("No such Channel", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}/messages")
    PagedResources<Message> getMessages(PagedResourcesAssembler assembler, @PathVariable("id") int channelId) {
        try {
            return assembler.toResource(messageService.getMessages(channelId));
        } catch (ChannelNotFoundException e) {
            return null;
        }
    }
}
