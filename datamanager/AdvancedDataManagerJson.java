package datamanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedDataManagerJson<T> implements AdvancedDataManager<T> {
    private final List<DataManagerJson<T>> dataManagers;

    public AdvancedDataManagerJson(List<String> filePaths, Class<T> type) {
        this.dataManagers = new ArrayList<>();
        for (String filePath : filePaths) {
            this.dataManagers.add(new DataManagerJson<>(filePath, type));
        }
    }

    @Override
    public List<List<T>> getAllData() throws IOException {
        List<List<T>> allData = new ArrayList<>();
        for (DataManagerJson<T> dataManager : dataManagers) {
            allData.add(dataManager.readData());
        }
        return allData;
    }

    @Override
    public void addData(int index, T data) throws IOException {
        checkIndex(index);
        dataManagers.get(index).addData(data);
    }

    @Override
    public void removeData(int index, T data) throws IOException {
        checkIndex(index);
        dataManagers.get(index).removeData(data);
    }

    @Override
    public boolean containsData(int index, T data) throws IOException {
        checkIndex(index);
        return dataManagers.get(index).readData().contains(data);
    }

    @Override
    public void moveData(int sourceIndex, int destinationIndex, T data) throws IOException {
        checkIndex(sourceIndex);
        checkIndex(destinationIndex);
        if (containsData(sourceIndex, data)) {
            removeData(sourceIndex, data);
            addData(destinationIndex, data);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= dataManagers.size()) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}