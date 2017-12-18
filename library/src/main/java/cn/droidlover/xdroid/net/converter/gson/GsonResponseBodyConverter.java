package cn.droidlover.xdroid.net.converter.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import cn.droidlover.xdroid.net.exception.ResultErrorException;
import cn.droidlover.xdroid.net.model.SimpleModel;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            T t = adapter.read(jsonReader);
            if (t instanceof SimpleModel && !((SimpleModel) t).isValid()) {
                throw new ResultErrorException(((SimpleModel) t).getResultCode(), ((SimpleModel) t).getResultMsg());
            }
            return t;
        } finally {
            value.close();
        }
    }
}
