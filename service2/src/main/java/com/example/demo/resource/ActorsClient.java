package com.example.demo.resource;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Actor;

@FeignClient(name = ActorsClient.ACTORS_SERVICE_ID)
public interface ActorsClient {

	String ACTORS_SERVICE_ID= "springCloudService1";
	String ACTORS_ENDPOINT = "/api/actors";
	String ACTOR_BY_ID_ENPOINT = "/api/actors/{id}";
	
	@RequestMapping(method = RequestMethod.GET, value =ACTORS_ENDPOINT )
	List<Actor> findActors();
	
	@RequestMapping(method = RequestMethod.GET, value =ACTOR_BY_ID_ENPOINT )
	Actor getActor(@PathVariable("id") String id);
}
