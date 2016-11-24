package com.phearme.cordovaplugin;

import org.apache.cordova.CallbackContext;
import android.content.ContentValues;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.net.Uri;

public class ContentProviderPlugin extends CordovaPlugin {
	private String WRONG_PARAMS = "Wrong parameters.";

	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final JSONArray methodArgs = args;
		final CallbackContext callback = callbackContext;
		
		if (action.equals("query")) {
			final JSONObject queryArgs = methodArgs.getJSONObject(0);
			if (queryArgs == null) {
				callback.error(WRONG_PARAMS);
				return false;
			}
			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					runQuery(queryArgs, callback);
				}
			});
			return true;
		}
		if (action.equals("updateRegister")) {
			final JSONObject queryArgs = methodArgs.getJSONObject(0);
			if (queryArgs == null) {
				callback.error(WRONG_PARAMS);
				return false;
			}
			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					updateRegister(queryArgs, callback);
				}
			});
			return true;
		}
		if (action.equals("updateDutyStatus")) {
			final JSONObject queryArgs = methodArgs.getJSONObject(0);
			if (queryArgs == null) {
				callback.error(WRONG_PARAMS);
				return false;
			}
			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					updateDutyStatus(queryArgs, callback);
				}
			});
			return true;
		}

		return false;
	}

	private void runQuery(JSONObject queryArgs, CallbackContext callback) {
		Uri contentUri = null;
		String[] projection = null;
		String selection = null;
		String[] selectionArgs = null;
		String sortOrder = null;
		JSONArray resultJSONArray;

		try {
			if (!queryArgs.isNull("contentUri")) {
				contentUri = Uri.parse(queryArgs.getString("contentUri"));
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		} catch (JSONException e) {
			callback.error(WRONG_PARAMS);
			return;
		}
		if (contentUri == null) {
			callback.error(WRONG_PARAMS);
			return;
		}

		try {
			if (!queryArgs.isNull("projection")) {
				JSONArray projectionJsonArray = queryArgs.getJSONArray("projection");
				projection = new String[projectionJsonArray.length()];
				for (int i = 0; i < projectionJsonArray.length(); i++) {
					projection[i] = projectionJsonArray.getString(i);
				}
			}
		} catch (JSONException e1) {
			projection = null;
		}

		try {
			if (!queryArgs.isNull("selection")) {
				selection = queryArgs.getString("selection");
			}
		} catch (JSONException e1) {
			selection = null;
		}

		try {
			if (!queryArgs.isNull("selectionArgs")) {
				JSONArray selectionArgsJsonArray = queryArgs.getJSONArray("selectionArgs");
				selectionArgs = new String[selectionArgsJsonArray.length()];
				for (int i = 0; i < selectionArgsJsonArray.length(); i++) {
					selectionArgs[i] = selectionArgsJsonArray.getString(i);
				}
			}
		} catch (JSONException e1) {
			selectionArgs = null;
		}

		try {
			if (!queryArgs.isNull("sortOrder")) {
				sortOrder = queryArgs.getString("sortOrder");
			}
		} catch (JSONException e1) {
			sortOrder = null;
		}

		// run query
		Cursor result = cordova.getActivity().getContentResolver().query(contentUri, projection, selection, selectionArgs, sortOrder);
		resultJSONArray = new JSONArray();
		while (result.moveToNext()) {
			JSONObject resultRow = new JSONObject();
			int colCount = result.getColumnCount();
			for (int i = 0; i < colCount; i++) {
				try {
					resultRow.put(result.getColumnName(i), result.getString(i));
				} catch (JSONException e) {
					resultRow = null;
				}
			}
			resultJSONArray.put(resultRow);
		}
		result.close();
		callback.success(resultJSONArray);
	}
///////////////////////////////////////////////////////////////////////////////////////////////update
	private void updateRegister(JSONObject queryArgs, CallbackContext callback) {
		Uri contentUri = null;
		String strValue = "";
		String strRegister = "";
		
		

		try {
			if (!queryArgs.isNull("contentUri")) {
				contentUri = Uri.parse(queryArgs.getString("contentUri"));
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		} catch (JSONException e) {
			callback.error(WRONG_PARAMS);
			return;
		}

		if (contentUri == null) {
			callback.error(WRONG_PARAMS);
			return;
		}
		try{
			if (!queryArgs.isNull("value")) {
				strValue = queryArgs.getString("value");
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		}catch (JSONException e) {
			strValue = " ";
		}
		try{
			if (!queryArgs.isNull("register")) {
				strRegister = queryArgs.getString("register");
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		}catch (JSONException e) {
			strRegister = " ";
		}

		ContentValues values = new ContentValues();
		values.put("value", strValue);
		// updateData

		cordova.getActivity().getContentResolver().update(contentUri, values, "register=?", new String[]{strRegister});
		JSONArray resultJSONArray = new JSONArray();

		JSONObject jo = new JSONObject();
		try{
		jo.put("return","true");
		}catch(JSONException e){
			jo = null;
		}
		resultJSONArray.put(jo);
		callback.success(resultJSONArray);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////update
	private void updateDutyStatus(JSONObject queryArgs, CallbackContext callback) {
		Uri contentUri = null;
		String carrier_id = "";
		String drv_id = "";
		String dt = "";
		String location = "";
		String status = "";
		String inactive = "";
		
		

		try {
			if (!queryArgs.isNull("contentUri")) {
				contentUri = Uri.parse(queryArgs.getString("contentUri"));
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		} catch (JSONException e) {
			callback.error(WRONG_PARAMS);
			return;
		}

		if (contentUri == null) {
			callback.error(WRONG_PARAMS);
			return;
		}
		//carrier_id
		try{
			if (!queryArgs.isNull("carrier_id")) {
				carrier_id = queryArgs.getString("carrier_id");
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		}catch (JSONException e) {
			carrier_id = " ";
		}
		//drv_id
		try{
			if (!queryArgs.isNull("drv_id")) {
				drv_id = queryArgs.getString("drv_id");
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		}catch (JSONException e) {
			drv_id = " ";
		}
		//status
		try{
			if (!queryArgs.isNull("status")) {
				status = queryArgs.getString("status");
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		}catch (JSONException e) {
			status = " ";
		}
		//dt
		try{
			if (!queryArgs.isNull("dt")) {
				dt = queryArgs.getString("dt");
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		}catch (JSONException e) {
			dt = " ";
		}
		
		//location
		try{
			if (!queryArgs.isNull("location")) {
				location = queryArgs.getString("location");
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		}catch (JSONException e) {
			location = " ";
		}
		
		//inactive
		try{
			if (!queryArgs.isNull("inactive")) {
				inactive = queryArgs.getString("inactive");
			} else {
				callback.error(WRONG_PARAMS);
				return;
			}
		}catch (JSONException e) {
			inactive = " ";
		}
		
		
		

		ContentValues values = new ContentValues();
		values.put("drv_id", drv_id);
	    values.put("carrier_id", carrier_id);
		values.put("dt", dt);
		values.put("status", status);
		values.put("inactive", inactive);
		values.put("location", location);

		// Insert Data

		cordova.getActivity().getContentResolver().insert(contentUri, values);
		JSONArray resultJSONArray = new JSONArray();

		JSONObject jo = new JSONObject();
		try{
		jo.put("return","true");
		}catch(JSONException e){
			jo = null;
		}
		resultJSONArray.put(jo);
		callback.success(resultJSONArray);
	}
}
