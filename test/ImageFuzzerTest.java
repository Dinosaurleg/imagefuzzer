import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageFuzzerTest {
    public static void main(String[] args) throws Exception {
        var image = new BufferedImage(4, 1, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, 0x80ff0000);
        image.setRGB(1, 0, 0xff808080);
        image.setRGB(2, 0, 0xff0000ff);
        image.setRGB(3, 0, 0x80ff0000);

        if (ImageFuzzer.colorImage(image, 0, 0) != image) {
            throw new AssertionError("Expected the original image to be modified");
        }

        int red = image.getRGB(0, 0);
        int blue = image.getRGB(2, 0);
        if ((red >>> 24) != 0x80 || image.getRGB(3, 0) != red) {
            throw new AssertionError("Identical colors or transparency were not preserved");
        }
        if (image.getRGB(1, 0) != 0xff808080 || red == 0x80ff0000) {
            throw new AssertionError("Grayscale changed or saturated color did not change");
        }

        float redHue = Color.RGBtoHSB((red >> 16) & 0xff, (red >> 8) & 0xff, red & 0xff, null)[0];
        float blueHue = Color.RGBtoHSB((blue >> 16) & 0xff, (blue >> 8) & 0xff, blue & 0xff, null)[0];
        float hueDifference = (blueHue - redHue + 1f) % 1f;
        if (Math.abs(hueDifference - 2f / 3f) > 0.005f) {
            throw new AssertionError("Pixels did not receive the same hue shift");
        }
    }
}
