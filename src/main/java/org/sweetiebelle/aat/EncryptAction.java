package org.sweetiebelle.aat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class EncryptAction implements Consumer<Path> {

	@Override
	public void accept(Path path) {
		try {
			File file = path.toFile();
			if (file.isDirectory())
				return;
			String command = String.format("./EncryptTools.exe d %s %s", getOldFile(file), getNewFile(file));
			System.out.println(command);
			Runtime.getRuntime().exec(command);
		} catch (Throwable e) {
			e.printStackTrace(System.err);
		}
	}

	private String getOldFile(File file) {
		Path current = Paths.get("");
		String path = current.relativize(file.toPath()).toString();
		return path.substring(3, path.length());
	}

	private String getNewFile(File file) {
		String name = file.getName();
		String[] parts = name.split("\\.(?=[^\\.]+$)");
		return parts[0] + "-decrypted" + parts[1];
	}

}