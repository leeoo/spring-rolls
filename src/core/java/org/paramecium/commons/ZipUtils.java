package org.paramecium.commons;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 压缩文件工具 <br>
 * 代 码 作 者:曹阳(CaoYang) <br>
 * 开 发 日 期:2012-1-11上午10:26:25 <br>
 * 项 目 信 息:paramecium:org.paramecium.commons.ZipUtils.java
 */
public abstract class ZipUtils {

	private final static Log logger = LoggerFactory.getLogger();

	/**
	 * 把一个文件夹下的所有文件压缩
	 * 例如:zip("c:\\zip\\hello.zip", "c:\\file")
	 * @param zipFilePath 压缩后，zip的存放路径和名称
	 * @param filePath 文件目录名
	 */
	public static void zip(String zipFilePath, String filePath) {
		File path = new File(filePath.concat("\\"));
		File[] files = path.listFiles();
		zip(zipFilePath, files);
	}

	/**
	 * 给出指定文件名称进行压缩
	 * 例如:zip("c:\\zip\\hello.zip", "c:\\file\\a.txt","c:\\file\\b.txt"...)
	 * @param zipFilePath 压缩后，zip的存放路径和名称
	 * @param fileNames 所有文件名称
	 */
	public static void zip(String zipFilePath, String... fileNames) {
		File files[] = new File[fileNames.length];
		for (int i = 0; i < fileNames.length; i++) {
			files[i] = new File(fileNames[i]);
		}
		zip(zipFilePath, files);
	}

	/**
	 * 根据文件File对象压缩
	 * 例如:zip("c:\\zip\\hello.zip", file1,file2...)
	 * @param zipFilePath 压缩后，zip的存放路径和名称
	 * @param files 所有文件实例
	 */
	public static void zip(String zipFilePath, File... files) {
		BufferedInputStream origin = null;
		FileOutputStream dest = null;
		File zipM = new File(zipFilePath.substring(0, zipFilePath.lastIndexOf('\\')));
		zipM.mkdirs();
		try {
			dest = new FileOutputStream(zipFilePath);
		} catch (FileNotFoundException e) {
			logger.error(e);
		}
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		byte data[] = new byte[2048];
		for (int i = 0; i < files.length; i++) {
			FileInputStream fi = null;
			try {
				fi = new FileInputStream(files[i]);
			} catch (FileNotFoundException e) {
				logger.error(e);
			}
			origin = new BufferedInputStream(fi, 2048);
			ZipEntry entry = new ZipEntry(files[i].getName());
			try {
				out.putNextEntry(entry);
			} catch (IOException e) {
				logger.error(e);
			}
			int count = 0;
			try {
				while ((count = origin.read(data, 0, 2048)) != -1) {
					out.write(data, 0, count);
				}
			} catch (IOException e) {
				logger.error(e);
			}
			try {
				if (origin != null) {
					origin.close();
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}
		try {
			if (out != null) {
				out.close();
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

	/**
	 * 给出压缩文件，解压到指定文件路径
	 * 例如:zip("c:\\zip\\hello.zip","c:\\unzip")
	 * @param zipFilePath 压缩后，zip的存放路径和名称
	 * @param unZipPath 解压路径
	 */
	public static void unZip(String zipFilePath, String unZipPath) {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipFilePath);
		} catch (IOException e) {
			logger.error(e);
		}
		Enumeration<?> emu = zipFile.entries();
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到.
			String unZipFile = unZipPath.concat("\\").concat(entry.getName());
			if (entry.isDirectory()) {
				new File(unZipFile).mkdirs();
				continue;
			}
			BufferedInputStream bis = null;
			try {
				bis = new BufferedInputStream(zipFile.getInputStream(entry));
			} catch (IOException e) {
				logger.error(e);
			}
			File file = new File(unZipFile);
			// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
			// 而这个文件所在的目录还没有出现过，所以要建出目录来。
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				parent.mkdirs();
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				logger.error(e);
			}
			BufferedOutputStream bos = new BufferedOutputStream(fos, 2048);
			int count;
			byte data[] = new byte[2048];
			try {
				while ((count = bis.read(data, 0, 2048)) != -1) {
					bos.write(data, 0, count);
				}
			} catch (IOException e) {
				logger.error(e);
			}
			try {
				if (bos != null) {
					bos.flush();
				}
			} catch (IOException e) {
				logger.error(e);
			}
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				logger.error(e);
			}
			try {
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}
		try {
			if (zipFile != null) {
				zipFile.close();
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}

}
