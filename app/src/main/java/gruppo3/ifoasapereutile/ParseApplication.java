package gruppo3.ifoasapereutile;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "2YAQjKxqd7uIMoXQT7IdRNXAhZNmIp3hFvVigEr7", "wL2N16SJnSZHBIatVUz8SxkHfDzIaLihIoicHL18");
    }
}
