package converter;

import converter.cli.ArgsParser;

public class Main {
    public static void main(String[] args) {
        try {
            ArgsParser parser = new ArgsParser(args);

            if (parser.hasHelp()) {
                parser.printHelp();
                return;
            }

            String input = parser.getInput();
            String output = parser.getOutput();

            Converter converter = new Converter();
            converter.convert(input, output);

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Use --help for usage information");
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            System.exit(1);
        }
    }
}