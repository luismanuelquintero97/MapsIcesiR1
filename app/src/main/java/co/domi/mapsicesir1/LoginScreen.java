package co.domi.mapsicesir1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    private EditText userName;
    private EditText userPassword;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userName= findViewById(R.id.userName);
        userPassword= findViewById(R.id.userPassword);
        loginBtn = findViewById(R.id.LoginBtn);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION},1
                );


        loginBtn.setOnClickListener(
            (V) -> {
                String user = userName.getText().toString();
                String password = userPassword.getText().toString();

                Intent i = new Intent(this,MapsActivity.class);
                i.putExtra("user",user);
                i.putExtra("password",password);
                startActivity(i);
            }
        );
    }
}