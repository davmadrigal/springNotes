package com.example.demo.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Actor;

@RestController
@RequestMapping(value="/agregation", produces = "application/json")
public class AggreationResource {

	// Uses Feign
		private ActorsClient actorsClient;

		// Uses Ribbon to load balance requests
		private LoadBalancerClient loadBalancer;
		private RestTemplate restTemplate;

		// Uses Ribbon to load balance requests
		private RestTemplate loadBalancedRestTemplate;

		@RequestMapping(value = "/actors", method = RequestMethod.GET)
		public List<Actor> findActors() {
			return this.actorsClient.findActors();
		}

		@RequestMapping(value = "/actors1/{id}", method = RequestMethod.GET)
		public Actor findActor1(@PathVariable(value = "id") String id) {
			ServiceInstance instance = loadBalancer.choose(ActorsClient.ACTORS_SERVICE_ID);
			URI uri = instance.getUri();
			String url = String.format("%s%s/{id}", uri, ActorsClient.ACTORS_ENDPOINT);
			return this.restTemplate.getForObject(url, Actor.class, id);
		}

		@RequestMapping(value = "/actors2/{id}", method = RequestMethod.GET)
		public Actor findActor2(@PathVariable(value = "id") String id) {
			String url = String.format("http://%s%s/{id}", ActorsClient.ACTORS_SERVICE_ID, ActorsClient.ACTORS_ENDPOINT);
			return this.loadBalancedRestTemplate.getForObject(url, Actor.class, id);
		}

		@Autowired
		public void setLoadBalancer(LoadBalancerClient loadBalancer) {
			this.loadBalancer = loadBalancer;
		}

		@Autowired
		public void setRestTemplate(RestTemplate restTemplate) {
			this.restTemplate = restTemplate;
		}

		@Autowired
		public void setLoadBalancedRestTemplate(RestTemplate loadBalancedRestTemplate) {
			this.loadBalancedRestTemplate = loadBalancedRestTemplate;
		}

		@Autowired
		public void setActorsClient(ActorsClient actorsClient) {
			this.actorsClient = actorsClient;
		}
}
