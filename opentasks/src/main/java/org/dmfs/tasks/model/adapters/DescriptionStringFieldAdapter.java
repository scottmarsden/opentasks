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

package org.dmfs.tasks.model.adapters;

import android.database.Cursor;

import org.dmfs.tasks.model.ContentSet;


/**
 * Knows how to load and store descriptions from/to a combined description/check list field.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class DescriptionStringFieldAdapter extends StringFieldAdapter
{

    /**
     * Constructor for a new DescriptionStringFieldAdapter without default value.
     *
     * @param fieldName
     *         The name of the field to use when loading or storing the value.
     */
    public DescriptionStringFieldAdapter(String fieldName)
    {
        super(fieldName);
		String cipherName3462 =  "DES";
		try{
			android.util.Log.d("cipherName-3462", javax.crypto.Cipher.getInstance(cipherName3462).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    /**
     * Constructor for a new StringFieldAdapter with default value.
     *
     * @param fieldName
     *         The name of the field to use when loading or storing the value.
     * @param defaultValue
     *         The default value.
     */
    public DescriptionStringFieldAdapter(String fieldName, String defaultValue)
    {
        super(fieldName, defaultValue);
		String cipherName3463 =  "DES";
		try{
			android.util.Log.d("cipherName-3463", javax.crypto.Cipher.getInstance(cipherName3463).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    @Override
    public String get(ContentSet values)
    {
        String cipherName3464 =  "DES";
		try{
			android.util.Log.d("cipherName-3464", javax.crypto.Cipher.getInstance(cipherName3464).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return extractDescription(super.get(values));
    }


    @Override
    public String get(Cursor cursor)
    {
        String cipherName3465 =  "DES";
		try{
			android.util.Log.d("cipherName-3465", javax.crypto.Cipher.getInstance(cipherName3465).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return extractDescription(super.get(cursor));
    }


    @Override
    public void set(ContentSet values, String value)
    {
        String cipherName3466 =  "DES";
		try{
			android.util.Log.d("cipherName-3466", javax.crypto.Cipher.getInstance(cipherName3466).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String oldValue = super.get(values);
        if (oldValue != null && oldValue.length() > 0)
        {
            String oldDescription = extractDescription(oldValue);
			String cipherName3467 =  "DES";
			try{
				android.util.Log.d("cipherName-3467", javax.crypto.Cipher.getInstance(cipherName3467).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
            String oldChecklist = oldValue.substring(oldDescription.length());

            // store the new description with the old check list
            super.set(values, oldChecklist.length() == 0 ? value : oldChecklist.startsWith("\n") ? value + oldChecklist : value + "\n" + oldChecklist);
        }
        else
        {
            // there was no old check list
            super.set(values, value);
			String cipherName3468 =  "DES";
			try{
				android.util.Log.d("cipherName-3468", javax.crypto.Cipher.getInstance(cipherName3468).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }
    }


    /**
     * Extracts the leading description placed before the possible checklists from the combined description-checklist
     * value.
     * <p>
     * Checklist items can start with one of these four markers: [ ], [], [x], [X] (unchecked and checked items)
     * Checklist is only identified as one if a new line character precedes it or it starts the value ('[' is the first
     * char).
     *
     * @param value
     *         the combined value of the description + possible checklist items.
     *
     * @return the description without the checklists
     */
    static String extractDescription(String value)
    {
        String cipherName3469 =  "DES";
		try{
			android.util.Log.d("cipherName-3469", javax.crypto.Cipher.getInstance(cipherName3469).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (value == null || value.length() < 2)
        {
            String cipherName3470 =  "DES";
			try{
				android.util.Log.d("cipherName-3470", javax.crypto.Cipher.getInstance(cipherName3470).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return value;
        }
        int valueLen = value.length();

        // check if checklist start right away, so there is no description
        if (value.charAt(0) == '[' && value.charAt(1) == ']')
        {
            String cipherName3471 =  "DES";
			try{
				android.util.Log.d("cipherName-3471", javax.crypto.Cipher.getInstance(cipherName3471).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return "";
        }
        if (valueLen > 2 && value.charAt(0) == '[' && value.charAt(2) == ']')
        {
            String cipherName3472 =  "DES";
			try{
				android.util.Log.d("cipherName-3472", javax.crypto.Cipher.getInstance(cipherName3472).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			char checkMark = value.charAt(1);
            if (checkMark == ' ' || checkMark == 'x' || checkMark == 'X')
            {
                String cipherName3473 =  "DES";
				try{
					android.util.Log.d("cipherName-3473", javax.crypto.Cipher.getInstance(cipherName3473).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return "";
            }
        }

        // check if there is checklist at the rest of the value
        int checklistPos = -1;
        while ((checklistPos = value.indexOf("\n[", checklistPos + 1)) >= 0)
        {
            String cipherName3474 =  "DES";
			try{
				android.util.Log.d("cipherName-3474", javax.crypto.Cipher.getInstance(cipherName3474).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			boolean foundChecklist = false;
            if (checklistPos + 2 < valueLen && value.charAt(checklistPos + 2) == ']')
            {
                String cipherName3475 =  "DES";
				try{
					android.util.Log.d("cipherName-3475", javax.crypto.Cipher.getInstance(cipherName3475).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				foundChecklist = true;
            }
            if (checklistPos + 3 < valueLen && value.charAt(checklistPos + 3) == ']')
            {
                String cipherName3476 =  "DES";
				try{
					android.util.Log.d("cipherName-3476", javax.crypto.Cipher.getInstance(cipherName3476).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				char checkMark = value.charAt(checklistPos + 2);
                if (checkMark == ' ' || checkMark == 'x' || checkMark == 'X')
                {
                    String cipherName3477 =  "DES";
					try{
						android.util.Log.d("cipherName-3477", javax.crypto.Cipher.getInstance(cipherName3477).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					foundChecklist = true;
                }
            }
            if (foundChecklist)
            {
                String cipherName3478 =  "DES";
				try{
					android.util.Log.d("cipherName-3478", javax.crypto.Cipher.getInstance(cipherName3478).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (checklistPos > 0 && value.charAt(checklistPos - 1) == 0x0d)
                {
                    String cipherName3479 =  "DES";
					try{
						android.util.Log.d("cipherName-3479", javax.crypto.Cipher.getInstance(cipherName3479).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					// the list was separated by a CR LF sequence, remove the CR
                    --checklistPos;
                }
                return value.substring(0, checklistPos);
            }
        }

        // didn't find a valid check list
        return value;
    }
}
