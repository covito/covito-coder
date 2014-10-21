package org.covito.coder.gui.engine;

import java.util.HashMap;
import java.util.Map;


public class EngineBuilder {

    private Map<String, TemplateEngine> engineMap;

    public EngineBuilder() {
        engineMap = new HashMap<String, TemplateEngine>();
        synchronized (this) {
            engineMap.put("freemarker", new FreeMarkerImpl());
            engineMap.put("velocity", new VelocityImpl());
        }
    }

    public TemplateEngine getTemplateEngine(String engine) {
        return engineMap.get(engine);
    }
}
