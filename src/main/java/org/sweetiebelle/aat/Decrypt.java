package org.sweetiebelle.aat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class Decrypt implements Runnable {

	@Override
	public void run() {
		File directory = new File("../PWAAT_Data/StreamingAssets/Sound/bgm");
		Optional<Stream<Path>> oFiles = getFiles(directory);
		if (!oFiles.isPresent()) { // Already logged exception. Just die quietly now.
			System.out.println("Got an error, dying quietly...");
			return;
		}
		Stream<Path> files = oFiles.get();
		files.forEach(new EncryptAction());
	}

	private Optional<Stream<Path>> getFiles(File directory) {
		try {
			verifyDirectory(directory);
			return Optional.ofNullable(Files.list(directory.toPath()));
		} catch (Throwable e) {
			e.printStackTrace(System.err);
		}
		return Optional.empty();
	}

	private void verifyDirectory(File directory) throws IllegalArgumentException {
		if (!directory.exists() || !directory.canRead() || !directory.canWrite() || !directory.isDirectory())
			throw new IllegalArgumentException();
	}
}
