import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

public class ImageFuzzer {
    public static void main(String[] args) throws IOException {
        BufferedImage image = null;
        File file = null;
        int height = 0;
        int width = 0;

        try {
            image = getBufferedImage(image, file, height, width);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        image = colorImage(image, height, width);

        try {
            writeBufferedImage(image, file);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static BufferedImage getBufferedImage(BufferedImage image, File file, int height, int width)
            throws IOException {
        try {
            System.out.println("Trying to upload picture!");
            file = new File("../bananas-1354785_1920-1024x683.png");
            image = ImageIO.read(file);
            height = image.getHeight();
            width = image.getWidth();
            System.out.println(height);
            System.out.println(width);
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(file);
            System.out.println("File finished uploading!");
        } catch (IOException e) {
            System.out.println("Before error-------");
            System.out.println("Error: " + e);
            System.out.println("After error----------");
        }
        return image;
    }

    public static void writeBufferedImage(BufferedImage image, File file) throws IOException {
        try {
            if (image != null) {
                System.out.println("Image is not null");
            } else {
                System.out.println("Image is null");
            }
            System.out.println("Updating your picture!");
            file = new File("../output.jpg");
            System.out.println("After new file++++++++++++");
            ImageIO.write(image, "jpg", file);
            System.out.println("After image write+++++++++++");
            System.out.println("Picture updated!");
        } catch (IOException e) {
            System.out.println("Before error +++++++++++");
            System.out.println("Error: " + e);
            System.out.println("After error +++++++++++++++");
        }
    }

    public static BufferedImage colorImage(BufferedImage image, int height, int width) throws IOException {
        height = image.getHeight();
        width = image.getWidth();
        var pixels = new int[width][height];
        var recolored = new int[width][height];

        System.out.println("hdalskdnlajsna");
        System.out.println(height);
        System.out.println(width);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = image.getRGB(x, y);
            }
        }

        recolored[0][0] = setPixelValue(pixelRandomizer());
        for (int y = 1; y < height; y++) {
            if (matches(pixels, 0, y, 0, y - 1)) {
                recolored[0][y] = recolored[0][y - 1];
            } else {
                recolored[0][y] = setPixelValue(pixelRandomizer());
            }
        }

        for (int x = 1; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y == 0) {
                    if (matches(pixels, x, y, x - 1, y)
                            && matches(pixels, x, y, x - 1, y + 1)) {
                        recolored[x][y] = recolored[x - 1][y];
                    } else {
                        recolored[x][y] = setPixelValue(pixelRandomizer());
                    }
                } else if (y == height - 1) {
                    if (matches(pixels, x, y, x - 1, y)
                            && matches(pixels, x, y, x - 1, y - 1)) {
                        recolored[x][y] = recolored[x - 1][y];
                    } else {
                        recolored[x][y] = setPixelValue(pixelRandomizer());
                    }
                } else {
                    boolean left = matches(pixels, x, y, x - 1, y);
                    boolean upperLeft = matches(pixels, x, y, x - 1, y - 1);
                    boolean lowerLeft = matches(pixels, x, y, x - 1, y + 1);
                    boolean above = matches(pixels, x, y, x, y - 1);

                    if (left && (upperLeft || lowerLeft || above)) {
                        recolored[x][y] = recolored[x - 1][y];
                    } else if (upperLeft && lowerLeft) {
                        recolored[x][y] = recolored[x - 1][y - 1];
                    } else if ((upperLeft || lowerLeft) && above) {
                        recolored[x][y] = recolored[x][y - 1];
                    } else {
                        recolored[x][y] = setPixelValue(pixelRandomizer());
                    }
                }
            }
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, recolored[x][y]);
            }
        }
        return image;
    }

    private static boolean matches(int[][] pixels, int x, int y, int otherX, int otherY) {
        return comparePixels(getRGBValues(pixels[x][y]), getRGBValues(pixels[otherX][otherY]));
    }

    public static ArrayList<Integer> pixelRandomizer() {
        var random = new Random();
        var pixel = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            pixel.add(random.nextInt(256));
        }
        return pixel;
    }

    public static ArrayList<Integer> getRGBValues(int pixel) {
        var values = new ArrayList<Integer>();
        values.add((pixel >> 24) & 0xff);
        values.add((pixel >> 16) & 0xff);
        values.add((pixel >> 8) & 0xff);
        values.add(pixel & 0xff);
        return values;
    }

    public static int setPixelValue(ArrayList<Integer> pixelList) {
        int alpha = pixelList.get(0);
        int red = pixelList.get(1);
        int blue = pixelList.get(3);
        int green = pixelList.get(2);
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public static boolean comparePixels(ArrayList<Integer> oldImageList, ArrayList<Integer> newImageList) {
        return Math.abs(oldImageList.get(0) - newImageList.get(0)) <= 255
                && Math.abs(oldImageList.get(1) - newImageList.get(1)) <= 10
                && Math.abs(oldImageList.get(2) - newImageList.get(2)) <= 10
                && Math.abs(oldImageList.get(3) - newImageList.get(3)) <= 10;
    }
}
