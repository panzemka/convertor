# File Converter

A command-line utility for converting files between JSON, XML, and CSV formats.

## Features

- Convert between JSON, XML, and CSV formats (all 6 directions supported)
- Flat data structure support
- Clean error messages
- Simple CLI interface

## Requirements

- Java 21 or higher
- Maven 3.6+

## Building

```bash
./mvnw clean package
```

On Windows:
```bash
mvnw.cmd clean package
```
## Usage

```bash
java -jar target/file-converter.jar --input <input-file> --output <output-file>
```

### Examples

**CSV to JSON:**
```bash
java -jar target/file-converter-1.0.0-jar-with-dependencies.jar -i examples/username.csv -o output.json
```

**CSV to XML:**
```bash
java -jar target/file-converter-1.0.0-jar-with-dependencies.jar --input examples/username.csv --output output.xml
```

**JSON to CSV:**
```bash
java -jar target/file-converter-1.0.0-jar-with-dependencies.jar -i examples/username.json -o output.csv
```

**JSON to XML:**
```bash
java -jar target/file-converter-1.0.0-jar-with-dependencies.jar -i examples/username.json -o output.xml
```

**XML to JSON:**
```bash
java -jar target/file-converter-1.0.0-jar-with-dependencies.jar -i examples/username.xml -o output.json
```

**XML to CSV:**
```bash
java -jar target/file-converter-1.0.0-jar-with-dependencies.jar -i examples/username.xml -o output.csv
```

## Running Tests

```bash
./mvnw test
```

On Windows:
```bash
mvn test 
```