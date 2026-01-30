package converter.cli;

import java.util.HashMap;
import java.util.Map;

public class ArgsParser {
    private final Map<String, String> arguments = new HashMap<>();
    private final Map<String, String> shortToLong = Map.of(
        "i", "input",
        "o", "output",
        "h", "help"
    );
    
    public ArgsParser(String[] args) {
        parseArgs(args);
    }
    
    private void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            
            if (arg.startsWith("--")) {
                String key = arg.substring(2);
                if (key.isEmpty()) {
                    throw new IllegalArgumentException("Empty argument name");
                }
                
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    arguments.put(key, args[i + 1]);
                    i++;
                } else {
                    arguments.put(key, "");
                }
            } else if (arg.startsWith("-")) {
                String shortKey = arg.substring(1);
                if (shortKey.isEmpty()) {
                    throw new IllegalArgumentException("Empty short option");
                }
                
                String longKey = shortToLong.get(shortKey);
                
                if (longKey != null) {
                    if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        arguments.put(longKey, args[i + 1]);
                        i++;
                    } else {
                        arguments.put(longKey, "");
                    }
                } else {
                    throw new IllegalArgumentException("Unknown option: " + arg);
                }
            } else {
                // Для простих аргументів без префікса
                throw new IllegalArgumentException("Unexpected argument: " + arg);
            }
        }
    }
    
    public String getInput() {
        String input = arguments.get("input");
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Input file is required. Use --input <file> or -i <file>"
            );
        }
        return input.trim();
    }
    
    public String getOutput() {
        String output = arguments.get("output");
        if (output == null || output.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Output file is required. Use --output <file> or -o <file>"
            );
        }
        return output.trim();
    }
    
    public boolean hasHelp() {
        return arguments.containsKey("help");
    }
    
    public void printHelp() {
        System.out.println("FILE CONVERTER CLI");
        System.out.println("Convert files between JSON, XML, and CSV formats");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  java -jar file-converter.jar --input <file> --output <file>");
        System.out.println("  java -jar file-converter.jar -i <file> -o <file>");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  -i, --input    Path to input file (required)");
        System.out.println("  -o, --output   Path to output file (required)");
        System.out.println("  -h, --help     Show this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java -jar converter.jar -i data.json -o data.xml");
        System.out.println("  java -jar converter.jar --input data.csv --output data.json");
        System.out.println("  java -jar converter.jar -i users.xml -o users.csv");
    }
}