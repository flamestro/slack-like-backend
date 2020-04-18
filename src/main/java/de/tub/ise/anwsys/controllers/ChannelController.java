package de.tub.ise.anwsys.controllers;

import de.tub.ise.anwsys.errors.ChannelAlreadyExistsException;
import de.tub.ise.anwsys.errors.ChannelNotFoundExeption;
import de.tub.ise.anwsys.model.Channel;
import de.tub.ise.anwsys.model.ChannelJSON;
import de.tub.ise.anwsys.repositories.ChannelRepository;
import de.tub.ise.anwsys.services.ChannelService;
import de.tub.ise.anwsys.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    private final ChannelRepository channelRepository;

    private final ChannelService channelService;

    private final UserService userService;

    @Autowired
    public ChannelController(UserService userService, ChannelService channelService, ChannelRepository channelRepository) {
        this.userService = userService;
        this.channelService = channelService;
        this.channelRepository = channelRepository;
    }

    @GetMapping(produces = "application/json")
    PagedResources<Channel> get(@PageableDefault Pageable p, PagedResourcesAssembler assembler) {
        Page<Channel> channels = channelRepository.findAll(p);
        return assembler.toResource(channels);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getChannel(@PathVariable("id") int id) {
        ResponseEntity<?> result;
        try {
            Channel channel = channelService.getChannel(id);
            result = (new ResponseEntity<>(channel, HttpStatus.OK));
        } catch (ChannelNotFoundExeption e) {
            result = (new ResponseEntity<>("", HttpStatus.OK));
        }
        return result;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> postChannel(@RequestBody ChannelJSON c) {
        ResponseEntity<?> result;

        try {
            Channel channel = channelService.postChannel(c);
            result = (new ResponseEntity<>(channel, HttpStatus.OK));
        } catch (ChannelAlreadyExistsException e) {
            result = (new ResponseEntity<>("Channel is already existing", HttpStatus.CONFLICT));
        }

        return result;
    }

    @GetMapping(value = "/{id}/users", consumes = "application/json")
    public ResponseEntity<?> getUsers(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.getChannelUsers(id), HttpStatus.OK);
    }
}

