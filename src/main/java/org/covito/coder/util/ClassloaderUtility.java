package org.covito.coder.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.covito.coder.CoderHepler;

public class ClassloaderUtility {

	private ClassloaderUtility() {
	}

	public static ClassLoader getCustomClassloader(List<String> entries) {
		List<URL> urls = new ArrayList<URL>();
		File file;

		if (entries != null) {
			for (String classPathEntry : entries) {
				file = new File(classPathEntry);
				CoderHepler.logger.debug("Loading jar : " + file.getPath());
				if (!file.exists()) {
					throw new RuntimeException(MessagesUtil.getString("RuntimeError.4", classPathEntry));
				}

				try {
					urls.add(file.toURI().toURL());
				} catch (MalformedURLException e) {
					throw new RuntimeException(MessagesUtil.getString("RuntimeError.4", classPathEntry));
				}
			}
		}

		ClassLoader parent = Thread.currentThread().getContextClassLoader();

		URLClassLoader ucl = new URLClassLoader(urls.toArray(new URL[urls.size()]), parent);

		return ucl;
	}
}
