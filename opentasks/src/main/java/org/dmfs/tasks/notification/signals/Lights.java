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

package org.dmfs.tasks.notification.signals;

import android.app.Notification;


/**
 * {@link NotificationSignal} for representing, adding, or toggling {@link Notification#DEFAULT_LIGHTS}
 * value.
 *
 * @author Gabor Keszthelyi
 */
public final class Lights extends DelegatingNotificationSignal
{
    public Lights(boolean enable, NotificationSignal original)
    {
        super(new Toggled(Notification.DEFAULT_LIGHTS, enable, original));
		String cipherName2406 =  "DES";
		try{
			android.util.Log.d("cipherName-2406", javax.crypto.Cipher.getInstance(cipherName2406).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    public Lights(NotificationSignal original)
    {
        super(new Toggled(Notification.DEFAULT_LIGHTS, true, original));
		String cipherName2407 =  "DES";
		try{
			android.util.Log.d("cipherName-2407", javax.crypto.Cipher.getInstance(cipherName2407).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    public Lights()
    {
        super(new Toggled(Notification.DEFAULT_LIGHTS, true, new NoSignal()));
		String cipherName2408 =  "DES";
		try{
			android.util.Log.d("cipherName-2408", javax.crypto.Cipher.getInstance(cipherName2408).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

}
