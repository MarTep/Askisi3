package gr.upatras.Askisi3;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gr.upatras.Askisi3.IMessageService;
import gr.upatras.Askisi3.Message;
import gr.upatras.Askisi3.MessageController;
//import gr.upatras.mqtt.SimpleMQTTClient;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





@RestController
public class MessageController {
	
	@Autowired
	private IMessageService messageService;
	private static final Logger log = LoggerFactory.getLogger(MessageController.class);
	
	
	@ApiOperation(value = "Get Message History", notes = "This operation retrieves all Message entities.", response = Message.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Message.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
			@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
			@ApiResponse(code = 404, message = "Not Found", response = Error.class),
			@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
			@ApiResponse(code = 409, message = "Conflict", response = Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/message/", produces = { "application/json;charset=utf-8" }, method = RequestMethod.GET)
	public List<Message> getMessage() {
		
	
		// finds all the messages ( Lucky Numbers and Your Numbers
		List<Message> messages = messageService.findAll();
		
		// returns the messages list
		return messages;
	}

	@ApiOperation(value = "Add your Message", notes = "Your Message will publish via MQTT", response = Message.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = Message.class),
			@ApiResponse(code = 400, message = "Bad Request", response = Error.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
			@ApiResponse(code = 403, message = "Forbidden", response = Error.class),
			@ApiResponse(code = 405, message = "Method Not allowed", response = Error.class),
			@ApiResponse(code = 409, message = "Conflict", response = Error.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
	@RequestMapping(value = "/message", produces = { "application/json;charset=utf-8" }, consumes = {
			"application/json;charset=utf-8" }, method = RequestMethod.POST)
	public ResponseEntity<Message> createMessage(
			@ApiParam(value = "The Message to be created", required = true) @RequestBody Message p) {
		log.info("Will add a new message");
		// Get Topic
		String myTopic = messageService.class2.TOPIC;
		MqttTopic topic =  messageService.class2.myClient.getTopic(myTopic);
		// Get the random number from SimpleMQTTClient.java 
		messageService.class2.sendMess(topic, p.getyourMessage());
		
		// Add it to message so it can be stored to history
		Message message = messageService.addMessage(p);
		
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}


}
