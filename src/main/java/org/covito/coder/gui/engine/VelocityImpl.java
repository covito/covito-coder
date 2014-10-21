package org.covito.coder.gui.engine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.covito.coder.config.model.TemplateElement;
import org.covito.coder.util.StringUtil;

public class VelocityImpl implements TemplateEngine {

    private static final VelocityEngine engine = new VelocityEngine();

    public VelocityImpl(){
        Properties props = new Properties();
        props.setProperty(Velocity.INPUT_ENCODING, "UTF-8");// 设置输入字符集
        props.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");// 设置输出字符集
        props.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        props.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
        props.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "templates/velocity");
        engine.init(props);
    }

    public String processToString(Map<String, Object> model, String stringTemplate) throws TemplateEngineException {
        try {
            VelocityContext context = new VelocityContext(model);
            StringWriter writer = new StringWriter();
            engine.evaluate(context, writer, "", stringTemplate);
            return writer.toString();
        } catch (Exception e) {
            throw new TemplateEngineException(e.getMessage(), e);
        }
    }

    public void processToFile(Map<String, Object> model, TemplateElement templateElement)
                                                                                         throws TemplateEngineException {
        try {
            Template template = engine.getTemplate(templateElement.getTemplateFile(), templateElement.getEncoding());
            VelocityContext context = new VelocityContext(model);

            String targetPath = StringUtil.packagePathToFilePath(processToString(model, templateElement.getTargetPath()));
            String targetFileName = processToString(model, templateElement.getTargetFileName());
            File file = new File(targetPath + File.separator + targetFileName);
            File directory = new File(targetPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),
                                                                   templateElement.getEncoding()));
            template.merge(context, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new TemplateEngineException(e.getMessage(), e);
        }
    }

}
