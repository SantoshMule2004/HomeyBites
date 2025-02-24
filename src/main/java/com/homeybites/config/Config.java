package com.homeybites.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class Config {

	@Value("${config.cloudinary.cloud.key}")
	private String key;

	@Value("${config.cloudinary.cloud.secret}")
	private String secret;

	@Value("${config.cloudinary.cloud.name}")
	private String name;

	@Bean
	protected ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	protected Cloudinary cloudinary() {
		return new Cloudinary(ObjectUtils.asMap("cloud_name",name, "api_key",key, "api_secret",secret));
	}
}
