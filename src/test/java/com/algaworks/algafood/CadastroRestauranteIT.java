package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroRestauranteIT {
	
	private static final int RESTAURANTE_ID_INEXISTENTE = 100;

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private ResourceUtils resourceUtils;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	private Restaurante restauranteMineiro;
	
	private int quantRestaurantesCadastrados;
	
	private String jsonCorretoComidaJaponesaComFrete;
	private String jsonCorretoComidaJaponesaSemFrete;
	private String jsonIncorretoComidaJaponesaCozinhaIdInvalido;
	private String jsonIncorretoComidaJaponesaCozinhaNull;
	private String jsonIncorretoComidaJaponesaFreteGratisNomeIncorreto;
	private String jsonIncorretoComidaJaponesaFreteNull;
	private String jsonIncorretoNomeNull;
	private String jsonIncorretoNomeEmBranco;
	
	@LocalServerPort
	private int port;
		
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		jsonCorretoComidaJaponesaComFrete = resourceUtils.getContentFromResource("/json/correto/restaurante-comida-japonesa-com-frete.json");
		jsonCorretoComidaJaponesaSemFrete = resourceUtils.getContentFromResource("/json/correto/restaurante-comida-japonesa-frete-gratis-nome-correto.json");
		jsonIncorretoComidaJaponesaCozinhaIdInvalido = resourceUtils.getContentFromResource("/json/correto/restaurante-comida-japonesa-cozinha-id-invalido.json");
		jsonIncorretoComidaJaponesaCozinhaNull = resourceUtils.getContentFromResource("/json/correto/restaurante-comida-japonesa-cozinha-null.json");
		jsonIncorretoComidaJaponesaFreteGratisNomeIncorreto = resourceUtils.getContentFromResource("/json/correto/restaurante-comida-japonesa-frete-gratis-nome-incorreto.json");
		jsonIncorretoComidaJaponesaFreteNull = resourceUtils.getContentFromResource("/json/correto/restaurante-comida-japonesa-frete-null.json");
		jsonIncorretoNomeNull = resourceUtils.getContentFromResource("/json/correto/restaurante-nome-null.json");
		jsonIncorretoNomeEmBranco = resourceUtils.getContentFromResource("/json/correto/restaurante-nome-em-branco.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
		given()
			.pathParam("restauranteId", restauranteMineiro.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(restauranteMineiro.getNome()));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestauranteComFrete() {
		given()
			.body(jsonCorretoComidaJaponesaComFrete)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestauranteSemFrete() {
		given()
			.body(jsonCorretoComidaJaponesaSemFrete)
			.accept(ContentType.JSON.withCharset("utf-8"))
			.contentType(ContentType.JSON.withCharset("utf-8"))
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarQuantidadeCorretaDeRestaurantes_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantRestaurantesCadastrados))
			.body("nome", hasItems("Comida Mineira", "Forno de Lenha"));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
		given()
			.pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
			.contentType(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());	
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaIdInexistente() {
		given()
			.body(jsonIncorretoComidaJaponesaCozinhaIdInvalido)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaNull() {
		given()
			.body(jsonIncorretoComidaJaponesaCozinhaNull)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComFreteGratisNomeIncorreto() {
		given()
			.body(jsonIncorretoComidaJaponesaFreteGratisNomeIncorreto)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComFreteNull() {
		given()
			.body(jsonIncorretoComidaJaponesaFreteNull)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComNomeNull() {
		given()
			.body(jsonIncorretoNomeNull)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComNomeEmBranco() {
		given()
			.body(jsonIncorretoNomeEmBranco)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	private void prepararDados() {
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Mineira");
		cozinhaRepository.save(cozinha);
		
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Comida Mineira");
		restaurante1.setTaxaFrete(new BigDecimal(15.00));
		restaurante1.setCozinha(cozinha);
		restauranteRepository.save(restaurante1);
		
		restauranteMineiro = new Restaurante();
		restauranteMineiro.setNome("Forno de Lenha");
		restauranteMineiro.setTaxaFrete(new BigDecimal(15.00));
		restauranteMineiro.setCozinha(cozinha);
		restauranteRepository.save(restauranteMineiro);
		
		quantRestaurantesCadastrados = (int) restauranteRepository.count();
	}
}
