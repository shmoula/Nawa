package cz.shmoula.nawa;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Application singleton
 * Created by vbalak on 01/08/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
