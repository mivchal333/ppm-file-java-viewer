package file.conventer;

import file.conventer.pixel.PixelRow;
import file.model.Pixel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProcessPixelRow implements ProcessConvert<PixelRow> {
    private int PIXEL_COMPONENT_COUNT = 3;

    @Override
    public PixelRow convert(String data) {

        String[] values = data.split(" ");

        List<Integer> valueList = Arrays.asList(values)
                .stream()
                .filter(s -> !s.isBlank())
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Pixel> pixels = new ArrayList<>();

        for (int i = 0; i < valueList.size(); i += PIXEL_COMPONENT_COUNT) {
            Pixel pixel = new Pixel(valueList.get(i), valueList.get(i + 1), valueList.get(i + 2));
            pixels.add(pixel);
        }
        return PixelRow.of(pixels);
    }
}
