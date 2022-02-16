package ai.afarms.homie.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.view.Rendering;

import ai.afarms.homie.exception.NotValidRequest;
import ai.afarms.homie.exception.SoldOutException;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
	
	@ExceptionHandler(SoldOutException.class)
	public Mono<Rendering> soldout(){
		return Mono.just(Rendering.view("utility/soldout.html").build());
	}
	
	@ExceptionHandler(NotValidRequest.class)
	public Mono<Rendering> notValidRequest(){
		return Mono.just(Rendering.view("utility/error-404.html").build());
	}
}
