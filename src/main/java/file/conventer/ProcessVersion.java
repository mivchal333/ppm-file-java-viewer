package file.conventer;

import file.model.Version;

public class ProcessVersion implements ProcessConvert<Version> {
    public Version convert(String data) {
        try {
            return Version.valueOf(data);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Cannot convert version!");
    }
}