package file.conventer;

public class ProcessNumber implements ProcessConvert<Integer> {
    @Override
    public Integer convert(String data) {
        String[] split = data.split("/[-0-9]+/");

        return Integer.valueOf(split[0]);
    }
}
