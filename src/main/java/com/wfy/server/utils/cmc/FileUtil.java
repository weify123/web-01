package com.wfy.server.utils.cmc;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileUtil {
	private static int bufferLen = 1024;

	public static void doZipCompress(File srcFile, File destFile) throws IOException {
		ZipArchiveOutputStream out = null;
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(srcFile), bufferLen);
			out = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(destFile), bufferLen));
			ZipArchiveEntry entry = new ZipArchiveEntry(srcFile.getName());
			entry.setSize(srcFile.length());
			out.putArchiveEntry(entry);
			IOUtils.copy(is, out);
			out.closeArchiveEntry();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(out);
		}
	}

	public static void doZipDecompress(File srcFile, File destDir) throws IOException {
		ZipArchiveInputStream is = null;
		try {
			is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(srcFile), bufferLen));
			ZipArchiveEntry entry = null;
			while ((entry = is.getNextZipEntry()) != null) {
				if (entry.isDirectory()) {
					File directory = new File(destDir, entry.getName());
					directory.mkdirs();
				} else {
					OutputStream os = null;
					try {
						os = new BufferedOutputStream(new FileOutputStream(new File(destDir, entry.getName())), bufferLen);
						IOUtils.copy(is, os);
					} finally {
						IOUtils.closeQuietly(os);
					}
				}
			}
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	public static void main(String[] args) {
		try {
			File srcFile = new File("D:/Desktop/ons.log");
			File destFile = new File("D:/Desktop/ons.zip");
			doZipCompress(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
