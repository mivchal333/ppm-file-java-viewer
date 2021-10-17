package file.validator;

class EmptyLineFilter extends LineFilterChain {
    @Override
    public String filter(String line) {
        if (line == null || line.isBlank()) return "";
        return doFilter(line);
    }
}
