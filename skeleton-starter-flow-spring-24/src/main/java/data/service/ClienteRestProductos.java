package data.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.demoprogb.objects.entities.Marca;
import com.demoprogb.objects.entities.Producto;
import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent("ClienteRestProductos")
@Scope("singleton")
@PropertySource(value={"application.properties"})
//@Autowired(required=true)
@Service
public class ClienteRestProductos {
	protected final Logger logger = LoggerFactory.getLogger(ClienteRestProductos.class);
	
    @Value("${serviceproductos.address}")  // propiedad declarada en application.properties
    private String address;
    private String branch = "rest/";

	public RestTemplate createRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	public List<Marca> getMarcas(String filterName) throws ResourceAccessException {
		String uri = address.concat(branch).concat("getMarcas").concat("?filterName=").concat(filterName);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		RestTemplate restTemplate = createRestTemplate();
		try {
			ResponseEntity<List<Marca>> res = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
					HttpEntity.EMPTY, new ParameterizedTypeReference<List<Marca>>() {
			});
			return res.getBody();
		}catch (ResourceAccessException e) {
			    // Manejo de errores de conexión
			    //System.err.println("Error de conexión: " + e.getMessage());
				e.printStackTrace();
			    return new ArrayList<Marca>();

		} catch (HttpClientErrorException e2) {
			return new ArrayList<Marca>();
		}
	}
	
	public Marca getMarcaById(Long id) throws ResourceAccessException {
		String uri = address.concat(branch).concat("getMarca").concat("?marcaId=").concat(id.toString());
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		RestTemplate restTemplate = createRestTemplate();
		try {
			ResponseEntity<Marca> res = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
					HttpEntity.EMPTY, new ParameterizedTypeReference<Marca>() {
			});
			return res.getBody();
		} catch (HttpClientErrorException e) {
			return null;
		}
	}

	public Marca saveMarca(Marca marca) throws ResourceAccessException , HttpClientErrorException {
		String uri = address.concat(branch).concat("saveMarca");
		RestTemplate restTemplate = createRestTemplate();
		return restTemplate.postForObject(uri, marca, Marca.class);
	}
	
	public void deleteMarca(Marca marca) {
		String uri = address.concat(branch).concat("deleteMarca");
		RestTemplate restTemplate = createRestTemplate();
		restTemplate.postForObject(uri, marca, Marca.class);		
	}
	
	public List<Producto> getProductos(String filterName) throws ResourceAccessException {
		String uri = address.concat(branch).concat("getProductos").concat("?filterName=").concat(filterName);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
		RestTemplate restTemplate = createRestTemplate();
		try {
			ResponseEntity<List<Producto>> res = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
					HttpEntity.EMPTY, new ParameterizedTypeReference<List<Producto>>() {
			});
			return res.getBody();
		} catch (HttpClientErrorException e) {
			return new ArrayList<Producto>();
		}
	}
	public Producto saveProducto(Producto producto) throws ResourceAccessException , HttpClientErrorException {
		String uri = address.concat(branch).concat("saveProducto");
		RestTemplate restTemplate = createRestTemplate();
		return restTemplate.postForObject(uri, producto, Producto.class);
	}
	
	public void deleteProducto(Producto producto) {
		String uri = address.concat(branch).concat("deleteProducto");
		RestTemplate restTemplate = createRestTemplate();
		restTemplate.postForObject(uri, producto, Producto.class);		
	}
}

