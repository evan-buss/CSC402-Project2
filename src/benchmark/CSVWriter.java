package benchmark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// This class outputs benchmark results to a CSV file.
class CSVWriter {
  private PrintWriter writer;

  CSVWriter(String outputFile) throws IOException {
    File file = new File(outputFile);
    writer = new PrintWriter(new FileWriter(file, true));
  }

  void writeHead(String... columns) {
    StringBuilder sb = new StringBuilder();
    for (String str : columns) {
      sb.append(str);
      sb.append(",");
    }
    writer.println(sb.toString());
  }

  void close() {
    writer.close();
  }
}
