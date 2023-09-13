package com.example.ticket_api.controllers;

import com.example.ticket_api.entities.Event;
import com.example.ticket_api.entities.dto.EventListDTO;
import com.example.ticket_api.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    EventService eventServ;

    @GetMapping({"", "/all"})
    public List<Event> getAllEvents(){

        List<Event> eventList = eventServ.findAllEvents();
        return eventList;
    }

    @GetMapping("/{id}")
    public Event getOneEvent(@PathVariable Long id){

        Event eventList = eventServ.findOneEvent(id).get();
        return eventList ;
    }

    @GetMapping({"/list"})
    public ResponseEntity < List<EventListDTO> > listEvents(){

        List<Event> eventList = eventServ.findAllEvents();
        List<EventListDTO> eventDtoList = null;

        if (eventList != null) {

            eventDtoList = eventServ.findAllEvents().stream().map(event -> event.toDto()).toList();
            return ResponseEntity.ok(eventDtoList);
        } else {
            return ResponseEntity.status(404).build();
        }

    }

    @PostMapping("/add")
    public ResponseEntity createEvent(@RequestBody Event event){

        if (event != null && eventServ.findOneEvent(event.getId()) == null) {
            eventServ.create(event);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(409).build();
        }

    }

    @PutMapping("/update")
    public ResponseEntity updateEvent(@RequestBody Event event){

        if (event != null && eventServ.findOneEvent(event.getId()) == null) {
            eventServ.create(event);
            //TODO: vérifier que c'est la bonne méthode à utiliser
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(409).build();
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEvent(@PathVariable Long id){

        if (eventServ.findOneEvent(id) != null) {
            eventServ.deleteEvent(id);
            return ResponseEntity.status(410).build();
        } else {
            return ResponseEntity.status(404).build();
        }

    }

}
