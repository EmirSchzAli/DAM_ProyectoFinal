package com.ali.dc.asistencias_uat.DataBase;

import android.content.Context;
import android.util.Log;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.DAO.DAO_Registros;
import com.ali.dc.asistencias_uat.Models.Alumnos;
import com.ali.dc.asistencias_uat.Models.Registros;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
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

public class RegistrosDB implements DAO_Registros {

    Context context;
    public RegistrosDB(Context context){
        this.context = context;
    }


    @Override
    public void insert(Registros object, BooleanCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d(Constantes.TAG, object.toString());
        Gson gson = new Gson();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(gson.toJson(object));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Log.d("JsonObject", jsonObject.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constantes.REGTRY_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("JsonObject", response.toString());
                callback.onResponse(true, "Ingreso al aula registrado.");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = "";
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    callback.onResponse(false, "Sin conexion a internet");
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response.statusCode == 500) errorMsg = "Error en el servidor. Intente mas tarde.";
                    Log.d("VolleyError", String.valueOf(response.statusCode));
                    callback.onResponse(false, "Sin conexion a internet");
                }
            }
        });
        queue.add(request);
    }

    @Override
    public void getAll(VolleyCallback<List<Registros>> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Constantes.REGTRY_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Registros> registrosList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Gson gson = new Gson();
                        Registros registros = gson.fromJson(jsonObject.toString(), Registros.class);
                        registrosList.add(registros);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(registrosList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = "";
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    callback.onFailure("Sin conexion a internet", 0);
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response.statusCode == 404){
                        errorMsg = "Registros de asistencias no encontrados.";
                    } else if (response.statusCode == 500) {
                        errorMsg = "Error en el servidor. Intente mas tarde.";
                    }
                    Log.d("VolleyError", String.valueOf(response.statusCode));
                    callback.onFailure(errorMsg, 0);
                }
            }
        });
        queue.add(request);
    }

    @Override
    public void getById(String id, VolleyCallback<Registros> callback) {

    }

    @Override
    public void update(Registros object, VolleyCallback<Registros> callback) {

    }

    @Override
    public void delete(String id, BooleanCallback callback) {

    }

    @Override
    public void getAllbyId(String id_admin, VolleyCallback<List<Registros>> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Constantes.REGTRY_URL + id_admin, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Registros> registrosList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Gson gson = new Gson();
                        Registros registros = gson.fromJson(jsonObject.toString(), Registros.class);
                        registrosList.add(registros);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callback.onSuccess(registrosList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = "";
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    callback.onFailure("Sin conexion a internet", 0);
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response.statusCode == 404){
                        errorMsg = "Registros de asistencias no encontrados.";
                    } else if (response.statusCode == 500) {
                        errorMsg = "Error en el servidor. Intente mas tarde.";
                    }
                    Log.d("VolleyError", String.valueOf(response.statusCode));
                    callback.onFailure(errorMsg, 0);
                }
            }
        });
        queue.add(request);
    }
}
