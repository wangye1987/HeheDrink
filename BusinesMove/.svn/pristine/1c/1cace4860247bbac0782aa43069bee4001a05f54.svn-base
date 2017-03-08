package com.heheys.ec.utils; 



import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/** 
 * @author 作者 E-mail: wk
 * @version 创建时间：2015-10-14 上午10:25:52 
 * 类说明 
 * @param
 */
public class MaxLengthWatcher implements TextWatcher {

    private int maxLen = 0;
    private EditText editText = null;
    private Context context;
    public MaxLengthWatcher(int maxLen, EditText editText,Context context) {
        this.maxLen = maxLen;
        this.editText = editText;
        this.context = context;
    }

    public void afterTextChanged(Editable arg0) {
        // TODO Auto-generated method stub

    }

    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
            int arg3) {
        // TODO Auto-generated method stub

    }

    public void onTextChanged(CharSequence arg0, int arg1, int arg2,
            int arg3) {
        // TODO Auto-generated method stub
        Editable editable = editText.getText();
        int len = editable.length();
//        tvCurrentNum.setText(len + "");
        if (len > maxLen) {
            int selEndIndex = Selection.getSelectionEnd(editable);
            String str = editable.toString();
            // 截取新字符串
            String newStr = str.substring(0, maxLen);
            editText.setText(newStr);
            editable = editText.getText();
            // 新字符串的长度
            int newLen = editable.length();
            // 旧光标位置超过字符串长度
            if (selEndIndex > newLen) {
                selEndIndex = editable.length();
            }
            // 设置新光标所在的位置
            Selection.setSelection(editable, selEndIndex);
            ToastUtil.showToast(context, "亲，输入字数超过最大限制");
        }
    }

}

 