package com.omgcaps.javautils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import com.google.gson.Gson;

public class TestHttpUtil {

    final String testUrl = "http://omggamesftw.com/tools/httptest.php";
    final String var1 = "variable1", var2 = "variable2", value = "true";

    Response response;

    @Test
    public void testGetOverload() {
        try {
            String response = HttpUtil.get(testUrl + "?" + var1 + "=" + value + "&" + var2 + "=" + value);
            this.response = new Gson().fromJson(response, Response.class);

            assertEquals(this.response.getParameters().size(), 2);
            assertEquals(this.response.getMethod(), "GET");
            assertEquals(this.response.getParameters().get(var1), value);
            assertEquals(this.response.getParameters().get(var2), value);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet() {
        try {
            HashMap<String, String> params = new HashMap<String, String>();

            params.put(var1, value);
            params.put(var2, value);

            String response;
            response = HttpUtil.get(testUrl, params);

            this.response = new Gson().fromJson(response, Response.class);

            assertEquals(this.response.getParameters().size(), 2);
            assertEquals(this.response.getMethod(), "GET");
            assertEquals(this.response.getParameters().get(var1), value);
            assertEquals(this.response.getParameters().get(var2), value);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testPost() {
        try {
            HashMap<String, String> params = new HashMap<String, String>();

            params.put(var1, value);
            params.put(var2, value);

            String response;
            response = HttpUtil.post(testUrl, params);

            this.response = new Gson().fromJson(response, Response.class);

            assertEquals(this.response.getParameters().size(), 2);
            assertEquals(this.response.getMethod(), "POST");
            assertEquals(this.response.getParameters().get(var1), value);
            assertEquals(this.response.getParameters().get(var2), value);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPut() {
        try {
            String response;
            response = HttpUtil.put(testUrl, null);

            this.response = new Gson().fromJson(response, Response.class);

            assertEquals(this.response.getParameters().size(), 0);
            assertEquals(this.response.getMethod(), "PUT");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete() {
        try {
            String response;
            response = HttpUtil.delete(testUrl, null);

            this.response = new Gson().fromJson(response, Response.class);

            assertEquals(this.response.getParameters().size(), 0);
            assertEquals(this.response.getMethod(), "DELETE");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Response {
        private String method;
        private HashMap<String, String> parameters;

        public String getMethod() {
            return this.method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public HashMap<String, String> getParameters() {
            return this.parameters;
        }

        public void setParameters(HashMap<String, String> parameters) {
            this.parameters = parameters;
        }
    }
}
