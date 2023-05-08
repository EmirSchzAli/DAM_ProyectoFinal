package com.ali.dc.asistencias_uat.DataBase;

import android.content.Context;
import android.util.Log;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.DAO.DAO_Alumnos;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlumnosDB implements DAO_Alumnos {

    private Context context;

    public AlumnosDB(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Alumnos object) {

    }

    @Override
    public void getAll(VolleyCallback<List<Alumnos>> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Constantes.STDNS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Alumnos> alumnosList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Gson gson = new Gson();
                        Alumnos alumnos = gson.fromJson(jsonObject.toString(), Alumnos.class);
                        alumnosList.add(alumnos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(alumnosList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError", error.toString());
                NetworkResponse response = error.networkResponse;
                String errorMsg = "Ocurrio un error en tu petición.";
                if (response.statusCode == 404) errorMsg = "Administador no esta registrado.";
                if (response.statusCode == 500) errorMsg = "Error en el servidor. Intente mas tarde.";
                callback.onFailure(errorMsg);
            }
        });
        queue.add(request);
    }

    @Override
    public Alumnos getById(String id, VolleyCallback<Alumnos> callback) {
        return null;
    }

    @Override
    public void update(Alumnos object, VolleyCallback<Alumnos> callback) {

    }

    @Override
    public void delete(String id, VolleyCallback<Alumnos> callback) {

    }
}
