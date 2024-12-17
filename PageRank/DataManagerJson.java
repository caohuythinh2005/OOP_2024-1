package pagerank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataManagerJson<T> implements DataManager<T> {
    private final String filePath;
    private final Class<T> type;

    public DataManagerJson(String filePath, Class<T> type) {
        this.filePath = filePath;
        this.type = type;
    }

    @Override
    public List<T> readData() throws IOException {
        return loadFromJsonFile(filePath, type);
    }

    @Override
    public void writeData(List<T> data) throws IOException {
        saveToJsonFile(data, filePath);
    }

    @Override
    public void addData(T data) throws IOException {
        List<T> currentData = readData();
        if (!currentData.contains(data)) {
            currentData.add(data);
            saveToJsonFile(currentData, filePath);
        }
    }

    @Override
    public void removeData(T data) throws IOException {
        List<T> currentData = readData();
        if (currentData.contains(data)) {
            currentData.remove(data);
            saveToJsonFile(currentData, filePath);
        }
    }

    private List<T> loadFromJsonFile(String filePath, Class<T> type) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = TypeToken.getParameterized(List.class, type).getType();
            return gson.fromJson(reader, listType);
        }
    }

    private void saveToJsonFile(List<T> items, String filePath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(items, writer);
        }
    }
}