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
		
		// Check if we're in backend directory, if so go up one level
		java.io.File currentFile = new java.io.File(currentDir);
		String frontedPath;
		
		if (currentFile.getName().equals("backend")) {
			// We're in backend directory, go to parent and then fronted
			frontedPath = Paths.get(currentFile.getParent(), "fronted").toAbsolutePath().toString();
		} else {
			// We're in root directory
			frontedPath = Paths.get(currentDir, "fronted").toAbsolutePath().toString();
		}
		
		// Normalize path separators for Windows (use forward slashes)
		frontedPath = frontedPath.replace("\\", "/");
		
		// Ensure the path ends with a separator
		if (!frontedPath.endsWith("/")) {
			frontedPath += "/";
		}
		
		// Log the path for debugging
		System.out.println("===========================================");
		System.out.println("Frontend path: " + frontedPath);
		System.out.println("Current directory: " + currentDir);
		System.out.println("===========================================");
		
		// Verify the directory exists
		java.io.File frontedDir = new java.io.File(frontedPath);
		if (!frontedDir.exists()) {
			System.err.println("WARNING: Frontend directory does not exist: " + frontedPath);
		} else {
			System.out.println("Frontend directory found: " + frontedPath);
		}
		
		// Serve static files from fronted directory
		registry.addResourceHandler("/**")
			.addResourceLocations("file:" + frontedPath)
			.setCachePeriod(0);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// Redirect root path to welcome.html
		registry.addViewController("/").setViewName("forward:/welcome.html");
	}

}
