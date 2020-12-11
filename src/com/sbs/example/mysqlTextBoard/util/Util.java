package com.sbs.example.mysqlTextBoard.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Util {

	public static void mkdirs(String path) {
		File dir = new File(path);

		if (dir.exists() == false) {
			dir.mkdirs();
		}
	}

	public static void fileWriter(String filePath, String body) {
		File file = new File(filePath);

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(body);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void rmdir(File dirToBeDel) {
		File[] allContents = dirToBeDel.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				if (file.isDirectory()) {
					rmdir(file);
				} else {
					file.delete();
				}
			}
		}
		dirToBeDel.delete();
	}
}
