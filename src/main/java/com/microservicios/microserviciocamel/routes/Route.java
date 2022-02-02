package com.microservicios.microserviciocamel.routes;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpMethods;
import org.springframework.stereotype.Component;

@Component
public class Route extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// timer
		// transformation
		// log
		// Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		//from("timer:first-timer") //queue
		//.transform().constant("La hora es: "+ LocalDateTime.now())
		//.bean("getHoraActual")
		//.to("log:first-timer"); //database
		
		from("direct:cliente") //dejar siempre direct debido a que llega con el nombre
        //.routeId("getCliente") //nombre dado a la ruta, nombre de libre eleccion, buena practica dejar mismo nombre que en el direct
        //.split().jsonpath("$.*") //para identificar todos los elementos en el path o header
        //.parallelProcessing() //indica que sea un proceso en paralelo al de ejecucion
		.setBody(simple("${body}"))
		.log("log-3 ${body}") //llena un log
        //.setHeader("key").simple("${body}") //definir headers del endpoint a consumir
        .doTry() //capturar exceptions
        .toD("http://localhost:8080/cliente/listarTodo?bridgeEndpoint=true&httpMethod=GET") //define el endpoint
        .setBody(simple("${body}")) //devuelve la respuesta del servicio
        //.log("log-3.1 ${headers} ${body}")
        .doCatch(Exception.class)
        .log("Error: ${exception.message}")
        //.doFinally()
        .log("Proceso finalizado!!").end();
		
		from("direct:prueba1")
		//.setBody(simple("${body}"))
		.setHeader("Authorization").simple("Basic")
		.setHeader("Content-Type").simple("application/json")
		.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.GET))
		//TODO se debe agregar el rut a la url

		//.toD("https://mindicador.cl/api?bridgeEndpoint=true&httpMethod=GET&authMethod=Basic");
		.toD("https://mindicador.cl/api?bridgeEndpoint=true")
		.setBody(simple("onComplete:${body}"))
		.log(">>> ${body}")
		//.log("HEADERS PRUEBA1 ${headers} ${headers.authorization}")
		.end();
		
	}

}

@Component
class GetHoraActual {
	public String getHoraActual() {
		return "La hora es: "+ LocalDateTime.now();
	}
}