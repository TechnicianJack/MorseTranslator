//package com.example.morsetranslator;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.res.AssetManager;
//
//import android.app.Dialog;
//import android.app.AlertDialog;
//import android.view.*;
//import android.webkit.*;
//
//import java.io.*;
//import java.nio.charset.Charset;
//
//public class HelpDialog
//{
//    private Context mContext;
//    private Dialog mDialog;
//
//    private static final String JAPANESE = "ja";
//
//
//    public HelpDialog(Context ctx)
//    {
//        mContext = ctx;
//
//        WebView webview = new WebView(ctx);
//
//        String data = getData(MorseCodeActivity.lang);
//        webview.loadData(data, "text/html", "UTF-8");
//
//        mDialog = new AlertDialog.Builder(ctx)
//            .setTitle(R.string.menu_help)
//            .setView(webview)
//            .setPositiveButton(ctx.getString(R.string.ok), 
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which)
//                        {
//                            dialog.dismiss();
//                        }
//            })
//            .create();
//    }
//
//    private String getData(String lang)
//    {
//        String filename;
//        if (lang.equals(JAPANESE)) {
//            filename = "help_ja.html";
//        } else {
//            filename = "help_en.html";
//        }
//
//        StringBuffer sb = new StringBuffer();
//        BufferedInputStream bi;
//        try {
//
//            AssetManager mgr = mContext.getAssets();
//            bi = new BufferedInputStream(mgr.open(filename));
//
//            byte[] buf = new byte[4096];
//            int size;
//            while ((size = bi.read(buf, 0, buf.length)) > 0) {
//                sb.append(new String(buf, Charset.forName("UTF-8")));
//            }
//
//            bi.close();
//
//            return sb.toString();
//
//        } catch (Exception e) {
//            return e.toString();
//        }
//    }
//
//    public void show()
//    {
//        mDialog.show();
//    }
//}
