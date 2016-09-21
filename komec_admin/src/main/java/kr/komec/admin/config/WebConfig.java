package kr.komec.admin.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
	    /*
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
		*/
		
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        HibernateAwareObjectMapper hibernateAwareObjectMapper = new HibernateAwareObjectMapper();
        jsonConverter.setObjectMapper(hibernateAwareObjectMapper);
        return jsonConverter;
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter2() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
	 
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/index").setViewName("index");
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//converters.add(customJackson2HttpMessageConverter());
		converters.add(customJackson2HttpMessageConverter2());
		super.configureMessageConverters(converters);
	}
}
