package kr.komec.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="serverConfig")
@Component
public class ServerConfig {

	private String webfileDir;

	public String getWebfileDir() {
		return webfileDir;
	}

	public void setWebfileDir(String webfileDir) {
		this.webfileDir = webfileDir;
	}
}
