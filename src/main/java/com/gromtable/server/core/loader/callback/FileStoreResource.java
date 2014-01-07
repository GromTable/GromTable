package com.gromtable.server.core.loader.callback;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.gromtable.server.core.data.Rows;
import com.gromtable.server.core.environment.Environment;

public class FileStoreResource extends MemoryStoreResource {
  private File file;
  public FileStoreResource(File file, Environment environment) {
    this.file = file;
    readFromFile();
  }

  public void dispatch() {
    super.dispatch();
    writeToFile();
  }

  private void writeToFile() {

    try {
      Writer writer = null;
      try {
        writer = new BufferedWriter(new FileWriter(file));
        writer.write(getRows().serializeRow() + "\n");
        writer.write(getRows().serializePretty());
      } finally {
        writer.close();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private void readFromFile() {
    try {
      BufferedReader input = new BufferedReader(new FileReader(file));

      try {
        String str = input.readLine();
        setRows(Rows.createFromString(str));
      } finally {
        input.close();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
