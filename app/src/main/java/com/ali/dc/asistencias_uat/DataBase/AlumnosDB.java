package com.ali.dc.asistencias_uat.DataBase;

import android.content.Context;
import android.util.Log;

import com.ali.dc.asistencias_uat.Controller.Callbacks.BooleanCallback;
import com.ali.dc.asistencias_uat.Controller.Callbacks.VolleyCallback;
import com.ali.dc.asistencias_uat.DataBase.DAO.DAO_Alumnos;
import com.ali.dc.asistencias_uat.Models.Administradores;
import com.ali.dc.asistencias_uat.Models.Alumnos;
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

public class AlumnosDB implements DAO_Alumnos {

    private Context context;

    public AlumnosDB(Context context) {
        this.context = context;
    }

    @Override
    public void insert(Alumnos object, BooleanCallback callback) {
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constantes.STDNS_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("JsonObject", response.toString());
                callback.onResponse(true, "Alumno(a) registrado(a).");
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
                String errorMsg = "";
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    callback.onFailure("Sin conexion a internet", 0);
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response.statusCode == 404){
                        errorMsg = "Alumnos no encontrados.";
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
    public void getById(String id, VolleyCallback<Alumnos> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constantes.STDNS_URL + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Alumnos alumnos = gson.fromJson(String.valueOf(response), Alumnos.class);
                Log.d("Objeto response =>", alumnos.toString());
                callback.onSuccess(alumnos);
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
                        errorMsg = "Alumno no esta registrado.";
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
    public void getByMatricula(String matricula, VolleyCallback<Alumnos> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constantes.STDNS_URL + "matricula/" + matricula, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Alumnos alumnos = gson.fromJson(String.valueOf(response), Alumnos.class);
                Log.d("Objeto response =>", alumnos.toString());
                callback.onSuccess(alumnos);
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
                        errorMsg = "Alumno no esta registrado.";
                    } else if (response.statusCode == 500) {
                        errorMsg = "Error en el servidor. Intnte mas tarde.";
                    }
                    Log.d("VolleyError", String.valueOf(response.statusCode));
                    callback.onFailure(errorMsg, 0);
                }
            }
        });
        queue.add(request);
    }

    @Override
    public void update(Alumnos object, VolleyCallback<Alumnos> callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        Gson gson = new Gson();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(gson.toJson(object));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH, Constantes.STDNS_URL + object.getId_alumno(), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("JsonObject", response.toString());
                    Alumnos alumnos = gson.fromJson(String.valueOf(response), Alumnos.class);
                Log.d("Objeto response =>", alumnos.toString());
                callback.onSuccess(alumnos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError", error.toString());
                String errorMsg = "";
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    callback.onFailure("Sin conexion a internet", 0);
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    errorMsg = "Ocurrio un error en tu petición.";
                    if (response.statusCode == 404) errorMsg = "Administador no encontrado.";
                    if (response.statusCode == 500) errorMsg = "Error en el servidor. Intente mas tarde.";
                    callback.onFailure(errorMsg, response.statusCode);
                }
            }
        });
        queue.add(request);
    }

    @Override
    public void delete(String id, BooleanCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, Constantes.STDNS_URL + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onResponse(true, "Alumno(a) eliminado(a).");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError", error.toString());
                String errorMsg = "";
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    callback.onResponse(false,"Sin conexion a internet");
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    errorMsg = "Ocurrio un error en tu petición.";
                    if (response.statusCode == 404) errorMsg = "Alumno(a) no encontrado(a).";
                    if (response.statusCode == 500) errorMsg = "Error en el servidor. Intente mas tarde.";
                    callback.onResponse(false, errorMsg);
                }
            }
        });
        queue.add(request);
    }
}
