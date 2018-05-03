package rest;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * rest : Spot
 * <p>
 * Created by Thomas John Wesolowski on 4/23/2018.
 * Contact email: wojoinc@gmail.com
 * GitHub username: WojoInc
 * Version ${VERSION}       Last Updated ${LAST_UPDATE_DATE}
 */
public class Spot extends Model {
    private int id;
    private Date lastCheckIn, lastCheckOut;
    private Lot lot;
    private Request request;

    public Spot() {
    }

    public Spot(int id) {
        this.id = id;
    }

    public static Spot fromJson(JSONObject jsonObject) {
        Spot spot = new Spot();
        try {
            spot.id = jsonObject.getInt("id");
            spot.lot = Lot.fromJSON(jsonObject.getJSONObject("lot"));
            //spot.lastCheckIn = new Date(jsonObject.getString("lastCheckIn"));
            //spot.lastCheckOut = new Date(jsonObject.getString("lastCheckOut"));
        } catch (JSONException e) {
            spot.lot = null;
            System.out.println(e.getMessage());
        }
        return spot;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonObject;
    }

    @Override
    public void LIST(Context context, RESTCallback callback) {

    }

    @Override
    public void GET(Context context, final RESTCallback callback) {
        request = new JsonObjectRequest(Request.Method.GET, BASE_URL + "/spot/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                updateOnResponse(response);
                Log.println(Log.INFO, null, "response");
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RestHelper.getInstance(context).addToRequestQueue(request);
    }

    private void updateOnResponse(JSONObject jsonObject) {
        try {
            id = jsonObject.getInt("id");
            lot = Lot.fromJSON(jsonObject.getJSONObject("lot"));
            //lastCheckIn = Calendar.getInstance().getTime();
            //spot.lastCheckOut = new Date(jsonObject.getString("lastCheckOut"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void checkIn(Context context) {
        request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "/spot/" + id + "/checkin", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.println(Log.INFO, null, "response");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RestHelper.getInstance(context).addToRequestQueue(request);
    }

    public void checkOut(Context context) {
        request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "/spot/" + id + "/checkout", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.println(Log.INFO, null, "response");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RestHelper.getInstance(context).addToRequestQueue(request);
    }

}
