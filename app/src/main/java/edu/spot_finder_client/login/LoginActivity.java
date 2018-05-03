package edu.spot_finder_client.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.spot_finder_client.MainActivity;
import edu.spot_finder_client.R;
import rest.RESTCallback;
import rest.User;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saved_instance_state) {
        super.onCreate(saved_instance_state);
        setContentView(R.layout.activity_login);

        final EditText et_username = (EditText) findViewById(R.id.etUsername);
        final EditText et_password = (EditText) findViewById(R.id.etPassword);

        final Button b_login = (Button) findViewById(R.id.bLogin);
//        final Button b_register = (Button) findViewById(R.id.bRegister);
//
//        b_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                LoginActivity.this.startActivity(register_intent);
//            }
//        });

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            User user = new User(et_username.getText().toString());

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", et_username.getText().toString());
            LoginActivity.this.startActivity(intent);


//            user.LIST(getApplicationContext(), new RESTCallback() {
//                @Override
//                public void onSuccess(JSONObject result) {
//
//                }
//
//                @Override
//                public void onSuccess(JSONArray result) {
//                    ArrayList<User> users = new ArrayList<>();
//                    ArrayList<String> names = new ArrayList<>();
//
//
//                    for (int i = 0; i < result.length(); i++) {
//                        try {
//                            users.add(User.fromJSON(result.getJSONObject(i)));
//                            names.add(users.get(i).getName());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    if (names.contains(et_username.getText().toString()) && et_password.getText().toString().compareTo("hunter12") == 0) {
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("username", et_username.getText().toString());
//                        LoginActivity.this.startActivity(intent);
//                    } else {
//                        Toast.makeText(LoginActivity.this.getApplication(), "Login Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
            }
        });
    }
}
