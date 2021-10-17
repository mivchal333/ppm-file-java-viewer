package file.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Pixel {
    private final int r;
    private final int g;
    private final int b;


    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
