package com.example.mancala.integrationTest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
public class EndPointTest {

    private String url = "http://localhost:8080/mancala";
    private String startGamePath = "/start/${cellCount}/${pitAmount}";
    private String starterPlayerPath = "/play/starterPlayer/${playerNumber}";
    private String playPath = "/play/${cellNumber}";

    @Autowired
    WebTestClient webTestClient;


    @Test
    @Order(1)
    public void startGame() {

        String startGameUrlEndpoint = url + startGamePath;

        webTestClient
                .post()
                .uri(startGameUrlEndpoint.replace("${cellCount}", "6").replace("${pitAmount}", "6"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Mancala is Start with 2 Player.");

        webTestClient
                .put()
                .uri(startGameUrlEndpoint.replace("${cellCount}", "6").replace("${pitAmount}", "6"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(405));
        webTestClient
                .get()
                .uri(startGameUrlEndpoint.replace("${cellCount}", "6").replace("${pitAmount}", "6"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(405));

    }

    @Test
    @Order(2)
    public void starterPlayer() {

        String startPlayerUrlEndpoint = url + starterPlayerPath;

        WebTestClient.BodySpec gameNotStarted = webTestClient
                .put()
                .uri(startPlayerUrlEndpoint.replace("${playerNumber}", "2"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(406))
                .expectBody(String.class);

        assertEquals(gameNotStarted.returnResult().getMockServerResult().toString().contains("game is not start yet please start from new Game."), true);
        startGame();

        webTestClient
                .put()
                .uri(startPlayerUrlEndpoint.replace("${playerNumber}", "2"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Player second player must Start.");


        WebTestClient.BodySpec invalidPlayerNumber = webTestClient
                .put()
                .uri(startPlayerUrlEndpoint.replace("${playerNumber}", "6"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(406))
                .expectBody(String.class);

        assertEquals(invalidPlayerNumber.returnResult().getMockServerResult().toString().contains("please choose a number between 1 and 2"), true);


        webTestClient
                .post()
                .uri(startPlayerUrlEndpoint.replace("${playerNumber}", "2"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(405));

        webTestClient
                .get()
                .uri(startPlayerUrlEndpoint.replace("${playerNumber}", "2"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(405));
    }


    @Test
    @Order(2)
    public void play() {
        startGame();
        String playUrlEndpoint = url + playPath;
        webTestClient
                .put()
                .uri(playUrlEndpoint.replace("${cellNumber}", "1"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("{\"valuesSecondPlayer\":\"0  || {6}, {6}, {6}, {6}, {6}, {6} ||  1\",\"valuesFirstPlayer\":\"    || {0}, {7}, {7}, {7}, {7}, {7} ||   \",\"nextPlayer\":\"first player\"}");

        WebTestClient.BodySpec notValidCell = webTestClient
                .put()
                .uri(playUrlEndpoint.replace("${cellNumber}", "1"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(406))
                .expectBody(String.class);

        assertEquals(notValidCell.returnResult().getMockServerResult().toString().contains("not valid Cell, please choose another cell."), true);

        webTestClient
                .put()
                .uri(playUrlEndpoint.replace("${cellNumber}", "2"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("{\"valuesSecondPlayer\":\"0  || {6}, {6}, {6}, {6}, {7}, {7} ||  2\",\"valuesFirstPlayer\":\"    || {0}, {0}, {8}, {8}, {8}, {8} ||   \",\"nextPlayer\":\"second player\"}");

    }
}
