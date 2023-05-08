package com.ali.dc.asistencias_uat.DataBase;

import android.content.Context;
import android.util.Log;

import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.DAO.DAO_Administradores;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.Utilities.Constantes;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class AdministradoresDB implements DAO_Administradores {

    private Context context;

    public AdministradoresDB(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Administradores object) {
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constantes.ADMIN_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("JsonObject", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError", error.toString());
            }
        });
        queue.add(request);
    }


    @Override
    public void getByFirebaseId(String id, VolleyCallback<Administradores> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constantes.ADMIN_URL + "fb/" + id.trim(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Administradores administradores = gson.fromJson(String.valueOf(response), Administradores.class);
                Log.d("Objeto response =>", administradores.toString());
                callback.onSuccess(administradores);
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
    public void getAll(VolleyCallback<List<Administradores>> callback) {

    }

    @Override
    public Administradores getById(String id, VolleyCallback<Administradores> callback) {
        return null;
    }

    @Override
    public void update(Administradores object, VolleyCallback<Administradores> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(gson.toJson(object));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH, Constantes.ADMIN_URL + object.getId_admin(), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("JsonObject", response.toString());
                Administradores administradores = gson.fromJson(String.valueOf(response), Administradores.class);
                Log.d("Objeto response =>", administradores.toString());
                callback.onSuccess(administradores);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
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
    public void delete(String id, VolleyCallback<Administradores> callback) {

    }
}
