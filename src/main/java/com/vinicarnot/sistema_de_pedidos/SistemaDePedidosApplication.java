package com.vinicarnot.sistema_de_pedidos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "sistema-de-pedidos", version = "1",
        description = "API desenvolida para simular um e-commerce."))
@SpringBootApplication
public class SistemaDePedidosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDePedidosApplication.class, args);
	}

}
