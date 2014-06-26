//package com.example.morsetranslator;
//
//import android.content.Context;
//import android.content.DialogInterface;
//
//import android.app.Dialog;
//import android.app.AlertDialog;
//import android.view.*;
//import android.widget.*;
//
//
//public class ThemeDialog
//{
//    private Context mContext;
//    private Dialog mDialog;
//
//    private int[] colors = 
//        new int[]{
//            R.drawable.ginza_line,
//            R.drawable.marunouchi_line,
//            R.drawable.hibiya_line,
//            R.drawable.tozai_line,
//            R.drawable.chiyoda_line,
//            R.drawable.yurakucho_line,
//            R.drawable.hanzomon_line,
//            R.drawable.namboku_line,
//            R.drawable.fukutoshin_line,
//            R.drawable.asakusa_line,
//            R.drawable.mita_line,
//            R.drawable.shinjuku_line,
//            R.drawable.oedo_line,
//            R.drawable.midosuji_line,
//            R.drawable.tanimachi_line,
//            R.drawable.yotsubashi_line,
//            R.drawable.chuo_line,
//            R.drawable.sennichimae_line,
//            R.drawable.sakaisuji_line,
//            R.drawable.nagahoritsurumi_ryokuchi_line,
//            R.drawable.imazatosuji_line,
//            R.drawable.nanko_port_town_line,
//        };
//
//    private int[] color_names = 
//        new int[] {
//            R.string.ginza_line,
//            R.string.marunouchi_line,
//            R.string.hibiya_line,
//            R.string.tozai_line,
//            R.string.chiyoda_line,
//            R.string.yurakucho_line,
//            R.string.hanzomon_line,
//            R.string.namboku_line,
//            R.string.fukutoshin_line,
//            R.string.asakusa_line,
//            R.string.mita_line,
//            R.string.shinjuku_line,
//            R.string.oedo_line,
//            R.string.midosuji_line,
//            R.string.tanimachi_line,
//            R.string.yotsubashi_line,
//            R.string.chuo_line,
//            R.string.sennichimae_line,
//            R.string.sakaisuji_line,
//            R.string.nagahoritsurumi_ryokuchi_line,
//            R.string.imazatosuji_line,
//            R.string.nanko_port_town_line,
//    };
//
//    public ThemeDialog(Context ctx)
//    {
//        mContext = ctx;
//
//
//        mDialog = new AlertDialog.Builder(ctx)
//            .setTitle(R.string.menu_theme)
//            .setAdapter(new ThemeColorAdapter(colors, color_names), 
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            ((MorseCodeActivity)ThemeDialog.this.mContext).setThemeColor(colors[which]);
//                        }
//                    })
//            .create();
//    }
//
//    private class ThemeColorAdapter extends BaseAdapter
//    {
//        private int[] colors;
//        private int[] color_names;
//
//        public ThemeColorAdapter(int[] colors, int[] color_names)
//        {
//            this.colors = colors;
//            this.color_names = color_names;
//        }
//
//        public int getCount()
//        {
//            return colors.length;
//        }
//
//        public Object getItem(int pos)
//        {
//            return null;
//        }
//
//        public long getItemId(int pos)
//        {
//            return 0;
//        }
//
//        private class ViewHolder {
//            View color_view;
//            TextView color_name_view;
//        }
//
//        public View getView(int pos, View view, ViewGroup parent)
//        {
//            if (view == null) {
//
//                view = getItemView();
//
//                ViewHolder holder = new ViewHolder();
//
//                holder.color_view = view.findViewById(R.id.color_view);
//                holder.color_name_view = (TextView)(view.findViewById(R.id.color_name_view));
//
//                view.setTag(holder);
//            }
//
//            ViewHolder tag = (ViewHolder)(view.getTag());
//            tag.color_view.setBackgroundResource(colors[pos]);
//            tag.color_name_view.setText(color_names[pos]);
//
//
//            return view;
//
//        }
//    }
//
//    private View getItemView()
//    {
//        LayoutInflater inflater = (LayoutInflater)
//            ThemeDialog.this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        return inflater.inflate(R.layout.menu_theme_item, null);
//    }
//
//    public void show()
//    {
//        mDialog.show();
//    }
//}