package pl.mg.rac.rent.websocket;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketConfigTest {

    @LocalServerPort
    private int port;

    private WebSocketStompClient stompClient;
    private CompletableFuture<Boolean> completableFuture;

    @BeforeEach
    public void setup() {
        this.stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.completableFuture = new CompletableFuture<>();
    }

    @Test
    public void testWebSocketConnection() throws InterruptedException, ExecutionException, TimeoutException {
        StompSessionHandlerAdapter sessionHandler = new TestSessionHandler();
        this.stompClient.connectAsync("ws://localhost:{port}/ws", sessionHandler, port);

        // Wait for the connection to be established
        Boolean isConnected = completableFuture.get(10, TimeUnit.SECONDS);

        assertTrue(isConnected);
    }

    private class TestSessionHandler extends StompSessionHandlerAdapter {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            completableFuture.complete(true);
        }
    }
}