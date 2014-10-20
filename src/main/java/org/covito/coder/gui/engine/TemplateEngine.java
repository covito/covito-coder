package org.covito.coder.gui.engine;

import java.util.Map;

import org.covito.coder.config.model.TemplateElement;


public interface TemplateEngine {

    public String processToString(Map<String, Object> model, String stringTemplate) throws TemplateEngineException;

    public void processToFile(Map<String, Object> model, TemplateElement templateElement) throws TemplateEngineException;
}
