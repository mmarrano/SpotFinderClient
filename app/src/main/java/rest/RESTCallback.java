package rest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * rest : RESTCallback
 * <p>
 * Created by Thomas John Wesolowski on 4/24/2018.
 * Contact email: wojoinc@gmail.com
 * GitHub username: WojoInc
 * Version ${VERSION}       Last Updated ${LAST_UPDATE_DATE}
 */
public interface RESTCallback {
    void onSuccess(JSONObject result);

    void onSuccess(JSONArray result);

}
