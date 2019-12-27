package demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse3<T> implements Model,Serializable {
    private int code;
    private String message;
    private boolean result;
    private List<T> list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ServerResponse3() {

    }

    public ServerResponse3(int code, String message, boolean result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ServerResponse3(int code, String message, List<T> list) {
        this.code = code;
        this.message = message;
        this.list = list;
    }

    public ServerResponse3(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ServerResponse3<T> setMessage2(int code, String message) {
        return new ServerResponse3<>(code, message);
    }
    public static <T> Map<String, Object> setMessage3(int code, String message, boolean result) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code",code);
        map.put("message",message);
        map.put("result",result);
        return map;
    }
    public static <T> ServerResponse3<T> setMessage4(int code, String message, List<T> list) {
        return new ServerResponse3<>(code, message,list);
    }


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public Model addAttribute(String s, @Nullable Object o) {
        return null;
    }

    @Override
    public Model addAttribute(Object o) {
        return null;
    }

    @Override
    public Model addAllAttributes(Collection<?> collection) {
        return null;
    }

    @Override
    public Model addAllAttributes(Map<String, ?> map) {
        return null;
    }

    @Override
    public Model mergeAttributes(Map<String, ?> map) {
        return null;
    }

    @Override
    public boolean containsAttribute(String s) {
        return false;
    }

    @Nullable
    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Map<String, Object> asMap() {
        return null;
    }
}
