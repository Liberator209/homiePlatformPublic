package ai.afarms.homie.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.mongodb.MongoClientSettings;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;

@org.springframework.context.annotation.Configuration
public class Configuration implements WebFluxConfigurer, ApplicationContextAware {

	ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = context;
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**",
        		"/resources/**")
            .addResourceLocations("classpath:/static/assets/",
            		"classpath:/static/resources/");
    }
	@Bean
	public ITemplateResolver thymeleafTemplateResolver() {
	    final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
	    resolver.setApplicationContext(this.context);
	    resolver.setPrefix("classpath:templates/");
	    resolver.setSuffix(".html");
	    resolver.setTemplateMode(TemplateMode.HTML);
	    resolver.setCacheable(false);
	    resolver.setCheckExistence(false);
	    return resolver;
	}
	
	@Bean
	public ISpringWebFluxTemplateEngine thymeleafTemplateEngine() {
	    SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
	    templateEngine.setTemplateResolver(thymeleafTemplateResolver());
	    return templateEngine;
	}
	
	@Bean
	public ThymeleafReactiveViewResolver thymeleafReactiveViewResolver() {
	    ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
	    viewResolver.setTemplateEngine(thymeleafTemplateEngine());
	    return viewResolver;
	}
	
//	@Override
//	public void configureViewResolvers(ViewResolverRegistry registry) {
//	    registry.viewResolver(thymeleafReactiveViewResolver());
//	}
//	    @Override
//	    public void addCorsMappings(CorsRegistry registry) {
//	        registry.addMapping("/**")
//	                .allowedOrigins("*");
//	}
}

