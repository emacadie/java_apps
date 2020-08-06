package info.shelfunit.justgifit;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@ConfigurationProperties( prefix = "info.shelfunit" )
public class JustGifItProperties {
    /**
     * The location of the animated gifs
     */
    private File gifLocation;

    public File getGifLocation() {
        return gifLocation;
    }

    public void setGifLocation( File gifLocation ) {
        this.gifLocation = gifLocation;
    }

    /**
     * Whether or not to optimize web filters
     */
    private Boolean optimize;

    public Boolean getOptimize() {
        return optimize;
    }

    public void setOptimize(Boolean optimize) {
        this.optimize = optimize;
    }
}
