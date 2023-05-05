package com.ali.dc.asistencias_uat.DataBase;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;

import com.ali.dc.asistencias_uat.DataBase.DAO.DAO_Users;
import com.ali.dc.asistencias_uat.Models.Users;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class UsersDB implements DAO_Users {
    @Override
    public void insert(Users object) {

        StringRequest solicitud = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // La solicitud se completó correctamente
                        Log.d(TAG, "La solicitud se completó correctamente: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Se produjo un error al realizar la solicitud
                        Log.e(TAG, "Se produjo un error al realizar la solicitud", error);
                    }
                }) {
            // Sobrescribir el método getParams() para agregar los parámetros de la solicitud
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", "Juan");
                params.put("email", "juan@mail.com");
                params.put("telefono", "123456789");
                return params;
            }
        };

// Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(new Activity());
        requestQueue.add(solicitud);
    }

    @Override
    public Users getById(String id) {
        return null;
    }

    @Override
    public void update(Users object) {

    }

    @Override
    public void delete(String id) {

    }
}
