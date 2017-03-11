import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

// This project gets an image from the user, (hard coded to get directly from the file directory right now), and 
// creates and a new image with regions of similiar colors being a uniform randomized color 
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

		image = colorImage(image, height, width);
	
		try{
			writeBufferedImage(image, f);
		}catch(IOException e){
			System.out.println("Error: " + e);
		}
	}

	// Reads the image from file
	public static BufferedImage getBufferedImage(BufferedImage image, File f, int height, int width) throws IOException{
			try{
				System.out.println("Trying to upload picture!");
				f = new File("/home/jae/Desktop/imageFuzzerPictures/pic2.jpg");
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

	// Writes the image
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

	// Colors the pixels of a new buffered image
	public static BufferedImage colorImage(BufferedImage image, int height, int width) throws IOException{
		height = (int)image.getHeight();
		width = (int)image.getWidth();
		int[][] pixelArray = new int[width][height];
		int[][] bImagePixelList = new int[width][height];
		
		int a, r, g, b = 0;


		System.out.println("hdalskdnlajsna");
		System.out.println(height);
		System.out.println(width);

		// Gets the pixel values for the image and puts it in an ArrayList of Integers so we can compare the pixel values
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				pixelArray[x][y] = image.getRGB(x,y);
			}
		}

		// Randomizes the first column of pixels of the newly colored image
		for(int wInitial = 0; wInitial < 1; wInitial++)
		{
			for(int hInitial = 0; hInitial < height; hInitial++)
				bImagePixelList[wInitial][hInitial] = setPixelValue(pixelRandomizer());
				// System.out.println("hInitial----------- " + hInitial);
				// System.out.println("INITIAL PIXELS----------------- " + bImagePixelList[wInitial][hInitial-1]);
		}

		// int hTest, wTest = 0;
		// for(wTest = 0; wTest < 1; wTest++){
		// 	for(hTest = 0; hTest < height; hTest++){
		// 		System.out.println("INITIAL PIXELS----------------- " + bImagePixelList[wTest][hTest]);		
		// 	}
		// }

		// Checks to see if the pixels are generally the same color, if so, makes it the same color as the ancestor, else it randomizes the color
		for(int w = 1; w < width; w++){
			for(int h = 0; h < height; h++){
				// If it is the top row
				if(h == 0){
					// If the pixel matches the pixel to the left and the pixel to left lower diagonal, set to the same
					if(comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h])) && 
						comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h+1]))){
						bImagePixelList[w][h] = bImagePixelList[w-1][h];
					} else {
						// Else randomize
						bImagePixelList[w][h] = setPixelValue(pixelRandomizer());
					}
					// If the pixel is on the bottom row
				} else if(h == height - 1){
					// If the pixel matches the pixel to the left and the pixel to the upper left diagonal, set to same
					if(comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h])) && 
						comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h-1]))){
						bImagePixelList[w][h] = bImagePixelList[w-1][h];
					} else {
						// Else randomize
						bImagePixelList[w][h] = setPixelValue(pixelRandomizer());
					}
				} else {
					// else if it is a "middle" pixel, and the pixel is the same as the left, lower left diagonal, and upper left diagonal, set to same 
					if(comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h])) && 
							comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h-1]))){

						bImagePixelList[w][h] = bImagePixelList[w-1][h];
					} else if(comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h])) && 
									comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h+1]))){

						bImagePixelList[w][h] = bImagePixelList[w-1][h];
					} else if(comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h])) && 
							comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w][h-1]))){

						bImagePixelList[w][h] = bImagePixelList[w-1][h];
					} else if(comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h-1])) && 
							comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h+1]))){

						bImagePixelList[w][h] = bImagePixelList[w-1][h-1];
					} else if(comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h-1])) && 
							comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w][h-1]))){

						bImagePixelList[w][h] = bImagePixelList[w-1][h-1];
					}else if(comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w-1][h+1])) && 
							comparePixels(getRGBValues(pixelArray[w][h]), getRGBValues(pixelArray[w][h-1]))){

						bImagePixelList[w][h] = bImagePixelList[w-1][h+1];
						// Have 
						// left AND top left 
						// AND 
						// left AND top  
						// OR
						// left AND bottom left
						// Need 
						// top left AND bottome left
						// OR
						// top left AND TOP
						// OR
						// Bottom Left AND TOP
					} else {
						// else randomize it
						bImagePixelList[w][h] = setPixelValue(pixelRandomizer());
					}
				}
			}
		}

		// int hTestTwo, wTestTwo = 0;
		// for(wTestTwo = 200; wTestTwo < width; wTestTwo++){
		// 	for(hTestTwo = 150; hTestTwo < height; hTestTwo++){
		// 		System.out.println("Middle PIXELS----------------- " + bImagePixelList[wTestTwo][hTestTwo]);
		// 	}
		// }

		// Sets the RGB values to the image
		for(int setWidth = 0; setWidth < width; setWidth++){
			for(int setHeight = 0; setHeight < height; setHeight++){
				image.setRGB(setWidth, setHeight, bImagePixelList[setWidth][setHeight]);
				// image.setRGB(setWidth, setHeight, setPixelValue(pixelRandomizer()));
				// System.out.println("PIXEL------------ " + bImagePixelList[85][130]);
			}
		}
		return image;

		// NEED TO CHECK EACH A, R, G, B VALUES AND NOT THE ACTUAL PIXEL INT
	}

	// Sets pseudorandom values in an arraylist to simulate random values for Alpha, Red, Blue, Green
	public static ArrayList<Integer> pixelRandomizer(){
		Random rand = new Random();
		ArrayList<Integer> pixelRandomized = new ArrayList<Integer>();

		for(int i = 1; i < 5; i++){
			pixelRandomized.add(rand.nextInt(256));
		}

		// for(int x = 0; x < 4; x++)
		// 	System.out.println("RANDOMIZED VALUES----------- " + pixelRandomized.get(x));

		return pixelRandomized;
	}

	// Gets the Alpha, Red, Blue, and Green values from the pixel value and turns them into readable ints from "signed"
	public static ArrayList<Integer> getRGBValues(int pixel){
		ArrayList<Integer> pixelList = new ArrayList<Integer>();

		pixelList.add((pixel>>24)&0xff);
		pixelList.add((pixel>>16)&0xff);
		pixelList.add((pixel>>8)&0xff);
		pixelList.add(pixel&0xff);

		return pixelList;
	}

	// Grabs the individual Alpha, Red, Blue, and Green Values and turns it back into the pixel value
	public static int setPixelValue(ArrayList<Integer> pixelList){
		int pixel = 0;

		int alpha = pixelList.get(0);
		int red = pixelList.get(1);
		int blue = pixelList.get(3);
		int green = pixelList.get(2);

		pixel = (alpha<<24) | (red<<16) | (green<<8) | blue;

		return pixel;
	}

	// Compares alpha, red, green, blue values to see if they are within 30 values of eachother
	public static boolean comparePixels(ArrayList<Integer> oldImageList, ArrayList<Integer> newImageList){
		if(((Math.abs(oldImageList.get(0) - newImageList.get(0)) <= 10)) && ((Math.abs(oldImageList.get(1) - newImageList.get(1)) <= 10)) &&
			((Math.abs(oldImageList.get(2) - newImageList.get(2)) <= 10)) && ((Math.abs(oldImageList.get(3) - newImageList.get(3)) <= 10))){
			return true;
		}

		return false;
	}

}
