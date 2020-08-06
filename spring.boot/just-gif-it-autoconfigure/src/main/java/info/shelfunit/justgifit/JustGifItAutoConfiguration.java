package info.shelfunit.justgifit;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import info.shelfunit.justgifit.services.ConverterService;
import info.shelfunit.justgifit.services.GifEncoderService;
import info.shelfunit.justgifit.services.VideoDecoderService;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;

@Configuration
@ConditionalOnClass( { FFmpegFrameGrabber.class, AnimatedGifEncoder.class } )
@EnableConfigurationProperties( JustGifItProperties.class )
public class JustGifItAutoConfiguration {

    /*
    @Value( "${multipart.location}/gif/" )
    private String gifLocation;
    */
    @Inject
    private JustGifItProperties properties;

    // this was in JustGitItApplication with annotation of PostConstructor
    @Bean
    @ConditionalOnProperty( prefix = "info.shelfunit", name = "create-result-dir" )
    public Boolean createResultDir() {
        // File gifFolder = new File( gifLocation );
        if ( !properties.getGifLocation().exists() ) {
            properties.getGifLocation().mkdir();
        }
        return true;
    }

    @Bean
    @ConditionalOnMissingBean( VideoDecoderService.class )
    public VideoDecoderService videoDecoderService() {
        return new VideoDecoderService();
    }

    @Bean
    @ConditionalOnMissingBean( GifEncoderService.class )
    public GifEncoderService gifEncoderService() {
        return new GifEncoderService();
    }

    @Bean
    @ConditionalOnMissingBean( ConverterService.class )
    public ConverterService converterService() {
        return new ConverterService();
    }

    @Configuration
    @ConditionalOnWebApplication
    public static class WebConfiguration {
        @Value( "${multipart.location}/gif/" )
        private String gifLocation;
        // this will disable a filter - how does it get called?
        // for some reason, it does not like it when two of the filters are enabled
        // perhaps they need to update the tutorials
    /*
        @Bean
        @ConditionalOnProperty( prefix = "info.shelfunit", name = "optimize" )
        public FilterRegistrationBean deregisterHiddenHttpMethodFilter( HiddenHttpMethodFilter filter ) {
            FilterRegistrationBean bean = new FilterRegistrationBean( filter );
            bean.setEnabled( false );
            return bean;
        }
        */


        @Bean
        @ConditionalOnProperty( prefix = "info.shelfunit", name = "optimize" )
        public FilterRegistrationBean deregisterRequestContextFilter( RequestContextFilter filter ) {
            FilterRegistrationBean bean = new FilterRegistrationBean( filter );
            bean.setEnabled( false );
            return bean;
        }

        @Bean
        @ConditionalOnProperty( prefix = "info.shelfunit", name = "optimize" )
        public FilterRegistrationBean deregisterHttpPutFormContentFilter( HttpPutFormContentFilter filter ) {
            FilterRegistrationBean bean = new FilterRegistrationBean( filter );
            bean.setEnabled( false );
            return bean;
        }

        @Bean
        public WebMvcConfigurer webMvcConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addResourceHandlers( ResourceHandlerRegistry registry ) {
                    registry.addResourceHandler("/gif/**" )
                            .addResourceLocations( "file:" + gifLocation );
                    super.addResourceHandlers( registry );
                }
            };
        }
    } // WebConfiguration class
}
