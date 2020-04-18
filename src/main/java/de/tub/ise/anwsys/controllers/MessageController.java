package de.tub.ise.anwsys.controllers;

import de.tub.ise.anwsys.model.Message;
import de.tub.ise.anwsys.model.MessageJSON;
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
    public ResponseEntity<?> postMessage(@PathVariable("id") int id, @RequestBody MessageJSON m) {
        return new ResponseEntity<>(messageService.postMessage(id, m), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/messages")
    PagedResources<Message> get(PagedResourcesAssembler assembler, @PathVariable("id") int id) {
        return assembler.toResource(messageService.getMessages(id));
    }
}
