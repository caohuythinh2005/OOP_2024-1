package datamanager;

import java.io.IOException;
import java.util.List;

public interface DataManager<T> {
    List<T> readData() throws IOException;
    void writeData(List<T> data) throws IOException;
    void addData(T data) throws IOException;
    void removeData(T data) throws IOException;
}