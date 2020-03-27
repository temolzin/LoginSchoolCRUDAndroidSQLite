package com.example.practicarecursoshumanos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class UserRegister extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextNombre;
    EditText editTextApPat;
    EditText editTextApMat;
    Spinner spinnerTipoUsuario;
    Button buttonRegistrar;
    Button buttonImagenAleatoria;
    RadioButton radioButtonRegistrarse;
    ArrayAdapter<TipoUsuario> adaptador;
    List<TipoUsuario> listaTiposUsuario;
    int[] imagenAleatorio = {R.drawable.avatar, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4,R.drawable.avatar5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregister);
        handleSSLHandshake();

        this.asociarCampos();

        //Se llena el Spinner
        adaptador = new ArrayAdapter<TipoUsuario>(getApplicationContext(),android.R.layout.simple_spinner_item,llenarListaTipoUsuarios());
        spinnerTipoUsuario.setAdapter(adaptador);

        buttonImagenAleatoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarImagen();
            }
        });

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarServicio("https://172.16.106.60/webserviceandroid/functions/process/webserviceusuario.php");
            }
        });
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

    private void asociarCampos() {
        this.buttonImagenAleatoria = findViewById(R.id.buttonimagenAleatoria);
        this.editTextUsername = findViewById(R.id.editTextUserName);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.editTextNombre = findViewById(R.id.editTextNombre);
        this.editTextApPat = findViewById(R.id.editTextApPat);
        this.editTextApMat = findViewById(R.id.editTextApMat);
        this.spinnerTipoUsuario = findViewById(R.id.spinnerTipoUsuario);
        this.buttonRegistrar = findViewById(R.id.buttonRegister);
        this.radioButtonRegistrarse = findViewById(R.id.radioButtonLoginRegister);
    }

    private void cambiarImagen() {
        ImageView imageView = findViewById(R.id.imageViewAleatorio);

        Random random = new Random();
        int posicion = random.nextInt(5);

        imageView.setImageResource(imagenAleatorio[posicion]);
    }

    private void ejecutarServicio(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACIÓN EXITOSA" + response, Toast.LENGTH_SHORT).show();
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
                parametros.put("accion","insert");
                parametros.put("username", editTextUsername.getText().toString());
                parametros.put("password", editTextPassword.getText().toString());
                parametros.put("nombre", editTextNombre.getText().toString());
                parametros.put("appat", editTextApPat.getText().toString());
                parametros.put("apmat", editTextApMat.getText().toString());
                TipoUsuario tipoUsuario = (TipoUsuario) spinnerTipoUsuario.getSelectedItem();
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

    public List<TipoUsuario> readtipousuarios(String url) {
        listaTiposUsuario = new ArrayList<TipoUsuario>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.d("mytag","hola");
                    for (int i = 0; i< jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        TipoUsuario tipoUsuario = new TipoUsuario(obj.getInt("id_tipo_usuario"), obj.getString("nombretipousuario"));
                        listaTiposUsuario.add(tipoUsuario);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                parametros.put("accion","readtipousuario");
                return parametros;
            }
        };
        return listaTiposUsuario;
    }
}

