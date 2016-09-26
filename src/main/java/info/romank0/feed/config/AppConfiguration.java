package info.romank0.feed.config;  
  
import info.romank0.feed.ContentAtomView;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration 
@ComponentScan("info.romank0.feed") 
public class AppConfiguration extends WebMvcConfigurerAdapter  {  
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.enableContentNegotiation(new ContentAtomView());
    }
}  
 