package edu.spot_finder_client.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.spot_finder_client.MainActivity;
import edu.spot_finder_client.R;
import rest.RESTCallback;
import rest.User;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText et_username = findViewById(R.id.etUsername);
        final EditText et_email = findViewById(R.id.etEmail);


        final TextView tv_username_error = findViewById(R.id.tvUsernameError);
        final TextView tv_email_error = findViewById(R.id.tvEmailError);

        final Button b_register = (Button) findViewById(R.id.bRegister);

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            tv_username_error.setText("");

            if(et_username.getText().toString().isEmpty())
                tv_username_error.setText("Username cannot be blank.");

            if(et_email.getText().toString().isEmpty()){
                tv_email_error.setText("Email cannot be blank.");
            }

            final User user = new User(et_username.getText().toString(), et_email.getText().toString());

            user.LIST(getApplicationContext(), new RESTCallback() {
                @Override
                public void onSuccess(JSONObject result) {

                }

                @Override
                public void onSuccess(JSONArray result) {
                    ArrayList<User> users = new ArrayList<>();

                    for (int i = 0; i < result.length(); i++) {
                        try {
                            users.add(User.fromJSON(result.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (users.contains(et_username.getText().toString())) {
                        Toast.makeText(RegisterActivity.this.getApplication(), "Username is already taken.", Toast.LENGTH_SHORT).show();
                    } else {

                        user.CREATE(getApplicationContext(), new RESTCallback() {
                            @Override
                            public void onSuccess(JSONObject result) {

                            }

                            @Override
                            public void onSuccess(JSONArray result) {

                            }
                        });


                        Intent register_intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                        register_intent.putExtra("username", et_username.getText().toString());
                        RegisterActivity.this.startActivity(register_intent);
                    }
                }
            });
            }
        });
    }
}