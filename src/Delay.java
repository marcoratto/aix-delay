
package it.marcoratto.ai2.delay;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.YailList;
import com.google.appinventor.components.runtime.util.JsonUtil;

import gnu.math.IntNum;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


@DesignerComponent(
    version = Delay.VERSION,
    description = "SQLite database interface.",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "aiwebres/small-icon.png"
)

@SimpleObject(external = true)
public class Delay extends AndroidNonvisibleComponent implements Component {

    public static final int VERSION = 1;
    
    private static final String NAME = "Delay";

    private ComponentContainer container;
    private Context context;
    private boolean isRepl;

    /**
    * Constructor
    */
    public Delay(ComponentContainer container) {
        super(container.$form());
        isRepl = form instanceof ReplForm;  // Note: form is defined in our superclass
        this.container = container;
        context = (Context)container.$context();
    }

   /**
    * Executes a delay.
    * @param ms: Time expressed as milliseconds.
    */
    @SimpleFunction(description = "Executes a delay. "
                    )
    public void sleep(final long ms) {
		try {
			Thread.sleep(ms);
			
			form.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SleepFinished();
                }
            });
		} catch (Exception e) {
		}
        
    }
        
    // ==========================================
    // Events
    
    @SimpleEvent(description = "This event fires when the delay finished.")
    public void SleepFinished() {
        EventDispatcher.dispatchEvent(this, "SleepFinished");
    }
 
}

