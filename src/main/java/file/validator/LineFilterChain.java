package file.validator;

abstract class LineFilterChain {
    private LineFilterChain next;

    public abstract String filter(String line);


    protected String doFilter(String line) {
        if (next == null) {
            return "";
        }
        return next.filter(line);
    }

    public LineFilterChain add(LineFilterChain filter) {
        this.next = filter;
        return this.next;
    }
}
