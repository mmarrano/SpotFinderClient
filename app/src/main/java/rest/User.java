package rest;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * rest : User
 * <p>
 * Created by Thomas John Wesolowski on 4/23/2018.
 * Contact email: wojoinc@gmail.com
 * GitHub username: WojoInc
 * Version ${VERSION}       Last Updated ${LAST_UPDATE_DATE}
 */
public class User extends Model {
    private Request request;
    private int ID;
    private String name, email, password;
    private int AccountID;

    public User(){}

    public User(String name) {
        this.name = name;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.password = "hunter12";
    }

    public String getName() {
        return name;
    }

    public void setFirstName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void GET(Context context, RESTCallback callback) {
        request = new JsonObjectRequest(Request.Method.GET, BASE_URL + "/user/" + ID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RestHelper.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void LIST(Context context, RESTCallback callback) {
        request = new JsonObjectRequest(Request.Method.GET, BASE_URL + "/user/list", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RestHelper.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public void CREATE(Context context, RESTCallback callback) {

        JSONObject json = new JSONObject();

        try{
            json.put("name", getName());
            json.put("email", getEmail());
            json.put("password", "hunter12");
        } catch (JSONException e){
            e.printStackTrace();
        }

        request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "/user", json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RestHelper.getInstance(context).addToRequestQueue(request);
    }
    

    @Override
    public void POST(Context context, RESTCallback callback){
        request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "/user", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                headers.put("Authorization", "Basic " + Base64.encodeToString((getName() + ":hunter12").getBytes(), Base64.NO_WRAP));
                return headers;
            }

//            @Override
//            public Response<JSONObject> parseNetworkResponse(NetworkResponse response){
//                statusCode = response.statusCode;
//                switch (statusCode){
//                    case HttpURLConnection.HTTP_OK:
//                        break;
//                    case HttpURLConnection.HTTP_UNAUTHORIZED:
//                        break;
//                }
//                return super.parseNetworkResponse(response);
//            }
        };

        RestHelper.getInstance(context).addToRequestQueue(request);
    }

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

}
