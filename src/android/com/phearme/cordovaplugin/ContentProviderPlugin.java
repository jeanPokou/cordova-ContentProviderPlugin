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

        if (action.equals("updateInsertStatus")) {
            final JSONObject queryArgs = methodArgs.getJSONObject(0);
            if (queryArgs == null) {
                callback.error(WRONG_PARAMS);
                return false;
            }
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    updateInsertStatus(queryArgs, callback);
                }
            });
            return true;
        }

        if (action.equals("insertMessage")) {
            final JSONObject queryArgs = methodArgs.getJSONObject(0);
            if (queryArgs == null) {
                callback.error(WRONG_PARAMS);
                return false;
            }
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                     insertMessage(queryArgs, callback);
                }
            });
            return true;
        }


        if (action.equals("deleteEntry")) {
            final JSONObject queryArgs = methodArgs.getJSONObject(0);
            if (queryArgs == null) {
                callback.error(WRONG_PARAMS);
                return false;
            }
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                     deleteEntry(queryArgs, callback);
                }
            });
            return true;
        }



        return false;
    }

    private void insertMessage (JSONObject queryArgs, CallbackContext callback) {
        Uri contentUri = null;
        String msg = "";
        String method = "";
        String priority = "";
        String dt = "";
        String partId = "";
        String created = "";
        String partTotal = "";
        String sent ="";

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


        //msg
        try {
            if (!queryArgs.isNull("msg")) {
                msg = queryArgs.getString("msg");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            msg = " ";
        }
        //method
        try {
            if (!queryArgs.isNull("method")) {
                method = queryArgs.getString("method");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
           method  = " ";
        }
        //Priority
        try {
            if (!queryArgs.isNull("priority")) {
                priority = queryArgs.getString("priority");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            priority = " ";
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

        //partId
        try {
            if (!queryArgs.isNull("partId")) {
                partId = queryArgs.getString("partId");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            partId = " ";
        }

      //partTotal
        try {
            if (!queryArgs.isNull("partTotal")) {
                partTotal = queryArgs.getString("partTotal");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            partTotal = " ";
        }


        //created
        try {
            if (!queryArgs.isNull("created")) {
                created = queryArgs.getString("created");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            created = " ";
        }

      //Sent
        try {
            if (!queryArgs.isNull("sent")) {
                sent = queryArgs.getString("sent");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            sent = " ";
        }



        ContentValues values = new ContentValues();
        values.put("msg", msg);
        values.put("method", method);
        values.put("priority", priority);
        values.put("dt", dt);
        values.put("partId", partId);
        values.put("partTotal", partTotal);
        values.put("created", created);
        values.put("sent", sent);

        // Insert Data
        JSONObject jo = new JSONObject();
            cordova.getActivity().getContentResolver().insert(contentUri, values);
            try {
                jo.put("return", "true");
            } catch (JSONException e) {
                jo = null;
            }

        JSONArray resultJSONArray = new JSONArray();
        resultJSONArray.put(jo);
        callback.success(resultJSONArray);

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
        if(result != null) {
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

        }
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
	String update = " ";
        String dateTime = " ";
        try {
            if (!queryArgs.isNull("dateTime")) {
                 dateTime= queryArgs.getString("dateTime");
            } else {
                callback.error(WRONG_PARAMS +"dateTime");
                return;
            }
        } catch (JSONException e) {
            dateTime = " ";
        }

        String driverId = " ";
        try {
            if (!queryArgs.isNull("driverId")) {
                driverId= queryArgs.getString("driverId");
            } else {
                callback.error(WRONG_PARAMS +"driverId");
                return;
            }
        } catch (JSONException e) {
            driverId = " ";
        }


        String trailer1Id = " ";
        try {
            if (!queryArgs.isNull("trailer1Id")) {
                trailer1Id= queryArgs.getString("trailer1Id");
            } else {
                callback.error(WRONG_PARAMS +"trailer1Id");
                return;
            }
        } catch (JSONException e) {
            trailer1Id = " ";
        }
        String trailer2Id = " ";
        try {
            if (!queryArgs.isNull("trailer2Id")) {
                trailer2Id= queryArgs.getString("trailer2Id");
            } else {
                callback.error(WRONG_PARAMS +"trailer2Id");
                return;
            }
        } catch (JSONException e) {
            trailer2Id = " ";
        }

        String statusId = " ";
        try {
            if (!queryArgs.isNull("statusId")) {
                statusId= queryArgs.getString("statusId");
            } else {
                callback.error(WRONG_PARAMS +"statusId");
                return;
            }
        } catch (JSONException e) {
            statusId = " ";
        }
        String latitude = " ";
        try {
            if (!queryArgs.isNull("latitude")) {
                latitude= queryArgs.getString("latitude");
            } else {
                callback.error(WRONG_PARAMS +"latitude");
                return;
            }
        } catch (JSONException e) {
            latitude = " ";
        }
        String longitude = " ";
        try {
            if (!queryArgs.isNull("longitude")) {
                longitude= queryArgs.getString("longitude");
            } else {
                callback.error(WRONG_PARAMS +"longitude");
                return;
            }
        } catch (JSONException e) {
            longitude = " ";
        }

        String location = " ";
        try {
            if (!queryArgs.isNull("location")) {
                location= queryArgs.getString("location");
            } else {
                callback.error(WRONG_PARAMS +"location");
                return;
            }
        } catch (JSONException e) {
            location = " ";
        }
        String remarks = " ";
        try {
            if (!queryArgs.isNull("remarks")) {
                remarks= queryArgs.getString("remarks");
            } else {
                callback.error(WRONG_PARAMS +"remarks");
                return;
            }
        } catch (JSONException e) {
            remarks = " ";
        }
        String verified = " ";
        try {
            if (!queryArgs.isNull("verified")) {
                verified= queryArgs.getString("verified");
            } else {
                callback.error(WRONG_PARAMS +"verified");
                return;
            }
        } catch (JSONException e) {
            verified = " ";
        }
        String active = " ";
        try {
            if (!queryArgs.isNull("active")) {
                active= queryArgs.getString("active");
            } else {
                callback.error(WRONG_PARAMS +"active");
                return;
            }
        } catch (JSONException e) {
            active = " ";
        }
        String shipping = " ";
        try {
            if (!queryArgs.isNull("shipping")) {
                shipping= queryArgs.getString("shipping");
            } else {
                callback.error(WRONG_PARAMS +"shipping");
                return;
            }
        } catch (JSONException e) {
            shipping = " ";
        }
        String shipper = " ";
        try {
            if (!queryArgs.isNull("shipper")) {
                shipper= queryArgs.getString("shipper");
            } else {
                callback.error(WRONG_PARAMS +"shipper");
                return;
            }
        } catch (JSONException e) {
            shipper = " ";
        }
        String commodity = " ";
        try {
            if (!queryArgs.isNull("commodity")) {
                commodity= queryArgs.getString("commodity");
            } else {
                callback.error(WRONG_PARAMS +"commodity");
                return;
            }
        } catch (JSONException e) {
            commodity = " ";
        }
        String personalMileage = " ";
        try {
            if (!queryArgs.isNull("personalMileage")) {
                personalMileage= queryArgs.getString("personalMileage");
            } else {
                callback.error(WRONG_PARAMS +"personalMileage");
                return;
            }
        } catch (JSONException e) {
            personalMileage = " ";
        }
        String cdnCycle = " ";
        try {
            if (!queryArgs.isNull("cdnCycle")) {
                cdnCycle= queryArgs.getString("cdnCycle");
            } else {
                callback.error(WRONG_PARAMS +"cdnCycle");
                return;
            }
        } catch (JSONException e) {
            cdnCycle = " ";
        }
        String usaCycle = " ";
        try {
            if (!queryArgs.isNull("usaCycle")) {
                usaCycle= queryArgs.getString("usaCycle");
            } else {
                callback.error(WRONG_PARAMS +"usaCycle");
                return;
            }
        } catch (JSONException e) {
            usaCycle = " ";
        }
        String deferral = " ";
        try {
            if (!queryArgs.isNull("deferral")) {
                deferral= queryArgs.getString("deferral");
            } else {
                callback.error(WRONG_PARAMS +"deferral");
                return;
            }
        } catch (JSONException e) {
            deferral = " ";
        }
        String ctpatsealno = " ";
        try {
            if (!queryArgs.isNull("ctpatsealno")) {
                ctpatsealno= queryArgs.getString("ctpatsealno");
            } else {
                callback.error(WRONG_PARAMS +"ctpatsealno");
                return;
            }
        } catch (JSONException e) {
            ctpatsealno = " ";
        }
        String origin = " ";
        try {
            if (!queryArgs.isNull("origin")) {
                origin= queryArgs.getString("origin");
            } else {
                callback.error(WRONG_PARAMS +"origin");
                return;
            }
        } catch (JSONException e) {
            origin = " ";
        }
        String indication = " ";
        try {
            if (!queryArgs.isNull("indication")) {
                indication= queryArgs.getString("indication");
            } else {
                callback.error(WRONG_PARAMS +"indication");
                return;
            }
        } catch (JSONException e) {
            indication = " ";
        }
        String sensorFault = " ";
        try {
            if (!queryArgs.isNull("sensorFault")) {
                sensorFault= queryArgs.getString("sensorFault");
            } else {
                callback.error(WRONG_PARAMS +"sensorFault");
                return;
            }
        } catch (JSONException e) {
            sensorFault = " ";
        }


        try {
            if (!queryArgs.isNull("contentUri")) {
                contentUri = Uri.parse(queryArgs.getString("contentUri"));
            } else {
                callback.error(WRONG_PARAMS +"contentUri");
                return;
            }
        } catch (JSONException e) {
            callback.error(WRONG_PARAMS);
            return;
        }

        if (contentUri == null) {
            callback.error(WRONG_PARAMS + " uri is null");
            return;
        }
        // Bolean value for update or insert
        try {
            if (!queryArgs.isNull("update")) {
                update = queryArgs.getString("update");
            } else {
                callback.error(WRONG_PARAMS +"update");
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
        // if (update.equals("true")) {
        //     // cordova.getActivity().getContentResolver().update(contentUri, values, "dt=?", new String[]{dt});
        //     cordova.getActivity().getContentResolver().update(contentUri, values, "dateTime= ?", new String[]{dateTime});
        //     try {
        //         jo.put("return", "upadte true");
        //     } catch (JSONException e) {
        //         jo = null;
        //     }
        // } else {

            // values.put("dt", dt);
            cordova.getActivity().getContentResolver().insert(contentUri, values);
            try {
                jo.put("return", "insertion true");
            } catch (JSONException e) {
                jo = null;
            }

        // }
        JSONArray resultJSONArray = new JSONArray();
        resultJSONArray.put(jo);
        callback.success(resultJSONArray);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////update
    private void deleteEntry  (JSONObject queryArgs, CallbackContext callback) {

        Uri contentUri = null;
        String id= "";

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

        //id
        try {
            if (!queryArgs.isNull("id")) {
                id = queryArgs.getString("id");
            } else {
                callback.error(WRONG_PARAMS);
                return;
            }
        } catch (JSONException e) {
            id = " ";
        }

        JSONObject jo = new JSONObject();
        ContentValues values = new ContentValues();
        values.put("id", id);
        cordova.getActivity().getContentResolver().delete(contentUri,  "id = ?", new String[]{id});
        try {
            jo.put("return", "delete true");
        } catch (JSONException e) {
            jo = null;
        }




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
