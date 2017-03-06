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
		ArrayList pixelList<Integer> = new ArrayList<Integer>();

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

		getRegion(image, height, width);
	}

	public static BufferedImage getBufferedImage(BufferedImage image, File f, int height, int width) throws IOException{
			try{
				System.out.println("Trying to upload picture!");
				f = new File("/home/jae/Desktop/imageFuzzerPictures/pic.jpg");
				image = ImageIO.read(f);
				height = (int)image.getHeight();
				width = (int)image.getWidth();
				System.out.println(height);
				System.out.println(width);
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

	public static void getRegion(BufferedImage image, int height, int width, ArrayList<Integer> pixelList) throws IOException{
		int column;
		int row;
		height = (int)image.getHeight();
		width = (int)image.getWidth();
		int[][] pixelArray = new int[width][height];
		int a, r, g, b = 0;


		System.out.println("hdalskdnlajsna");
		System.out.println(height);
		System.out.println(width);
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				pixelArray[x][y] = image.getRGB(x,y);
			}
		}

		for(int w = 0; w < width; w++){
			row = w;
			for(int h = 0; h < height; h++){
				column = h;

				getRGBValues(getRGB(w, h), pixelList);
				a = pixelList(0);
				r = pixelList(1);
				g = pixelList(2);
				b = pixelList(3);
			}
		}
	}

	public static arrayList<int> getRGBValues(int pixel, pixelList){
		pixelList.add((pixel>>24)&0xff);
		pixelList.add((pixel>>16)&0xff);
		pixelList.add((pixel>>8)&0xff);
		pixelList.add(pixel&0xff);

		return pixelList;
	}

}
