package com.trustpilot.bsdtest;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class AmandeepController {

	@RequestMapping("/")
	public String index() {
		return "Please provide Morpheus' Ship Name in the URL format -> http://[host]/ship/[morpheusShipName]";
	}

	@RequestMapping(value = "/ship/{name}")
	public String getShip(@PathVariable String name) {

		System.out.println("Ship name input: " + name);

		StringBuilder buf = new StringBuilder();
		String answer = name; //Should be "Nebuchadnezzar"
		MessageDigest m;
		String answermd5 = "";

		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(answer.toLowerCase().getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			answermd5 = bigInt.toString(16);
			while(answermd5.length() < 32)
				answermd5 = "0"+answermd5;
			System.out.println("MD5 of answer = " + answermd5);

			} catch (NoSuchAlgorithmException e) {

				e.printStackTrace();
			}
		
		 ClientConfig config = new ClientConfig();
		 Client client = ClientBuilder.newClient(config);
		 
		 WebTarget target = client.target(UriBuilder.fromUri("http://followthewhiterabbit.trustpilot.com/sd/"+answermd5+".json").build());
		 
		 Response response = target.request(MediaType.APPLICATION_JSON).get();
		 
		 if (response.getStatus() == 200) {
			 
			 return target.request(MediaType.APPLICATION_JSON).get(String.class);

		  }
		 
		return "Wrong Ship name";
	}

}
