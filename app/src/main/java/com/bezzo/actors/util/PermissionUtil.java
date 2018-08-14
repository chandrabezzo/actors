package com.bezzo.actors.util;

import android.app.*;
import android.content.*;
import android.widget.*;

import com.bezzo.actors.R;
import com.karumi.dexter.*;
import com.karumi.dexter.listener.*;
import com.karumi.dexter.listener.single.*;


/**
 * Created by bezzo on 13/02/18.
 */

public abstract class PermissionUtil {

    private Activity activity;
    private String permissionName;

    public PermissionUtil(final Activity activity, String permission, final String permissionName){
        this.activity = activity;
        this.permissionName = permissionName;

        Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        onGranted(response);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        onDenied(response);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, final PermissionToken token) {
                        String message = String.format(activity.getString(R.string.message_perizinan), permissionName);

                        new AlertDialog.Builder(activity).setTitle(R.string.perizinan)
                                .setMessage(message)
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        token.cancelPermissionRequest();
                                    }
                                })
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        token.continuePermissionRequest();
                                    }
                                })
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        token.cancelPermissionRequest();
                                    }
                                })
                                .show();
                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .check();
    }

    public abstract void onGranted(PermissionGrantedResponse response);

    public abstract void onDenied(PermissionDeniedResponse response);
}
