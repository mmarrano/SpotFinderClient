package rest;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * rest : Lot
 * <p>
 * Created by Thomas John Wesolowski on 4/23/2018.
 * Contact email: wojoinc@gmail.com
 * GitHub username: WojoInc
 * Version ${VERSION}       Last Updated ${LAST_UPDATE_DATE}
 */
public class Lot extends Model {
    private int id, available, count;
    private String owner;
    private String name, address;
    private Request request;
    private ArrayList<Spot> spots;

    public Lot(int id) {
        this.id = id;
    }

    public Lot() {

    }

    public static Lot fromJSON(JSONObject jsonObject) {
        Lot lot = new Lot();
        try {
            lot.id = jsonObject.getInt("id");
            lot.name = jsonObject.getString("name");
            lot.available = jsonObject.getInt("available");
            lot.address = jsonObject.getString("address");
            lot.owner = jsonObject.getJSONObject("account").getString("name");
            lot.count = jsonObject.getInt("count");
            lot.spots = new ArrayList<Spot>();
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("spots");
                for (int i = 0; i < jsonArray.length(); i++) {
                    lot.spots.add(Spot.fromJson(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lot;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Spot> getSpots() {
        return spots;
    }

    public void setSpots(ArrayList<Spot> spots) {
        this.spots = spots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void LIST(final Context context, final RESTCallback callback) {
        JsonArrayRequest listRequest = new JsonArrayRequest(Request.Method.GET, BASE_URL + "/lot/list", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.println(Log.INFO, "Request", response.toString());
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        RestHelper.getInstance(context).addToRequestQueue(listRequest);
    }

    @Override
    public void GET(Context context, final RESTCallback callback) {

        request = new JsonObjectRequest(Request.Method.GET, BASE_URL + "/lot/" + id, null, new Response.Listener<JSONObject>() {
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
            name = jsonObject.getString("name");
            available = jsonObject.getInt("available");
            address = jsonObject.getString("address");
            owner = jsonObject.getJSONObject("account").getString("name");
            count = jsonObject.getInt("count");
            spots = new ArrayList<Spot>();
            JSONArray jsonArray = jsonObject.getJSONArray("spots");
            for (int i = 0; i < jsonArray.length(); i++) {
                spots.add(Spot.fromJson(jsonArray.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
/*
    @Override

    public void UPDATE(Context context) {
        request = new JsonObjectRequest(Request.Method.PUT, BASE_URL + "/lot/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setName(response.getString("name"));
                    //TODO set information
                } catch (JSONException ex) {
                    Log.println(Log.ERROR, "Request Error", ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR, "Request Error", error.getMessage());
            }
        });
        RestHelper.getInstance(context).addToRequestQueue(request);
    }
*/
/*

    @Override
    public void DELETE(Context context) {
        request = new JsonObjectRequest(Request.Method.DELETE, BASE_URL + "/lot/" + id, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR, "Request Error", error.getMessage());
            }
        });
        RestHelper.getInstance(context).addToRequestQueue(request);
    }
*/
/*
    @Override
    public void CREATE(Context context) {
        request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "/lot/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setName(response.getString("name"));
                    //TODO set information
                } catch (JSONException ex) {
                    Log.println(Log.ERROR, "Request Error", ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.println(Log.ERROR, "Request Error", error.getMessage());
            }
        });
        RestHelper.getInstance(context).addToRequestQueue(request);
    }*/
}
