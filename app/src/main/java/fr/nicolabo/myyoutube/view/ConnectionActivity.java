package fr.nicolabo.myyoutube.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.nicolabo.myyoutube.R;
import fr.nicolabo.myyoutube.controller.AppPreferencesTool;
import fr.nicolabo.myyoutube.model.UserResponse;
import fr.nicolabo.myyoutube.model.callback.VideoService;
import fr.nicolabo.myyoutube.model.callback.VideoWebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nicolasboueme on 03/04/2016.
 */
public class ConnectionActivity extends Activity{

    @BindView(R.id.user_email) EditText userEmail;
    @BindView(R.id.user_password) EditText userPassword;
    private static String TAG = "ConnectionActivity";
    private AppPreferencesTool appPreferencesTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        ButterKnife.bind(this);

        appPreferencesTool = new AppPreferencesTool(this);

        if(appPreferencesTool.isAuthorized()) {
            Intent intent =  new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    @OnClick(R.id.sign_in)
    public void signIn() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (!(email.isEmpty()) && !(password.isEmpty())) {
            VideoService videoService = VideoWebService.getInstanceWithoutToken(getApplicationContext());
            Call<UserResponse> tokenCall = videoService.loginUser(email, password);
            tokenCall.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful() && !response.body().getError()) {
                        //Save the token and refresh token in a file on the user mobile
                        appPreferencesTool.saveUserAuthenticationInfo(response.body().getUser());
                        connection();
                    } else {
                        //Message.message(snackbar,linearLayout,R.string.wrong_credentials);
                    }
                }
                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });
        } else {
            //Message.message(snackbar, linearLayout, R.string.empty_field);
        }
    }

    public void connection() {
        Intent intent = new Intent(ConnectionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
