package info.shelfunit.justgifit;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass( { FFmpegFrameGrabber.class, AnimatedGifEncoder.class } )
public class JustGifItAutoConfiguration {
}
