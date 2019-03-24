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

  // Write a new line to the file
  void writeLine(String... columns) {
    StringBuilder sb = new StringBuilder();
    for (String str : columns) {
      sb.append(str);
      if (!str.equals(columns[columns.length-1])) {
        sb.append(",");
      }
    }
    writer.println(sb.toString());
  }

  // Flush the stream. This is a great way to improve write performance. By
  // writing in batches instead of after each line.
  void flush() {
    writer.flush();
  }

  // Close the file writer. This also flushes the stream
  void close() {
    writer.close();
  }
}
