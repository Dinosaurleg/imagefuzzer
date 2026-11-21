import java.awt.Color;
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
            file = new File("../adventure-time-1718205825928.jpg");
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
        float hueShift = new Random().nextFloat(1f / 12f, 11f / 12f);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                float[] hsb = Color.RGBtoHSB(
                        (pixel >> 16) & 0xff,
                        (pixel >> 8) & 0xff,
                        pixel & 0xff,
                        null);
                int shifted = Color.HSBtoRGB(
                        (hsb[0] + hueShift) % 1f, hsb[1], hsb[2]);
                image.setRGB(x, y, (pixel & 0xff000000) | (shifted & 0x00ffffff));
            }
        }
        return image;
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
