package file.conventer.pixel;

import file.model.Pixel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PixelRow {
    @Getter
    private final List<Pixel> row;

    public static PixelRow of(List<Pixel> pixels) {
        return new PixelRow(pixels);
    }
}
