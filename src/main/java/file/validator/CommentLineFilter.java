package file.validator;

class CommentLineFilter extends LineFilterChain {
    @Override
    public String filter(String line) {
        String toNext = line.contains("#")
                ? line.substring(0, line.indexOf("#"))
                : line;
        return doFilter(toNext);
    }
}
