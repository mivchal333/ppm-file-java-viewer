package file;

import file.conventer.*;
import file.conventer.colors.ColorScaler;
import file.conventer.pixel.PixelRow;
import file.model.Pixel;
import file.model.PpmImage;
import file.model.Version;

import java.util.Scanner;

public class Ppm3FileReader {

    public PpmImage convertImage(Scanner sc) {
        if (!sc.hasNext()) {
            throw new RuntimeException("Cannot open file!");
        }

        Version version = new ConvertRunner<Version>().run(new ProcessVersion(), sc);
        Integer width = new ConvertRunner<Integer>().run(new ProcessNumber(), sc);
        Integer height = new ConvertRunner<Integer>().run(new ProcessNumber(), sc);
        Integer colorBound = new ConvertRunner<Integer>().run(new ProcessNumber(), sc);

        PpmImage ppmImage = new PpmImage(version, width, height, colorBound);

        while (sc.hasNext()) {

            Integer pixelRValue = new ConvertRunner<Integer>().run(new ProcessNumber(), sc);
            Integer pixelGValue = new ConvertRunner<Integer>().run(new ProcessNumber(), sc);
            Integer pixelBValue = new ConvertRunner<Integer>().run(new ProcessNumber(), sc);

            Pixel pixel = ColorScaler.scaleColors(new Pixel(pixelRValue, pixelGValue, pixelBValue), colorBound);
            ppmImage.addPixel(pixel);
        }
        return ppmImage;
    }
}