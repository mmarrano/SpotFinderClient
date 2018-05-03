package rest;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

/**
 * rest : RestHelper
 * <p>
 * Created by Thomas John Wesolowski on 3/26/2018.
 * Contact email: wojoinc@gmail.com
 * GitHub username: WojoInc
 * Version ${VERSION}       Last Updated ${LAST_UPDATE_DATE}
 */
public class RestHelper {

    private RequestQueue queue;
    private static RestHelper instance;
    private Context context;

    public RestHelper(Context context) {
        this.context = context;
        this.queue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(queue == null){
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return queue;
    }
    public static synchronized RestHelper getInstance(Context context){
        if(instance == null){
            instance = new RestHelper(context);
        }
        return instance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
