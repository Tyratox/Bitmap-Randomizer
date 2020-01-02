package ch.tyratox.misc.pic.random;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class BitmapRandomizer {
	
	public static void main(String[] args){
		new BitmapRandomizer();
	}
	
	public BitmapRandomizer(){
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open the Bitmap");
		if(fc.showDialog(null, "Open and randomize") == JFileChooser.APPROVE_OPTION){
			File f = fc.getSelectedFile();
			for(int d = 0;d<20;d++){
				randomizeImage(f);
			}
			JOptionPane.showMessageDialog(null, "Done :D");
			System.exit(0);
		}
	}
	
	private void randomizeImage(File f){
		try {
			Random random = new Random();
			BufferedImage image = ImageIO.read(f);
			BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			for (int xPixel = 0; xPixel < image.getWidth(); xPixel++){
	            for (int yPixel = 0; yPixel < image.getHeight(); yPixel++){
	            	int x = random.nextInt(5000);
	            	boolean change = false;
	            	int color = image.getRGB(xPixel, yPixel);
	                newImage.setRGB(xPixel, yPixel, color);
	            	if(x==1){
	            		change = true;
	            	}else{
	            		continue;
	            	}
	            	if(change){
	            		int chunk = random.nextInt((image.getWidth()>=image.getHeight()?image.getWidth():image.getHeight()) / 4);
	            		
	            		int randomX = random.nextInt(image.getWidth());
						int randomY = random.nextInt(image.getHeight());
						
						int shifts = random.nextInt(32);
						int r = random.nextInt(3);
						
						//System.out.println("Moving chunk x:" + xPixel + " y: " + yPixel + " to x: " + randomX + " y: " + randomY + ", size: " + chunk);
	            		for (int i = 0; i < chunk; i++) {
							for (int j = 0; j < chunk; j++) {
								int color_ = 0;
								if(xPixel + i >= newImage.getWidth() || yPixel + j >= newImage.getHeight()){
									if(xPixel - i <= newImage.getWidth() || yPixel - j <= newImage.getHeight()){
										
									}else{
										//TODO
										color_ = image.getRGB(xPixel - i, yPixel - j);
									}
								}else{
									//TODO
									color_ = image.getRGB(xPixel + i, yPixel + j);
								}
								if(color_ != 0){
									if(r==1){
										color_ = color_ >> shifts;
									}else if(r==2){
										color_ = color_ << shifts;
									}
									if(randomX + i >= newImage.getWidth() || randomY + j >= newImage.getHeight()){
										if(randomY - i <= newImage.getWidth() || randomY <= newImage.getHeight()){
											
										}else{
											newImage.setRGB(randomX - i, randomY - j, color_);
										}
									}else{
										newImage.setRGB(randomX + i, randomY + j, color_);
									}
								}
							}
						}
	            	}
	            }
	        }
			int count = 0;
			while(new File("saved" + count + ".png").exists()){
				count++;
			}
			File outputfile = new File("saved"+count+".png");
			ImageIO.write(newImage, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
