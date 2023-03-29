package gameonline.rest.controller_user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(path = "/api/lobby/data/realtime-multiplayer")
public class Controller009_RealtimeMultiplayerScreen {
	public static String PATH = null;

	@GetMapping(value = "/demoError")
	public ObjectNode demoError() {
		ObjectNode node = new ObjectMapper().createObjectNode();
		node.put("ValueNumber", 6503556831371730829l);
		node.put("ValueString", "6503556831371730829");

		return node;
	}
}
