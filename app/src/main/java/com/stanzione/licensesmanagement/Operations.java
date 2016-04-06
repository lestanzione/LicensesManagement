package com.stanzione.licensesmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.stanzione.licensesmanagement.model.Company;
import com.stanzione.licensesmanagement.model.Contact;
import com.stanzione.licensesmanagement.model.Project;
import com.stanzione.licensesmanagement.model.ProjectSoftware;
import com.stanzione.licensesmanagement.model.Software;
import com.stanzione.licensesmanagement.model.UserAccess;
import com.stanzione.licensesmanagement.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Leandro Stanzione on 13/12/2015.
 */
public class Operations {

    private static final String TAG = Operations.class.getSimpleName();

    public interface OperationsCallback{

        public void onOperationSuccess(Object returnObject, int operationCode);
        public void onOperationFail(Object returnObject, int operationCode);
        public void onOperationError(Object returnObject, int operationCode);

    }

    OperationsCallback fragment;
    int operationCode;

    public Operations(Object operationsCallback, int operationCode){
        if (operationsCallback instanceof OperationsCallback) {
            fragment = (OperationsCallback) operationsCallback;
        } else {
            throw new RuntimeException(fragment.toString()
                    + " must implement OperationsCallback");
        }
        this.operationCode = operationCode;
    }

    public void doLogin(String login, String pass){

        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {

                try {

                    URL url = new URL("http://empiremobileapps.com/licenses/login.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("user", params[0]);
                    jsonParams.put("pass", params[1]);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }

                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(convertToUserAccess(jsonObject), operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new String[]{login, pass});

    }

    public void getCompanyList(){

        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {

                try {

                    URL url = new URL("http://empiremobileapps.com/licenses/getCompanies.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(convertToCompanyArray(jsonObject), operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute();

    }

    public void getProjectList(){

        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {

                try {

                    URL url = new URL("http://empiremobileapps.com/licenses/getProjects.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(convertToProjectArray(jsonObject), operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute();

    }

    public void getProjectListFromCompany(int companyId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    int companyId = (int) params[0];

                    URL url = new URL("http://empiremobileapps.com/licenses/getProjectsByCompany.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("companyId", companyId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(convertToProjectArray(jsonObject), operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{companyId});

    }

    public void getSoftwareList(){

        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {

                try {

                    URL url = new URL("http://empiremobileapps.com/licenses/getSoftwares.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(convertToSoftwareArray(jsonObject), operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute();

    }

    public void getContactList(){

        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {

                try {

                    URL url = new URL("http://empiremobileapps.com/licenses/getContacts.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(convertToContactArray(jsonObject), operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute();

    }

    public void getContactListFromCompany(int companyId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    int companyId = (int) params[0];

                    URL url = new URL("http://empiremobileapps.com/licenses/getContactsByCompany.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("companyId", companyId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();


                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(convertToContactArray(jsonObject), operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{companyId});

    }

    public void getProjectSoftwareListFromProject(int projectId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    int projectId = (int) params[0];

                    URL url = new URL("http://empiremobileapps.com/licenses/getProjectSoftwaresByProject.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("projectId", projectId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();


                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(convertToProjectSoftwareArray(jsonObject), operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{projectId});

    }

    public void addCompany(String companyName, String companyAddress, int creationUserId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    String companyName = (String) params[0];
                    String companyAddress = (String) params[1];
                    String creationDate = getCurrentDateMySQL();
                    int creationUserId = (int) params[2];

                    URL url = new URL("http://empiremobileapps.com/licenses/addCompany.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("name", companyName);
                    jsonParams.put("address", companyAddress);
                    jsonParams.put("creationDate", creationDate);
                    jsonParams.put("creationUserId", creationUserId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(jsonObject, operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{companyName, companyAddress, creationUserId});

    }

    public void addProject(String projectName, int projectCompanyId, int creationUserId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    String projectName = (String) params[0];
                    int projectCompanyId = (int) params[1];
                    String creationDate = getCurrentDateMySQL();
                    int creationUserId = (int) params[2];

                    URL url = new URL("http://empiremobileapps.com/licenses/addProject.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("name", projectName);
                    jsonParams.put("companyId", projectCompanyId);
                    jsonParams.put("creationDate", creationDate);
                    jsonParams.put("creationUserId", creationUserId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(jsonObject, operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{projectName, projectCompanyId, creationUserId});

    }

    public void addSoftware(String softwareName, String softwareCode, String softwareType, int creationUserId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    String softwareName = (String) params[0];
                    String softwareCode = (String) params[1];
                    String softwareType = (String) params[2];
                    String creationDate = getCurrentDateMySQL();
                    int creationUserId = (int) params[3];

                    URL url = new URL("http://empiremobileapps.com/licenses/addSoftware.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("name", softwareName);
                    jsonParams.put("code", softwareCode);
                    jsonParams.put("type", softwareType);
                    jsonParams.put("creationDate", creationDate);
                    jsonParams.put("creationUserId", creationUserId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(jsonObject, operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{softwareName, softwareCode, softwareType, creationUserId});

    }

    public void addContact(String contactFirstName, String contactLastName, String contactTitle, String contactEmail, String contactTelNumber, int contactCompanyId, int creationUserId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    String contactFirstName = (String) params[0];
                    String contactLastName = (String) params[1];
                    String contactTitle = (String) params[2];
                    String contactEmail = (String) params[3];
                    String contactTelNumber = (String) params[4];
                    String creationDate = getCurrentDateMySQL();
                    int contactCompanyId = (int) params[5];
                    int creationUserId = (int) params[6];

                    URL url = new URL("http://empiremobileapps.com/licenses/addContact.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("firstName", contactFirstName);
                    jsonParams.put("lastName", contactLastName);
                    jsonParams.put("title", contactTitle);
                    jsonParams.put("email", contactEmail);
                    jsonParams.put("telNumber", contactTelNumber);
                    jsonParams.put("creationDate", creationDate);
                    jsonParams.put("companyId", contactCompanyId);
                    jsonParams.put("creationUserId", creationUserId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(jsonObject, operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{contactFirstName, contactLastName, contactTitle, contactEmail, contactTelNumber, contactCompanyId, creationUserId});

    }

    public void editCompany(int companyId, String companyAddress, int modificationUserId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    int companyId = (int) params[0];
                    String companyAddress = (String) params[1];
                    String modificationDate = getCurrentDateMySQL();
                    int modificationUserId = (int) params[2];

                    URL url = new URL("http://empiremobileapps.com/licenses/editCompany.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("id", companyId);
                    jsonParams.put("address", companyAddress);
                    jsonParams.put("modificationDate", modificationDate);
                    jsonParams.put("modificationUserId", modificationUserId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(jsonObject, operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{companyId, companyAddress, modificationUserId});

    }

    public void editProject(int projectId, String projectName, String projectStartDate, String projectEndDate, int modificationUserId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    int projectId = (int) params[0];
                    String projectName = (String) params[1];
                    String projectStartDate = (String) params[2];
                    String projectEndDate = (String) params[3];
                    String modificationDate = getCurrentDateMySQL();
                    int modificationUserId = (int) params[4];

                    URL url = new URL("http://empiremobileapps.com/licenses/editProject.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("id", projectId);
                    jsonParams.put("name", projectName);
                    jsonParams.put("startDate", projectStartDate);
                    jsonParams.put("endDate", projectEndDate);
                    jsonParams.put("modificationDate", modificationDate);
                    jsonParams.put("modificationUserId", modificationUserId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(jsonObject, operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{projectId, projectName, projectStartDate, projectEndDate, modificationUserId});

    }

    public void editContact(int contactId, String contactFirstName, String contactLastName, String contactTitle, String contactEmail, String contactTelNumber, int modificationUserId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    int contactId = (int) params[0];
                    String contactFirstName = (String) params[1];
                    String contactLastName = (String) params[2];
                    String contactTitle = (String) params[3];
                    String contactEmail = (String) params[4];
                    String contactTelNumber = (String) params[5];
                    String modificationDate = getCurrentDateMySQL();
                    int modificationUserId = (int) params[6];

                    URL url = new URL("http://empiremobileapps.com/licenses/editContact.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("id", contactId);
                    jsonParams.put("firstName", contactFirstName);
                    jsonParams.put("lastName", contactLastName);
                    jsonParams.put("title", contactTitle);
                    jsonParams.put("email", contactEmail);
                    jsonParams.put("telNumber", contactTelNumber);
                    jsonParams.put("modificationDate", modificationDate);
                    jsonParams.put("modificationUserId", modificationUserId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(jsonObject, operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{contactId, contactFirstName, contactLastName, contactTitle, contactEmail, contactTelNumber, modificationUserId});

    }

    public void removeCompany(int companyId, int modificationUserId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    int companyId = (int) params[0];
                    String modificationDate = getCurrentDateMySQL();
                    int modificationUserId = (int) params[1];

                    URL url = new URL("http://empiremobileapps.com/licenses/removeCompany.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("id", companyId);
                    jsonParams.put("modificationDate", modificationDate);
                    jsonParams.put("modificationUserId", modificationUserId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(jsonObject, operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{companyId, modificationUserId});

    }

    public void removeProject(int projectId, int modificationUserId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    int projectId = (int) params[0];
                    String modificationDate = getCurrentDateMySQL();
                    int modificationUserId = (int) params[1];

                    URL url = new URL("http://empiremobileapps.com/licenses/removeProject.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("id", projectId);
                    jsonParams.put("modificationDate", modificationDate);
                    jsonParams.put("modificationUserId", modificationUserId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(jsonObject, operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{projectId, modificationUserId});

    }

    public void removeContact(int contactId, int modificationUserId){

        new AsyncTask<Object, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(Object... params) {

                try {

                    int contactId = (int) params[0];
                    String modificationDate = getCurrentDateMySQL();
                    int modificationUserId = (int) params[1];

                    URL url = new URL("http://empiremobileapps.com/licenses/removeContact.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParams = new JSONObject();
                    jsonParams.put("id", contactId);
                    jsonParams.put("modificationDate", modificationDate);
                    jsonParams.put("modificationUserId", modificationUserId);

                    Log.d(TAG, "jsonParams.toString(): " + jsonParams.toString());

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParams.toString());
                    writer.flush();
                    writer.close();

                    os.close();

                    conn.connect();

                    int response = conn.getResponseCode();
                    Log.d(TAG, "The response is: " + response);
                    InputStream is = conn.getInputStream();

                    // Convert the InputStream into a string
                    String returnString = convertToString(is);
                    Log.d(TAG, returnString);

                    JSONObject responseJson = new JSONObject(returnString);

                    return responseJson;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                try {

                    if(jsonObject == null){
                        fragment.onOperationError("", operationCode);
                    }
                    int code = jsonObject.getInt("code");

                    if(code == 1){
                        fragment.onOperationSuccess(jsonObject, operationCode);
                    }
                    else{
                        fragment.onOperationFail(jsonObject, operationCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                fragment.onOperationError(jsonObject, operationCode);

            }
        }.execute(new Object[]{contactId, modificationUserId});

    }

    private UserAccess convertToUserAccess(JSONObject jsonObject) throws JSONException {

        JSONArray userJsonArray = jsonObject.getJSONArray("user");

        UserAccess loggedUser = new UserAccess();

        for(int i=0; i<userJsonArray.length(); i++){

            JSONObject userJsonObj = userJsonArray.getJSONObject(i);
            int id = userJsonObj.getInt("id");
            String firstName = userJsonObj.getString("firstName");
            String lastName = userJsonObj.getString("lastName");
            String userName = userJsonObj.getString("userName");
            String email = userJsonObj.getString("email");
            String telNumber = userJsonObj.getString("telNumber");
            boolean activate = (userJsonObj.getString("activate").equals("1")) ? true : false;

            UserAccess userAccess = new UserAccess();

            userAccess.setId(id);
            userAccess.setFirstName(firstName);
            userAccess.setLastName(lastName);
            userAccess.setUserName(userName);
            userAccess.setEmail(email);
            userAccess.setTelNumber(telNumber);
            userAccess.setActive(activate);

            return userAccess;

        }

        return null;

    }

    private ArrayList<Company> convertToCompanyArray(JSONObject jsonObject) throws JSONException {

        ArrayList<Company> companyArrayList = new ArrayList<Company>();

        JSONArray companyJsonArray = jsonObject.getJSONArray("company");

        for(int i=0; i<companyJsonArray.length(); i++){

            Company company = new Company();

            JSONObject companyObject = companyJsonArray.getJSONObject(i);

            int id = companyObject.getInt("id");
            String name = companyObject.getString("name");
            String address = companyObject.getString("address");
            String creationDate = (companyObject.get("creationDate").toString() != "null") ? companyObject.getString("creationDate") : "";
            String modificationDate = (companyObject.get("modificationDate").toString() != "null") ? companyObject.getString("modificationDate") : "";
            boolean activate = (companyObject.getString("activate").equals("1")) ? true : false;
            int creationUserId = (companyObject.get("creationUserId").toString() != "null") ? companyObject.getInt("creationUserId") : -1;
            int modificationUserId = (companyObject.get("modificationUserId").toString() != "null") ? companyObject.getInt("modificationUserId") : -1;
            String creationUserName = (companyObject.get("creationUserName").toString() != "null") ? companyObject.getString("creationUserName") : "";
            String modificationUserName = (companyObject.get("modificationUserName").toString() != "null") ? companyObject.getString("modificationUserName") : "";

            company.setId(id);
            company.setName(name);
            company.setAddress(address);
            company.setCreationDate(creationDate);
            company.setModificationDate(modificationDate);
            company.setActivate(activate);
            company.setCreationUserId(creationUserId);
            company.setModificationUserId(modificationUserId);
            company.setCreationUserName(creationUserName);
            company.setModificationUserName(modificationUserName);

            companyArrayList.add(company);

        }

        return companyArrayList;

    }

    private ArrayList<Project> convertToProjectArray(JSONObject jsonObject) throws JSONException {

        ArrayList<Project> projectArrayList = new ArrayList<Project>();

        JSONArray projectJsonArray = jsonObject.getJSONArray("project");

        for(int i=0; i<projectJsonArray.length(); i++){

            Project project = new Project();

            JSONObject projectObject = projectJsonArray.getJSONObject(i);

            int id = projectObject.getInt("id");
            String name = projectObject.getString("name");
            String startDate = (projectObject.get("startDate").toString() != "null") ? projectObject.getString("startDate") : "";
            String endDate = (projectObject.get("endDate").toString() != "null") ? projectObject.getString("endDate") : "";
            String creationDate = (projectObject.get("creationDate").toString() != "null") ? projectObject.getString("creationDate") : "";
            String modificationDate = (projectObject.get("modificationDate").toString() != "null") ? projectObject.getString("modificationDate") : "";
            boolean activate = (projectObject.getString("activate").equals("1")) ? true : false;
            int companyId = (projectObject.get("companyId").toString() != "null") ? projectObject.getInt("companyId") : -1;
            String companyName = (projectObject.get("companyName").toString() != "null") ? projectObject.getString("companyName") : "";
            String creationUserName = (projectObject.get("creationUserName").toString() != "null") ? projectObject.getString("creationUserName") : "";
            String modificationUserName = (projectObject.get("modificationUserName").toString() != "null") ? projectObject.getString("modificationUserName") : "";

            project.setId(id);
            project.setName(name);
            project.setStartDate(startDate);
            project.setEndDate(endDate);
            project.setCreationDate(creationDate);
            project.setModificationDate(modificationDate);
            project.setActivate(activate);
            project.setCompanyId(companyId);
            project.setCompanyName(companyName);
            project.setCreationUserName(creationUserName);
            project.setModificationUserName(modificationUserName);

            projectArrayList.add(project);

        }

        return projectArrayList;

    }

    private ArrayList<Software> convertToSoftwareArray(JSONObject jsonObject) throws JSONException {

        ArrayList<Software> softwareArrayList = new ArrayList<Software>();

        JSONArray softwareJsonArray = jsonObject.getJSONArray("software");

        for(int i=0; i<softwareJsonArray.length(); i++){

            Software software = new Software();

            JSONObject softwareObject = softwareJsonArray.getJSONObject(i);

            int id = softwareObject.getInt("id");
            String name = softwareObject.getString("name");
            String code = (softwareObject.get("code").toString() != "null") ? softwareObject.getString("code") : "";
            String type = (softwareObject.get("type").toString() != "null") ? softwareObject.getString("type") : "";
            String creationDate = (softwareObject.get("creationDate").toString() != "null") ? softwareObject.getString("creationDate") : "";
            String modificationDate = (softwareObject.get("modificationDate").toString() != "null") ? softwareObject.getString("modificationDate") : "";
            boolean activate = (softwareObject.getString("activate").equals("1")) ? true : false;
            int creationUserId = (softwareObject.get("creationUserId").toString() != "null") ? softwareObject.getInt("creationUserId") : -1;
            int modificationUserId = (softwareObject.get("modificationUserId").toString() != "null") ? softwareObject.getInt("modificationUserId") : -1;
            String creationUserName = (softwareObject.get("creationUserName").toString() != "null") ? softwareObject.getString("creationUserName") : "";
            String modificationUserName = (softwareObject.get("modificationUserName").toString() != "null") ? softwareObject.getString("modificationUserName") : "";

            software.setId(id);
            software.setName(name);
            software.setCode(code);
            software.setType(type);
            software.setCreationDate(creationDate);
            software.setModificationDate(modificationDate);
            software.setActivate(activate);
            software.setCreationUserId(creationUserId);
            software.setModificationUserId(modificationUserId);
            software.setCreationUserName(creationUserName);
            software.setModificationUserName(modificationUserName);

            softwareArrayList.add(software);

        }

        return softwareArrayList;

    }

    private ArrayList<Contact> convertToContactArray(JSONObject jsonObject) throws JSONException {

        ArrayList<Contact> contactArrayList = new ArrayList<Contact>();

        JSONArray contactJsonArray = jsonObject.getJSONArray("contact");

        for(int i=0; i<contactJsonArray.length(); i++){

            Contact contact = new Contact();

            JSONObject contactObject = contactJsonArray.getJSONObject(i);

            int id = contactObject.getInt("id");
            String firstName = (contactObject.get("firstName").toString() != "null") ? contactObject.getString("firstName") : "";
            String lastName = (contactObject.get("lastName").toString() != "null") ? contactObject.getString("lastName") : "";
            String title = (contactObject.get("title").toString() != "null") ? contactObject.getString("title") : "";
            String email = (contactObject.get("email").toString() != "null") ? contactObject.getString("email") : "";
            String telNumber = (contactObject.get("telNumber").toString() != "null") ? contactObject.getString("telNumber") : "";
            String creationDate = (contactObject.get("creationDate").toString() != "null") ? contactObject.getString("creationDate") : "";
            String modificationDate = (contactObject.get("modificationDate").toString() != "null") ? contactObject.getString("modificationDate") : "";
            boolean activate = (contactObject.getString("activate").equals("1")) ? true : false;
            int companyId = (contactObject.get("companyId").toString() != "null") ? contactObject.getInt("companyId") : -1;
            String companyName = (contactObject.get("companyName").toString() != "null") ? contactObject.getString("companyName") : "";
            int creationUserId = (contactObject.get("creationUserId").toString() != "null") ? contactObject.getInt("creationUserId") : -1;
            int modificationUserId = (contactObject.get("modificationUserId").toString() != "null") ? contactObject.getInt("modificationUserId") : -1;
            String creationUserName = (contactObject.get("creationUserName").toString() != "null") ? contactObject.getString("creationUserName") : "";
            String modificationUserName = (contactObject.get("modificationUserName").toString() != "null") ? contactObject.getString("modificationUserName") : "";

            contact.setId(id);
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setTitle(title);
            contact.setEmail(email);
            contact.setTelNumber(telNumber);
            contact.setCreationDate(creationDate);
            contact.setModificationDate(modificationDate);
            contact.setActivate(activate);
            contact.setCompanyId(companyId);
            contact.setCompanyName(companyName);
            contact.setCreationUserId(creationUserId);
            contact.setModificationUserId(modificationUserId);
            contact.setCreationUserName(creationUserName);
            contact.setModificationUserName(modificationUserName);

            contactArrayList.add(contact);

        }

        return contactArrayList;

    }

    private ArrayList<ProjectSoftware> convertToProjectSoftwareArray(JSONObject jsonObject) throws JSONException {

        ArrayList<ProjectSoftware> projectSoftwareArrayList = new ArrayList<ProjectSoftware>();

        JSONArray projectSoftwareJsonArray = jsonObject.getJSONArray("projectSoftware");

        for(int i=0; i<projectSoftwareJsonArray.length(); i++){

            ProjectSoftware projectSoftware = new ProjectSoftware();

            JSONObject projectSoftwareObject = projectSoftwareJsonArray.getJSONObject(i);

            int id = projectSoftwareObject.getInt("id");
            String name = (projectSoftwareObject.get("name").toString() != "null") ? projectSoftwareObject.getString("name") : "";
            String creationDate = (projectSoftwareObject.get("creationDate").toString() != "null") ? projectSoftwareObject.getString("creationDate") : "";
            String modificationDate = (projectSoftwareObject.get("modificationDate").toString() != "null") ? projectSoftwareObject.getString("modificationDate") : "";
            boolean activate = (projectSoftwareObject.getString("activate").equals("1")) ? true : false;
            int creationUserId = (projectSoftwareObject.get("creationUserId").toString() != "null") ? projectSoftwareObject.getInt("creationUserId") : -1;
            int modificationUserId = (projectSoftwareObject.get("modificationUserId").toString() != "null") ? projectSoftwareObject.getInt("modificationUserId") : -1;
            int projectId = (projectSoftwareObject.get("projectId").toString() != "null") ? projectSoftwareObject.getInt("projectId") : -1;
            int softwareId = (projectSoftwareObject.get("softwareId").toString() != "null") ? projectSoftwareObject.getInt("softwareId") : -1;
            String projectName = (projectSoftwareObject.get("projectName").toString() != "null") ? projectSoftwareObject.getString("projectName") : "";
            String softwareName = (projectSoftwareObject.get("softwareName").toString() != "null") ? projectSoftwareObject.getString("softwareName") : "";
            String creationUserName = (projectSoftwareObject.get("creationUserName").toString() != "null") ? projectSoftwareObject.getString("creationUserName") : "";
            String modificationUserName = (projectSoftwareObject.get("modificationUserName").toString() != "null") ? projectSoftwareObject.getString("modificationUserName") : "";

            projectSoftware.setId(id);
            projectSoftware.setName(name);
            projectSoftware.setCreationDate(creationDate);
            projectSoftware.setModificationDate(modificationDate);
            projectSoftware.setActivate(activate);
            projectSoftware.setCreationUserId(creationUserId);
            projectSoftware.setModificationUserId(modificationUserId);
            projectSoftware.setProjectId(projectId);
            projectSoftware.setSoftwareId(softwareId);
            projectSoftware.setProjectName(projectName);
            projectSoftware.setSoftwareName(softwareName);
            projectSoftware.setCreationUserName(creationUserName);
            projectSoftware.setModificationUserName(modificationUserName);

            projectSoftwareArrayList.add(projectSoftware);

        }

        return projectSoftwareArrayList;

    }

    // Reads an InputStream and converts it to a String.
    private String convertToString(InputStream stream) throws IOException {

        InputStreamReader is = new InputStreamReader(stream);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = br.readLine();

        while(read != null) {
            //System.out.println(read);
            sb.append(read);
            read = br.readLine();

        }

        return sb.toString();

    }

    private String getCurrentDateMySQL(){

        //2015-12-20 18:19:03

        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);

        return formatter.format(currentDate);

    }

}
