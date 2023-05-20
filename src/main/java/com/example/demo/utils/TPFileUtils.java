package com.example.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import exception.InternalServerErrorException;

public class TPFileUtils {
	private TPFileUtils() {
	}

//	public static InputStream readFile(Path filePath) {
//		try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ)) {
//			// Map the file to memory
//			MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
//			return new ByteBufferInputStream(mappedByteBuffer);
//		} catch (IOException e) {
//			throw new InternalServerErrorException(e);
//		}
//	}
}
