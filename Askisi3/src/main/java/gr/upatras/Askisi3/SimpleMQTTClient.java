package gr.upatras.Askisi3;
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


public class SimpleMQTTClient implements MqttCallback {
	
	MqttClient myClient;
	MqttConnectOptions connOpt;
	
	
	static final String M2MIO_THING = UUID.randomUUID().toString();
	static final String BROKER_URL = "tcp://test.mosquitto.org:1883";
	
	static final Boolean subscriber = true;
	static final Boolean publisher = true;
	
	
	private Random rnd = new Random();
	private static final Logger log = LoggerFactory.getLogger(SimpleMQTTClient.class);
	public static final String TOPIC = "grupatras/AdvCompTech/Lab/Askisi3";

	
	/**
	 *
	 * connectionLost This callback is invoked upon losing the MQTT connection.
	 *
	 */
	
	public void connectionLost(Throwable t) {
		log.info("Connection lost!");
		// code to reconnect to the broker would go here if desired
	}

	
	/**
	 *
	 * deliveryComplete This callback is invoked when a message published by this
	 * client is successfully received by the broker.
	 *
	 */
	
	public void deliveryComplete(IMqttDeliveryToken token) {
	}

	
	public void sendMess(MqttTopic topic, String yourMess){
		String pubMsg = "";
		pubMsg =  yourMess + " ";
		
		int pubQoS = 0;
		MqttMessage message = new MqttMessage(pubMsg.getBytes());
		message.setQos(pubQoS);
		message.setRetained(false);
		//Publish the message
		log.info("Publishing to topic \"" + topic + " \" -- \"qos" + pubQoS + "\" -- value: \"" + yourMess+ "\" ");
		MqttDeliveryToken token = null;
		try {
			
			//publish message to broker
			token = topic.publish(message);
			
			//Wait until the message has been delivered to the broker
			token.waitForCompletion();
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 *
	 * messageArrived This callback is invoked when a message is received on a
	 * subscribed topic.
	 *
	 */
	
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		log.info("\n");
		log.info("-------------------------------------------------");
		log.info("| Topic:" + topic);
		log.info("| Message: " + new String(message.getPayload()));
		log.info("-------------------------------------------------");
		log.info("\n");
	}

	
	
	
	/**
	 *
	 * MAIN
	 *
	 */
	public static void runMQTT() {
		SimpleMQTTClient smc = new SimpleMQTTClient();
		smc.runClient();
	}

	
	
	/**
	 *
	 * runClient The main functionality of this simple example. Create a MQTT
	 * client, connect to broker, pub/sub, disconnect.
	 *
	 */
	
	public void runClient() {
		
		// setup MQTT Client
		String clientID = M2MIO_THING;
		connOpt = new MqttConnectOptions();
		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
		// Connect to Broker
		try {
			myClient = new MqttClient(BROKER_URL, clientID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		log.info("Connected to " + BROKER_URL);
		String myTopic = TOPIC;
		MqttTopic topic = myClient.getTopic(myTopic);
		// subscribe to topic if subscriber
		if (subscriber) {
			try {
				int subQoS = 0;
				myClient.subscribe(myTopic, subQoS);
				if (!publisher) {
					while (true) {
						Thread.sleep(1000);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// publish messages if publisher
		if (publisher) {
			//while (true) {
				//sendMess(topic);
			//}
		}
		// disconnect
		try {
			// wait to ensure subscribed messages are delivered
			if (subscriber) {
				Thread.sleep(1000);
			}
			//myClient.disconnect();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
