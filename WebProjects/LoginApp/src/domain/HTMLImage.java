package domain;

import java.io.Serializable;

public class HTMLImage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String filename;
	private String extension;

	public HTMLImage(String filename, String extension) {
		this.filename = filename;
		this.extension = extension;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
