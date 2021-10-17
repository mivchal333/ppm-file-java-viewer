package file.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PpmImage {
    private Version version;
    private Integer width;
    private Integer height;
    private int colorMax;
    private Map<Integer, Pixel> pixels;


    public PpmImage(Version version, Integer width, Integer height, int colorMax) {
        this.version = version;
        this.width = width;
        this.height = height;
        this.colorMax = colorMax;
        this.pixels = new HashMap<>();
    }

    public void addPixel(Pixel pixel) {
        this.pixels.put(this.pixels.size(), pixel);
    }
}