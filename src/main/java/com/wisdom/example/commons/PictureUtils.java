package com.wisdom.example.commons;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.wisdom.core.utils.DateUtils;
/**
 * 
 * <b>业务说明</b>:<br>
 * 图片工具类
 *<br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-4-27<br>
 * <b>建立时间</b>: 下午03:22:51<br>
 * <b>项目名称</b>: wedding<br>
 * <b>包及类名</b>: com.wisdom.wedding.commonsPictureUtils.java<br>
 */
public class PictureUtils {

	/**
	 * 生成指定分辨率图片(GIF是商业格式，支持的不好)
	 * @param upload文件流来源
	 * @param newPath目标文件名
	 * @param widthdist生成宽度
	 * @param heightdist生成高度
	 */
	private static void createNewSmallSizeImage(File upload, String newPath,int widthdist, int heightdist) {
		try {
			Image src = ImageIO.read(upload);
			BufferedImage tag = new BufferedImage((int) widthdist,(int) heightdist, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,Image.SCALE_DEFAULT), 0, 0, null);
			init(newPath,tag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 为图片添加水印
	 * @param upload文件流来源
	 * @param newPath目标文件名
	 * @param markContent水印文字
	 * @param markColor水印颜色
	 * @param markSize水印文字大小
	 */
	private static void createNewRemarkImage(File upload, String newPath,String markContent, Color markColor,int markSize) {
		try {
			Image src2 = ImageIO.read(upload);
			int width = src2.getWidth(null);
			int height = src2.getHeight(null);
			BufferedImage tag2 = new BufferedImage((int) width,(int) height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = tag2.createGraphics();
			g.setColor(markColor);
			g.setBackground(Color.white);
			g.drawImage(src2, 0, 0, null);
			g.setFont(new Font("黑体", 36, markSize));   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f)); 
			g.drawString(markContent, width / 2-width / 3, height-60);
			g.dispose();
			init(newPath,tag2);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void init(String newPath,BufferedImage tag)throws Exception{
		FileOutputStream out = new FileOutputStream(newPath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(tag);
		out.close();
	}
	/**
	 * 生成指定大小图片并加以水印
	 * @param upload文件流来源
	 * @param newPath目标文件名
	 * @param markContent水印文字
	 * @param markColor水印颜色
	 * @param markSize水印文字大小
	 * @param widthdist生成宽度
	 * @param heightdist生成高度
	 */
	public synchronized static void createNewSmallSizeAndRemarkImage(File upload,String newPath,
			String markContent, Color markColor,int markSize,int widthdist, int heightdist) {
		createNewSmallSizeImage(upload,newPath,widthdist,heightdist);
		File file=new File(newPath);
		createNewRemarkImage(file,newPath,markContent,markColor,markSize);
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.getCurrentDateTime());
		createNewSmallSizeAndRemarkImage(new File("c://"+"abc.jpg"),"c://"+"123.jpg","絕色圖片",Color.PINK,100,640,480);
		System.out.println(DateUtils.getCurrentDateTime());
	}
	
}
