package pl.net.lkd.simcheck;

import com.sun.jersey.api.core.ResourceConfig;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import pl.net.lkd.simcheck.resources.SimResource;

@Slf4j
public class SimApp extends Application<SimAppConfig> {
    public static void main(String[] args) throws Exception {
        new SimApp().run(args);
    }

    @Override
    public String getName() {
        return "SimCheck";
    }

    @Override
    public void initialize(Bootstrap<SimAppConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
    }

    @Override
    public void run(SimAppConfig configuration, Environment environment) {
        environment.jersey().disable(ResourceConfig.FEATURE_DISABLE_WADL);
        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(new SimResource());
    }
}