package com.wfy.server.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		final Path path = new File( "D:/adress.txt" ).toPath();
		try( Stream <String> lines = Files.lines( path, StandardCharsets.UTF_8 ) ) {
		    lines.onClose( () -> System.out.println("Done!") ).forEach( System.out::println );
		}
	}

}
