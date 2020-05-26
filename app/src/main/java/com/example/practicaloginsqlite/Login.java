package com.example.practicaloginsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Login extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    Button buttonLogin;
    RadioButton radioButtonRegistrarse;
    Spinner spinnerLoginTipoUsuario;
    ArrayAdapter<TipoUsuario> adaptador;
    List<TipoUsuario> listaTiposUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handleSSLHandshake();
        this.asociarCampos();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("https://192.168.0.7/webservicephpandroid/functions/process/webserviceusuario.php");
            }
        });

        //Se llena el Spinner
        adaptador = new ArrayAdapter<TipoUsuario>(getApplicationContext(),android.R.layout.simple_spinner_item,llenarListaTipoUsuarios());
        spinnerLoginTipoUsuario.setAdapter(adaptador);

        radioButtonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserRegister.class);
                startActivity(intent);
            }
        });
    }

    public void asociarCampos() {
        this.editTextUsername = findViewById(R.id.editTextUserName);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.buttonLogin = findViewById(R.id.buttonLogin);
        this.radioButtonRegistrarse = findViewById(R.id.radioButtonLoginRegisterUser);
        this.spinnerLoginTipoUsuario = findViewById(R.id.spinnerLoginTipoUsuario);
    }

    private void ejecutarServicio(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String nombrecompleto = "";
                String nombretipousuario = "";
                try {
                    JSONObject obj = new JSONObject(response);
                    nombrecompleto = obj.getString("nombre") + " " + obj.getString("ap_pat") + " " + obj.getString("ap_mat");
                    nombretipousuario = obj.getString("nombretipousuario");
                    Intent intent2 = new Intent(getApplicationContext(), PanelAdmin.class);
                    intent2.putExtra("nombrecompleto", nombrecompleto);
                    intent2.putExtra("nombretipousuario", nombretipousuario);
                    startActivity(intent2);
                } catch (JSONException e) {
                    if(response.equals("errorTipoUsuario")) {
                        Toast.makeText(getApplicationContext(), "Tu usuario no tiene permisos para ese tipo de usuario", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("accion","readbyusernameandpassword");
                parametros.put("username", editTextUsername.getText().toString());
                parametros.put("password", editTextPassword.getText().toString());
                TipoUsuario tipoUsuario = (TipoUsuario) spinnerLoginTipoUsuario.getSelectedItem();
                parametros.put("idtipousuario", tipoUsuario.getIdTipoUsuario() + "");
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public List<TipoUsuario> llenarListaTipoUsuarios() {
        listaTiposUsuario = new ArrayList<TipoUsuario>();
        TipoUsuario tipoUsuario1 = new TipoUsuario(1,"Administrador");
        TipoUsuario tipoUsuario2 = new TipoUsuario(2,"Docente");
        TipoUsuario tipoUsuario3 = new TipoUsuario(3,"Alumno");
        listaTiposUsuario.add(tipoUsuario1);
        listaTiposUsuario.add(tipoUsuario2);
        listaTiposUsuario.add(tipoUsuario3);
        return listaTiposUsuario;
    }

    /** * Enables https connections */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}
