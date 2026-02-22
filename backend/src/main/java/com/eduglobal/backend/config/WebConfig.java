package com.eduglobal.backend.config;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(false);
		
		registry.addMapping("/apis/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(false);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Get the absolute path to fronted directory
		String currentDir = System.getProperty("user.dir");
		String frontedPath = Paths.get(currentDir, "fronted").toAbsolutePath().toString();
		
		// Ensure the path ends with a separator
		if (!frontedPath.endsWith(File.separator)) {
			frontedPath += File.separator;
		}
		
		// Serve static files from fronted directory
		registry.addResourceHandler("/**")
			.addResourceLocations("file:" + frontedPath, "classpath:/static/")
			.setCachePeriod(0);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// Redirect root path to index.html
		registry.addViewController("/").setViewName("forward:/index.html");
	}

}
