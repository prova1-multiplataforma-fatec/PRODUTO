package io.github.fatec.integracao;

import io.github.fatec.dto.ClienteIntegracaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
		name = "clienteIntegracao",
		url = "${integracao.gateway-url}",
		configuration = ClienteIntegracaoFeignConfig.class)
public interface ClienteIntegracao {

	@GetMapping("/api-clientes/cliente/{id}")
	ClienteIntegracaoResponse buscarPorId(@PathVariable("id") String id);
}
