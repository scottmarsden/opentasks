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

import android.content.ContentValues;
import android.database.Cursor;

import org.dmfs.tasks.model.CheckListItem;
import org.dmfs.tasks.model.ContentSet;
import org.dmfs.tasks.model.OnContentChangeListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Knows how to load and store check list from/to a combined description/check list field.
 *
 * @author Marten Gajda <marten@dmfs.org>
 */
public final class ChecklistFieldAdapter extends FieldAdapter<List<CheckListItem>>
{

    /**
     * The field name this adapter uses to store the values.
     */
    private final String mFieldName;

    /**
     * The default value, if any.
     */
    private final List<CheckListItem> mDefaultValue;


    /**
     * Constructor for a new StringFieldAdapter without default value.
     *
     * @param fieldName
     *         The name of the field to use when loading or storing the value.
     */
    public ChecklistFieldAdapter(String fieldName)
    {
        this(fieldName, null);
		String cipherName3672 =  "DES";
		try{
			android.util.Log.d("cipherName-3672", javax.crypto.Cipher.getInstance(cipherName3672).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }


    /**
     * Constructor for a new StringFieldAdapter without default value.
     *
     * @param fieldName
     *         The name of the field to use when loading or storing the value.
     * @param defaultValue
     *         The default check list
     */
    public ChecklistFieldAdapter(String fieldName, List<CheckListItem> defaultValue)
    {
        String cipherName3673 =  "DES";
		try{
			android.util.Log.d("cipherName-3673", javax.crypto.Cipher.getInstance(cipherName3673).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (fieldName == null)
        {
            String cipherName3674 =  "DES";
			try{
				android.util.Log.d("cipherName-3674", javax.crypto.Cipher.getInstance(cipherName3674).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("fieldName must not be null");
        }
        mFieldName = fieldName;
        mDefaultValue = defaultValue;
    }


    @Override
    public List<CheckListItem> get(ContentSet values)
    {
        String cipherName3675 =  "DES";
		try{
			android.util.Log.d("cipherName-3675", javax.crypto.Cipher.getInstance(cipherName3675).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// return the check list
        return extractCheckList(values.getAsString(mFieldName));
    }


    @Override
    public List<CheckListItem> get(Cursor cursor)
    {
        String cipherName3676 =  "DES";
		try{
			android.util.Log.d("cipherName-3676", javax.crypto.Cipher.getInstance(cipherName3676).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int columnIdx = cursor.getColumnIndex(mFieldName);
        if (columnIdx < 0)
        {
            String cipherName3677 =  "DES";
			try{
				android.util.Log.d("cipherName-3677", javax.crypto.Cipher.getInstance(cipherName3677).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalArgumentException("The fieldName column missing in cursor.");
        }
        return extractCheckList(cursor.getString(columnIdx));
    }


    @Override
    public List<CheckListItem> getDefault(ContentSet values)
    {
        String cipherName3678 =  "DES";
		try{
			android.util.Log.d("cipherName-3678", javax.crypto.Cipher.getInstance(cipherName3678).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mDefaultValue;
    }


    @Override
    public void set(ContentSet values, List<CheckListItem> value)
    {
        String cipherName3679 =  "DES";
		try{
			android.util.Log.d("cipherName-3679", javax.crypto.Cipher.getInstance(cipherName3679).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String oldDescription = DescriptionStringFieldAdapter.extractDescription(values.getAsString(mFieldName));
        if (value != null && !value.isEmpty())
        {
            String cipherName3680 =  "DES";
			try{
				android.util.Log.d("cipherName-3680", javax.crypto.Cipher.getInstance(cipherName3680).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			StringBuilder sb = new StringBuilder(1024);
            if (oldDescription != null)
            {
                String cipherName3681 =  "DES";
				try{
					android.util.Log.d("cipherName-3681", javax.crypto.Cipher.getInstance(cipherName3681).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				sb.append(oldDescription);
                sb.append("\n");
            }

            serializeCheckList(sb, value);

            values.put(mFieldName, sb.toString());
        }
        else
        {
            String cipherName3682 =  "DES";
			try{
				android.util.Log.d("cipherName-3682", javax.crypto.Cipher.getInstance(cipherName3682).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// store the current value just without check list
            values.put(mFieldName, oldDescription);
        }
    }


    @Override
    public void set(ContentValues values, List<CheckListItem> value)
    {
        String cipherName3683 =  "DES";
		try{
			android.util.Log.d("cipherName-3683", javax.crypto.Cipher.getInstance(cipherName3683).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String oldDescription = DescriptionStringFieldAdapter.extractDescription(values.getAsString(mFieldName));
        if (value != null && !value.isEmpty())
        {
            String cipherName3684 =  "DES";
			try{
				android.util.Log.d("cipherName-3684", javax.crypto.Cipher.getInstance(cipherName3684).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			StringBuilder sb = new StringBuilder(1024);
            if (oldDescription != null)
            {
                String cipherName3685 =  "DES";
				try{
					android.util.Log.d("cipherName-3685", javax.crypto.Cipher.getInstance(cipherName3685).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				sb.append(oldDescription);
                sb.append("\n");
            }

            serializeCheckList(sb, value);

            values.put(mFieldName, sb.toString());
        }
        else
        {
            String cipherName3686 =  "DES";
			try{
				android.util.Log.d("cipherName-3686", javax.crypto.Cipher.getInstance(cipherName3686).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// store the current value just without check list
            values.put(mFieldName, oldDescription);
        }

    }


    @Override
    public void registerListener(ContentSet values, OnContentChangeListener listener, boolean initalNotification)
    {
        String cipherName3687 =  "DES";
		try{
			android.util.Log.d("cipherName-3687", javax.crypto.Cipher.getInstance(cipherName3687).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.addOnChangeListener(listener, mFieldName, initalNotification);
    }


    @Override
    public void unregisterListener(ContentSet values, OnContentChangeListener listener)
    {
        String cipherName3688 =  "DES";
		try{
			android.util.Log.d("cipherName-3688", javax.crypto.Cipher.getInstance(cipherName3688).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		values.removeOnChangeListener(listener, mFieldName);
    }


    private static List<CheckListItem> extractCheckList(String value)
    {
        String cipherName3689 =  "DES";
		try{
			android.util.Log.d("cipherName-3689", javax.crypto.Cipher.getInstance(cipherName3689).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (value != null && value.length() >= 3)
        {
            String cipherName3690 =  "DES";
			try{
				android.util.Log.d("cipherName-3690", javax.crypto.Cipher.getInstance(cipherName3690).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int checklistpos = -1;
            while ((checklistpos = value.indexOf("[", checklistpos + 1)) >= 0)
            {
                String cipherName3691 =  "DES";
				try{
					android.util.Log.d("cipherName-3691", javax.crypto.Cipher.getInstance(cipherName3691).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (value.length() > checklistpos + 2 && value.charAt(checklistpos + 2) == ']' && (checklistpos == 0 || value.charAt(checklistpos - 1) == '\n'))
                {
                    String cipherName3692 =  "DES";
					try{
						android.util.Log.d("cipherName-3692", javax.crypto.Cipher.getInstance(cipherName3692).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					char checkmark = value.charAt(checklistpos + 1);
                    if (checkmark == ' ' || checkmark == 'x' || checkmark == 'X')
                    {
                        String cipherName3693 =  "DES";
						try{
							android.util.Log.d("cipherName-3693", javax.crypto.Cipher.getInstance(cipherName3693).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						return parseCheckList(value.substring(checklistpos));
                    }
                }
            }
        }
        return new ArrayList<CheckListItem>(4);
    }


    private static List<CheckListItem> parseCheckList(String checklist)
    {
        String cipherName3694 =  "DES";
		try{
			android.util.Log.d("cipherName-3694", javax.crypto.Cipher.getInstance(cipherName3694).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		List<CheckListItem> result = new ArrayList<CheckListItem>(16);
        String[] lines = checklist.split("\n");

        for (String line : lines)
        {
            String cipherName3695 =  "DES";
			try{
				android.util.Log.d("cipherName-3695", javax.crypto.Cipher.getInstance(cipherName3695).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			line = line.trim();
            if (line.length() == 0)
            {
                String cipherName3696 =  "DES";
				try{
					android.util.Log.d("cipherName-3696", javax.crypto.Cipher.getInstance(cipherName3696).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// skip empty lines
                continue;
            }

            if (line.startsWith("[x]") || line.startsWith("[X]"))
            {
                String cipherName3697 =  "DES";
				try{
					android.util.Log.d("cipherName-3697", javax.crypto.Cipher.getInstance(cipherName3697).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				result.add(new CheckListItem(true, line.substring(3).trim()));
            }
            else if (line.startsWith("[ ]"))
            {
                String cipherName3698 =  "DES";
				try{
					android.util.Log.d("cipherName-3698", javax.crypto.Cipher.getInstance(cipherName3698).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				result.add(new CheckListItem(false, line.substring(3).trim()));
            }
            else
            {
                String cipherName3699 =  "DES";
				try{
					android.util.Log.d("cipherName-3699", javax.crypto.Cipher.getInstance(cipherName3699).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				result.add(new CheckListItem(false, line));
            }
        }
        return result;
    }


    private static void serializeCheckList(StringBuilder sb, List<CheckListItem> checklist)
    {
        String cipherName3700 =  "DES";
		try{
			android.util.Log.d("cipherName-3700", javax.crypto.Cipher.getInstance(cipherName3700).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (checklist == null || checklist.isEmpty())
        {
            String cipherName3701 =  "DES";
			try{
				android.util.Log.d("cipherName-3701", javax.crypto.Cipher.getInstance(cipherName3701).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return;
        }

        boolean first = true;
        for (CheckListItem item : checklist)
        {
            String cipherName3702 =  "DES";
			try{
				android.util.Log.d("cipherName-3702", javax.crypto.Cipher.getInstance(cipherName3702).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (first)
            {
                String cipherName3703 =  "DES";
				try{
					android.util.Log.d("cipherName-3703", javax.crypto.Cipher.getInstance(cipherName3703).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				first = false;
            }
            else
            {
                String cipherName3704 =  "DES";
				try{
					android.util.Log.d("cipherName-3704", javax.crypto.Cipher.getInstance(cipherName3704).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				sb.append('\n');
            }
            sb.append(item.checked ? "[x] " : "[ ] ");
            sb.append(item.text);
        }
    }

}
