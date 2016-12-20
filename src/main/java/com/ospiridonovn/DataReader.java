package com.ospiridonovn;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author oleg (20.12.16).
 */
public class DataReader {
    public void read() {
        try {
            File imagesFile = new File("mnist/train-images.idx3-ubyte");
            File labelsFile = new File("mnist/train-labels.idx1-ubyte");

            FileInputStream imagesInputStream = new FileInputStream(imagesFile);
            FileInputStream labelsInputStream = new FileInputStream(labelsFile);
            int[] hashMap = new int[10];
            String outputPath = "mnist/";


            int s;
            int magicNumberImages = (imagesInputStream.read() << 24) | (imagesInputStream.read() << 16) | (imagesInputStream.read() << 8) | (imagesInputStream.read());
            int numberOfImages = (imagesInputStream.read() << 24) | (imagesInputStream.read() << 16) | (imagesInputStream.read() << 8) | (imagesInputStream.read());
            int numberOfRows  = (imagesInputStream.read() << 24) | (imagesInputStream.read() << 16) | (imagesInputStream.read() << 8) | (imagesInputStream.read());
            int numberOfColumns = (imagesInputStream.read() << 24) | (imagesInputStream.read() << 16) | (imagesInputStream.read() << 8) | (imagesInputStream.read());

            System.out.println(magicNumberImages);
            System.out.println(numberOfImages);
            System.out.println(numberOfRows);
            System.out.println(numberOfColumns);

            int magicNumberLabels = (labelsInputStream.read() << 24) | (labelsInputStream.read() << 16) | (labelsInputStream.read() << 8) | (labelsInputStream.read());
            int numberOfLabels = (labelsInputStream.read() << 24) | (labelsInputStream.read() << 16) | (labelsInputStream.read() << 8) | (labelsInputStream.read());

            System.out.println("\n");
            System.out.println(magicNumberLabels);
            System.out.println(numberOfLabels);

            BufferedImage image = new BufferedImage(numberOfColumns, numberOfRows, BufferedImage.TYPE_INT_ARGB);
            int numberOfPixels = numberOfRows * numberOfColumns;
            int[] imgPixels = new int[numberOfPixels];

            for(int i = 0; i < numberOfImages; i++) {

                if(i % 100 == 0) {System.out.println("Number of images extracted: " + i);}

                for(int p = 0; p < numberOfPixels; p++) {
                    int gray = 255 - imagesInputStream.read();
                    imgPixels[p] = 0xFF000000 | (gray<<16) | (gray<<8) | gray;
                }

                image.setRGB(0, 0, numberOfColumns, numberOfRows, imgPixels, 0, numberOfColumns);

                int label = labelsInputStream.read();

                hashMap[label]++;
                File outputFile = new File(outputPath + label + "_0" + hashMap[label] + ".png");
                outputFile.createNewFile();

                ImageIO.write(image, "png", outputFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
