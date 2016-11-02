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
}
