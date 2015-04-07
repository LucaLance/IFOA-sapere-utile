package gruppo3.ifoasapereutile.Model;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Luca on 02/04/2015.
 */
public class KeyValue {

    private String key;
    private String value;

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
