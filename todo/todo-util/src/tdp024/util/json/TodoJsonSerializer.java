package tdp024.util.json;

public interface TodoJsonSerializer {

    public <T> T fromJson(String json, Class<T> clazz);
    
    public String toJson(Object object);
}
