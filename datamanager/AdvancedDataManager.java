package datamanager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface AdvancedDataManager<T> {
    List<List<T>> getAllData() throws IOException;
    void addData(int index, T data) throws IOException;
    void removeData(int index, T data) throws IOException;
    boolean containsData(int index, T data) throws IOException;
    void moveData(int sourceIndex, int destinationIndex, T data) throws IOException;
}