package com.example.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Actor;

@RestController
public class ActorController {
	
	@RequestMapping("/actors")
	public List<Actor> findActors() {
		List<Actor> result = new ArrayList<>();
		result.add(this.buildActor("1", "First1", "Last1"));
		result.add(this.buildActor("2", "First2", "Last2"));
		return result;
		
	}
	
	@RequestMapping("/actors/{id}")
	public String getActor(@PathVariable("id") String id) {
		return this.buildActor(id, String.format("First%s", id), String.format("Last%s", id)).toString();
		
	}

	
	private Actor buildActor(String id, String firstName, String lastName) {
		Actor result = new Actor();
		result.setActorId(id);
		result.setFirstName(firstName);
		result.setLastName(lastName);
		return result;
	}
}
