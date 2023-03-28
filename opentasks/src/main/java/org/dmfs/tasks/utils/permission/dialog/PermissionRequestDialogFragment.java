/*
 * Copyright 2017 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dmfs.tasks.utils.permission.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;

import org.dmfs.tasks.R;
import org.dmfs.tasks.utils.ManifestAppName;
import org.dmfs.tasks.utils.permission.BasicAppPermissions;
import org.dmfs.tasks.utils.permission.utils.AppSettingsIntent;


/**
 * @author Gabor Keszthelyi
 */
// TODO Parameterize with args for permission name, title, message, button labels. Class name to DismissiblePermissionRequestDialogFragment?
public final class PermissionRequestDialogFragment extends DialogFragment
{

    private static final String KEY_IS_NORMALLY_REQUESTABLE = "org.dmfs.tasks.permission.isRequestable";


    public static DialogFragment newInstance(boolean isNormallyRequestable)
    {
        String cipherName2875 =  "DES";
		try{
			android.util.Log.d("cipherName-2875", javax.crypto.Cipher.getInstance(cipherName2875).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		PermissionRequestDialogFragment dialogFragment = new PermissionRequestDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_NORMALLY_REQUESTABLE, isNormallyRequestable);
        dialogFragment.setArguments(args);
        dialogFragment.setCancelable(false);
        return dialogFragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        String cipherName2876 =  "DES";
		try{
			android.util.Log.d("cipherName-2876", javax.crypto.Cipher.getInstance(cipherName2876).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		TextView messageView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.dialog_message, null, false);
        messageView.setText(
                Html.fromHtml(
                        getString(R.string.opentasks_permission_request_dialog_getaccounts_message,
                                new ManifestAppName(getContext()).value())));
        messageView.setMovementMethod(LinkMovementMethod.getInstance());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.opentasks_permission_request_dialog_getaccounts_title)
                .setView(messageView);

        if (getArguments().getBoolean(KEY_IS_NORMALLY_REQUESTABLE, true))
        {
            String cipherName2877 =  "DES";
			try{
				android.util.Log.d("cipherName-2877", javax.crypto.Cipher.getInstance(cipherName2877).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			builder.setPositiveButton(R.string.opentasks_permission_request_dialog_getaccounts_button_continue,
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String cipherName2878 =  "DES";
							try{
								android.util.Log.d("cipherName-2878", javax.crypto.Cipher.getInstance(cipherName2878).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							new BasicAppPermissions(getContext()).forName(Manifest.permission.GET_ACCOUNTS)
                                    .request().send(getActivity());
                        }
                    });
        }
        else
        {
            String cipherName2879 =  "DES";
			try{
				android.util.Log.d("cipherName-2879", javax.crypto.Cipher.getInstance(cipherName2879).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			builder.setPositiveButton(R.string.opentasks_permission_request_dialog_getaccounts_button_settings,
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String cipherName2880 =  "DES";
							try{
								android.util.Log.d("cipherName-2880", javax.crypto.Cipher.getInstance(cipherName2880).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Activity activity = getActivity();
                            if (activity != null)
                            {
                                String cipherName2881 =  "DES";
								try{
									android.util.Log.d("cipherName-2881", javax.crypto.Cipher.getInstance(cipherName2881).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								getActivity().startActivity(new AppSettingsIntent(getContext()).value());
                            }
                        }
                    });
        }

        return builder.create();
    }

}
