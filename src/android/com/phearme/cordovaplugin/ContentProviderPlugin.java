/**
 * Created by User1 on 2/2/2017.
 */

public class dd {
}
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
        try {
            if (!queryArgs.isNull("value")) {
                strValue = queryArgs.getString("value");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            strValue = " ";
        }
        try {
            if (!queryArgs.isNull("register")) {
                strRegister = queryArgs.getString("register");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            strRegister = " ";
        }

        ContentValues values = new ContentValues();
        values.put("value", strValue);
        // updateData

        cordova.getActivity().getContentResolver().update(contentUri, values, "register=?", new String[]{strRegister});
        JSONArray resultJSONArray = new JSONArray();

        JSONObject jo = new JSONObject();
        try {
            jo.put("return", "true");
        } catch (JSONException e) {
            jo = null;
        }
        resultJSONArray.put(jo);
        callback.success(resultJSONArray);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////update
    private void updateInsertStatus(JSONObject queryArgs, CallbackContext callback) {

        Uri contentUri = null;
        String dateTime = " ";
        try {
            if (!queryArgs.isNull("dateTime")) {
                carrier_id = queryArgs.getString("dateTime");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            dateTime = " ";
        }

        String driverId = " ";
        try {
            if (!queryArgs.isNull("driverId")) {
                carrier_id = queryArgs.getString("driverId");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            driverId = " ";
        }


        String trailer1Id = " ";
        try {
            if (!queryArgs.isNull("trailer1Id")) {
                carrier_id = queryArgs.getString("trailer1Id");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            trailer1Id = " ";
        }
        String trailer2Id = " ";
        try {
            if (!queryArgs.isNull("trailer2Id")) {
                carrier_id = queryArgs.getString("trailer2Id");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            trailer2Id = " ";
        }

        String statusId = " ";
        try {
            if (!queryArgs.isNull("statusId")) {
                carrier_id = queryArgs.getString("statusId");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            statusId = " ";
        }
        String latitude = " ";
        try {
            if (!queryArgs.isNull("latitude")) {
                carrier_id = queryArgs.getString("latitude");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            latitude = " ";
        }
        String longitude = " ";
        try {
            if (!queryArgs.isNull("longitude")) {
                carrier_id = queryArgs.getString("longitude");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            longitude = " ";
        }

        String location = " ";
        try {
            if (!queryArgs.isNull("location")) {
                carrier_id = queryArgs.getString("location");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            location = " ";
        }
        String remarks = " ";
        try {
            if (!queryArgs.isNull("remarks")) {
                carrier_id = queryArgs.getString("remarks");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            remarks = " ";
        }
        String verified = " ";
        try {
            if (!queryArgs.isNull("verified")) {
                carrier_id = queryArgs.getString("verified");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            verified = " ";
        }
        String active = " ";
        try {
            if (!queryArgs.isNull("active")) {
                carrier_id = queryArgs.getString("active");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            active = " ";
        }
        String shipping = " ";
        try {
            if (!queryArgs.isNull("shipping")) {
                carrier_id = queryArgs.getString("shipping");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            shipping = " ";
        }
        String shipper = " ";
        try {
            if (!queryArgs.isNull("shipper")) {
                carrier_id = queryArgs.getString("shipper");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            shipper = " ";
        }
        String commodity = " ";
        try {
            if (!queryArgs.isNull("commodity")) {
                carrier_id = queryArgs.getString("commodity");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            commodity = " ";
        }
        String personalMileage = " ";
        try {
            if (!queryArgs.isNull("personalMileage")) {
                carrier_id = queryArgs.getString("personalMileage");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            personalMileage = " ";
        }
        String cdnCycle = " ";
        try {
            if (!queryArgs.isNull("cdnCycle")) {
                carrier_id = queryArgs.getString("cdnCycle");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            cdnCycle = " ";
        }
        String usaCycle = " ";
        try {
            if (!queryArgs.isNull("usaCycle")) {
                carrier_id = queryArgs.getString("usaCycle");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            usaCycle = " ";
        }
        String deferral = " ";
        try {
            if (!queryArgs.isNull("deferral")) {
                carrier_id = queryArgs.getString("deferral");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            deferral = " ";
        }
        String ctpatsealno = " ";
        try {
            if (!queryArgs.isNull("ctpatsealno")) {
                carrier_id = queryArgs.getString("ctpatsealno");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            ctpatsealno = " ";
        }
        String origin = " ";
        try {
            if (!queryArgs.isNull("origin")) {
                carrier_id = queryArgs.getString("origin");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            origin = " ";
        }
        String indication = " ";
        try {
            if (!queryArgs.isNull("indication")) {
                carrier_id = queryArgs.getString("indication");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            indication = " ";
        }
        String sensorFault = " ";
        try {
            if (!queryArgs.isNull("sensorFault")) {
                carrier_id = queryArgs.getString("sensorFault");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            sensorFault = " ";
        }


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
        // Bolean value for update or insert
        try {
            if (!queryArgs.isNull("update")) {
                update = queryArgs.getString("update");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            callback.error(WRONG_PARAMS);
            return;
        }

        ContentValues values = new ContentValues();
        values.put("dateTime", dateTime);
        values.put("driverId", driverId);
        values.put("trailer1Id", trailer1Id);
        values.put("trailer2Id", trailer2Id);
        values.put("statusId", statusId);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("location", location);
        values.put("remarks", remarks);
        values.put("verified", verified);
        values.put("active", active);
        values.put("shipping", shipping);
        values.put("shipper", shipper);
        values.put("commodity", commodity);
        values.put("personalMileage", personalMileage);
        values.put("cdnCycle", cdnCycle);
        values.put("usaCycle", usaCycle);
        values.put("deferral", deferral);
        values.put("ctpatsealno", ctpatsealno);
        values.put("origin", origin);
        values.put("indication", indication);
        values.put("sensorFault", sensorFault);
        // Insert Data

        JSONObject jo = new JSONObject();
        if (update.equals("true")) {
            // cordova.getActivity().getContentResolver().update(contentUri, values, "dt=?", new String[]{dt});
            cordova.getActivity().getContentResolver().update(contentUri, values, "dateTime= ?", new String[]{dt});
            try {
                jo.put("return", "upadte true");
            } catch (JSONException e) {
                jo = null;
            }
        } else {

            // values.put("dt", dt);
            cordova.getActivity().getContentResolver().insert(contentUri, values);
            try {
                jo.put("return", "insertion true");
            } catch (JSONException e) {
                jo = null;
            }

        }
        JSONArray resultJSONArray = new JSONArray();
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
        String update = "";


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

        // Bolean value for update or insert
        try {
            if (!queryArgs.isNull("update")) {
                update = queryArgs.getString("update");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            carrier_id = "false";
        }

        //carrier_id
        try {
            if (!queryArgs.isNull("carrier_id")) {
                carrier_id = queryArgs.getString("carrier_id");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            carrier_id = " ";
        }
        //drv_id
        try {
            if (!queryArgs.isNull("drv_id")) {
                drv_id = queryArgs.getString("drv_id");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            drv_id = " ";
        }
        //status
        try {
            if (!queryArgs.isNull("status")) {
                status = queryArgs.getString("status");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            status = " ";
        }
        //dt
        try {
            if (!queryArgs.isNull("dt")) {
                dt = queryArgs.getString("dt");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            dt = " ";
        }

        //location
        try {
            if (!queryArgs.isNull("location")) {
                location = queryArgs.getString("location");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            location = " ";
        }

        //inactive
        try {
            if (!queryArgs.isNull("inactive")) {
                inactive = queryArgs.getString("inactive");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            inactive = " ";
        }

        ContentValues values = new ContentValues();
        values.put("drv_id", drv_id);
        values.put("carrier_id", carrier_id);
        values.put("status", status);
        values.put("inactive", inactive);
        values.put("location", location);

        // Insert Data

        JSONObject jo = new JSONObject();
        if (update.equals("true")) {
            // cordova.getActivity().getContentResolver().update(contentUri, values, "dt=?", new String[]{dt});
            cordova.getActivity().getContentResolver().update(contentUri, values, "dt = ?", new String[]{dt});
            try {
                jo.put("return", "upadte true");
            } catch (JSONException e) {
                jo = null;
            }
        } else {

            values.put("dt", dt);
            cordova.getActivity().getContentResolver().insert(contentUri, values);
            try {
                jo.put("return", "insertion true");
            } catch (JSONException e) {
                jo = null;
            }

        }
        JSONArray resultJSONArray = new JSONArray();
        resultJSONArray.put(jo);
        callback.success(resultJSONArray);
    }
}
