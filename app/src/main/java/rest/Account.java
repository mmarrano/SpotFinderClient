package rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * rest : Account
 * <p>
 * Created by Thomas John Wesolowski on 4/24/2018.
 * Contact email: wojoinc@gmail.com
 * GitHub username: WojoInc
 * Version ${VERSION}       Last Updated ${LAST_UPDATE_DATE}
 */
public class Account extends Model {
    private String name;
    private int id;
    private ArrayList<Lot> lots;

    public Account() {
    }

    public Account(int id) {
        this.id = id;
    }

    public static Account fromJSON(JSONObject jsonObject) {
        Account account = new Account();
        try {
            account.id = jsonObject.getInt("id");
            account.name = jsonObject.getString("name");
            account.lots = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray("lots");
            for (int i = 0; i < jsonArray.length(); i++) {
                account.lots.add(Lot.fromJSON(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Lot> getLots() {
        return lots;
    }

    public void setLots(ArrayList<Lot> lots) {
        this.lots = lots;
    }
}
