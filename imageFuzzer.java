import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class imageFuzzer{
	public static void main(String[] args) throws IOException{
		BufferedImage image = null;
		File f = null;
		int height = 0;
		int width = 0;

		try{
			image = getBufferedImage(image, f, height, width);
		}catch(IOException e){
			System.out.println("Error: " + e);
		}

		try{
			writeBufferedImage(image, f);
		}catch(IOException e){
			System.out.println("Error: " + e);
		}
	}

	public static BufferedImage getBufferedImage(BufferedImage image, File f, int height, int width) throws IOException{
			try{
				System.out.println("Trying to upload picture!");
				f = new File("/home/jae/Desktop/imageFuzzerPictures/pic.jpg");
				image = ImageIO.read(f);
				height = (int)image.getHeight();
				width = (int)image.getWidth();
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				image = ImageIO.read(f);
				System.out.println("File finished uploading!");
			}catch(IOException e){
				System.out.println("Before error-------");
				System.out.println("Error: " + e);
				System.out.println("After error----------");
			}
		return image;
	}

	public static void writeBufferedImage(BufferedImage image, File f) throws IOException{
		try{
			if(image != null)
				System.out.println("Image is not null");
			else
				System.out.println("Image is null");
			System.out.println("Updating your picture!");
			f = new File("/home/jae/Desktop/imageFuzzerPictures/output.jpg");
			System.out.println("After new file++++++++++++");
			ImageIO.write(image, "jpg", f);
			System.out.println("After image write+++++++++++");
			System.out.println("Picture updated!");
		}catch(IOException e){
			System.out.println("Before error +++++++++++");
			System.out.println("Error: " + e);
			System.out.println("After error +++++++++++++++");
		}
	}

}
