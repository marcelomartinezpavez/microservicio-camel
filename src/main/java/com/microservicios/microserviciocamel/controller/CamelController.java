package com.microservicios.microserviciocamel.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.converter.stream.CachedByteArrayOutputStream;
import org.apache.camel.converter.stream.CachedOutputStream;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class CamelController {
	@Autowired
    ProducerTemplate camelProducer;


    @GetMapping("test")
    public Object getCliente(@RequestHeader(required = true) String authorization){
    	System.out.println("Antes Response");
    	Map<String, Object> body = new HashMap<String, Object>();
    	Map<String, Object> paramsCustomerOffer = new HashMap<String, Object>();
    	paramsCustomerOffer.put("authorization", authorization);
    	Object response = camelProducer.requestBodyAndHeaders("direct:prueba1",body,paramsCustomerOffer);
        //Object response = camelProducer.requestBodyAndHeader("direct:cliente", null, null);
        System.out.println("Despues Response");
        return new ResponseEntity(response, HttpStatus.OK);

    }

}
