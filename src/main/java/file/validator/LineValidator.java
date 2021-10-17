package file.validator;

public class LineValidator {
    private final LineFilterChain filterChain;

    public LineValidator() {
        EmptyLineFilter filter = new EmptyLineFilter();
        filter
                .add(new CommentLineFilter())
                .add(new TrimLineFilter())
                .add(new EmptyLineFilter());

        this.filterChain = filter;
    }

    public String validate(String line) {
        return filterChain.filter(line);
    }
}
