package lv.rtu.resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Resource {

	String sourcePath = "C:\\test\\resource.txt";

	public void createFile() throws IOException {

		Path path = Paths.get(sourcePath);

		Files.createDirectories(path.getParent());

		try {
			Files.createFile(path);
			System.out.println("File is created!");
		} catch (FileAlreadyExistsException e) {
			System.err.println("already exists: " + e.getMessage());
		}
	}

	public void deleteFile() {
		File file = new File(sourcePath);

		if (file.delete()) {
			System.out.println("File is deleted!");
		} else {
			System.err.println("Delete operation is failed.");
		}
	}

	public String readFromFile() throws IOException {

		File file = new File(sourcePath);

		String text = "";
		FileReader fr = null;
		BufferedReader br = null;
		int read, N = 1024 * 1024;

		char[] buffer = new char[N];

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			while (true) {
				read = br.read(buffer, 0, N);
				text += new String(buffer, 0, read);

				if (read < N) {
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			fr.close();
			br.close();
		}

		return text;
	}

	public void writeToFile(String text) throws IOException {

		File file = new File(sourcePath);
		BufferedWriter output = null;

		try {
			output = new BufferedWriter(new FileWriter(file));
			output.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			output.close();
		}
	}
}
