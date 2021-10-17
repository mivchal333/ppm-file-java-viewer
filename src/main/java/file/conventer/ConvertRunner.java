package file.conventer;

import file.validator.LineValidator;

import java.util.Scanner;

public class ConvertRunner<T> {
    private final LineValidator lineValidator = new LineValidator();
    private final String COMMENT_REGEX_PATTERN = "[#(.*)]";

    public T run(ProcessConvert<T> convertResolver, Scanner sc) {
        boolean isFinished = false;
        T result = null;

        do {
            if (sc.hasNext(COMMENT_REGEX_PATTERN)) {
                sc.nextLine();
                continue;
            }

            String originalLine = sc.next();
            String line = lineValidator.validate(originalLine);

            if (!line.isEmpty()) {
                isFinished = true;
                result = convertResolver.convert(line);
            }
        } while (!isFinished);

        if (result == null) {
            throw new RuntimeException("Cannot resolve conversion");
        }

        return result;
    }
}