package unittests;

import org.junit.Test;
import renderer.ImageWriter;

import java.awt.*;

/**
 * Testing ImageWriter Class
 */
public class ImageWriterTest {

    /**
     * Test method for ImageWriter
     * {@link ImageWriter#ImageWriter(String, double, double, int, int)}
     */
    @Test
    public void testWriteToImage() {

        final int HEIGHT = 1600;
        final int WIDTH = 1000;
        final int nX = 800;
        final int nY = 500;

        final int numOfColumns = 16;
        final int numOfRows = 10;

        ImageWriter imageWriter = new ImageWriter("test" , WIDTH , HEIGHT , nX , nY);

        for(int i = 0 ; i < 500 ; i++){
            for(int j = 0; j < 800 ; j++) {
                if(j % (nX/numOfColumns) == 0 || i % (nY/numOfRows) == 0)
                    imageWriter.writePixel(j, i, Color.red);
                else
                    imageWriter.writePixel(j, i, Color.blue);
            }
        }
        imageWriter.writeToImage();
    }
}
