package file.validator;

class TrimLineFilter extends LineFilterChain {
    @Override
    public String filter(String line) {
        return line.trim();
    }
}
