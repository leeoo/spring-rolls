package com.exam;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		saveStringToPic("温家宝：“最近，日本当局导演的一场钓鱼岛的闹剧，全世界都应该知道钓鱼岛是中国神圣的固有领土，我们要维护国家的主权和领土完整，在这个问题上我们要采取有力措施。在涉及国家主权领土完整，这些重大问题上要保持铮铮铁骨，毫不退让。一个民族没有比尊严和自主、独立更为重要的了。”","d://ddd.jpg");

	}

	public static boolean saveStringToPic(String content, String filePath) {
		boolean rs;
		int width = 1024; // 300;
		int height = 600;
		int lineLength = 47;// 23;
		int lineLength2 = 47;// 24;
		String content1 = content;
		String content2 = "";
		String content3 = "";
		int cnLineLength = lineLength;
		for (int i = 0; i < content.length(); i++) {
			char ca = content.charAt(i);
			if ((ca < '0' || ca > '9') && ca != '-' && ca != ':' && ca != '('
					&& ca != ')' && ca != '.' && ca != ',' && ca != ' ') {
				cnLineLength = cnLineLength - 1;
			}
			if (i >= cnLineLength)
				break;
		}
		if (content.length() > cnLineLength) {
			content1 = content.substring(0, cnLineLength);
			content2 = content.substring(cnLineLength);
			String line2 = content2;
			int cnLineLength2 = lineLength2;
			for (int i = 0; i < line2.length(); i++) {
				char ca = line2.charAt(i);
				if ((ca < '0' || ca > '9') && ca != '-' && ca != ':'
						&& ca != '(' && ca != ')' && ca != '.' && ca != ','
						&& ca != ' ') {
					cnLineLength2 = cnLineLength2 - 1;
				}
				if (i >= cnLineLength2)
					break;
			}
			if (line2.length() > cnLineLength2) {
				content2 = line2.substring(0, cnLineLength2);
				content3 = line2.substring(cnLineLength2);
			}
		}
		BufferedImage bi = null;
		Graphics2D g2 = null;
		OutputStream outPutStream = null;
		try {
			bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			g2 = (Graphics2D) bi.getGraphics();
			// g2.setBackground(Color.white);
			Color bg = new Color(255, 255, 255);
			g2.setColor(bg);
			g2.fillRect(0, 0, width, height);
			g2.setColor(Color.black);
			Font font = new Font("宋体", Font.BOLD, 20);
			g2.setFont(font);
			g2.drawString(content1, 10, 200);
			g2.drawString(content2, 10, 300);
			g2.drawString(content3, 10, 400);
			outPutStream = new FileOutputStream(filePath);
			JPEGImageEncoder encoder = JPEGCodec
					.createJPEGEncoder(outPutStream);
			encoder.encode(bi);
			rs = true;
		} catch (Exception e) {
			e.printStackTrace();
			rs = false;
		} finally {
			if (g2 != null) {
				g2.dispose();
			}
			if (outPutStream != null) {
				try {
					outPutStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return rs;
	}

}
