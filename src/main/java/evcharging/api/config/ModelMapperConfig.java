package evcharging.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	ModelMapper modelMapper() {
		final ModelMapper modelMapper = new ModelMapper();
		modelMapper.validate();
		return modelMapper;
	}

}
