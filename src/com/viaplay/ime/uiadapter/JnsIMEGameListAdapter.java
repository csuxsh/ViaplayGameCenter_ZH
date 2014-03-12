package com.viaplay.ime.uiadapter;

import java.io.File;
import java.util.Locale;

import com.viaplay.ime.R;
import com.viaplay.ime.JnsIMECoreService;
import com.viaplay.ime.JnsIMEGameListActivity;
import com.viaplay.ime.JnsIMEKeyMappingActivity;
import com.viaplay.ime.util.GameInfo;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ?��??????��?��??����D������?����???��
 * 
 * @author Steven.xu
 *
 */
public class JnsIMEGameListAdapter extends BaseAdapter implements OnClickListener{

	final static String TAG = "JNS_GAME_ADAPTER";
	private Activity activity;
	private LayoutInflater inflater;
	private Cursor cursor;
	private ImageView icon;
	private TextView title;
	private ImageButton  get;
	private ImageButton play;
	private ImageButton  keymapping;
	private ImageButton  delete;
	private PackageManager pm;

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}


	public JnsIMEGameListAdapter(Cursor cursor, Activity activity)
	{
		this.cursor = cursor;
		this.activity = activity;
		inflater = activity.getLayoutInflater();
		pm = activity.getPackageManager();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cursor.getCount();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		cursor.moveToPosition(arg0);
		return cursor;

	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("UseValueOf")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.game_list_item, parent, false);
		}
		if(!cursor.moveToPosition(position))
			Log.d(TAG, "cursor erro at postion"+position);
		GameInfo game = new GameInfo();
		game.setPkgname(cursor.getString(cursor.getColumnIndex("_name")));
		game.setExists(new Boolean(cursor.getString(cursor.getColumnIndex("_exists"))));
		if(Locale.getDefault().getLanguage().startsWith("zh"))
		{	
			game.setUrl(cursor.getString(cursor.getColumnIndex("_url")));
			if(game.getUrl() == null || game.getUrl().equals(""))
				game.setUrl("https://play.google.com/store/apps/details?id="+game.getPkgname());

		}
		else
		{	
			Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+game.getPkgname());  
			if(game.getPkgname().equals("com.androidemu.n64"))
				uri = Uri.parse("http://slideme.org/application/n64oid");
			else if(game.getPkgname().equals("fr.mydedibox.afba"))
				uri = Uri.parse("http://forum.xda-developers.com/showthread.php?t=1932280");
			else if(game.getPkgname().equals("com.joyemu.fbaapp"))
				uri = Uri.parse("http://hi.baidu.com/tofro/item/c1dde9d837b2214efb5768c3");
			else if(game.getPkgname().equals("com.kawaks"))
				uri = Uri.parse("http://www.kawaks.net/");
			else if(game.getPkgname().equals("com.eamobile.tetris_eu"))
				uri = Uri.parse("http://www.1mobile.com/com-ea-tetrisfree-na-350822.html");
			else if(game.getPkgname().equals("com.bistudio.at"))
				uri = Uri.parse("http://samsungapps.sina.cn/topApps/topAppsDetail.as?productId=000000684336");
			else if(game.getPkgname().equals("com.tiger.game.arcade2"))
				uri = Uri.parse("http://slideme.org/application/tiger-arcade");
			else if(game.getPkgname().equals("com.retrobomb.expendablerearmed"))
				uri = Uri.parse("http://android.mob.org/download/a3303.html");

			game.setUrl(uri+"");
		}
		if(Locale.getDefault().getLanguage().startsWith("zh"))
			game.setLable(cursor.getString(cursor.getColumnIndex("_lable_zh")));
		else
			game.setLable(cursor.getString(cursor.getColumnIndex("_lable")));
		if(game.getLable() == null || game.getLable().equals(""))
			game.setLable(cursor.getString(cursor.getColumnIndex("_lable")));
		Drawable icon_pic = null;
		String lable= "";
		try {
			icon_pic = pm.getApplicationIcon(game.getPkgname());
			lable =  (String) pm.getApplicationLabel(pm.getApplicationInfo(game.getPkgname(), PackageManager.GET_UNINSTALLED_PACKAGES));
			game.setExists(true);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			icon_pic = Drawable.createFromPath("mnt/sdcard/viaplay/app_icon/"+game.getPkgname()+".icon");
			lable = game.getLable();//cursor.getString(cursor.getColumnIndex("_description"));
			game.setExists(false);
			e.printStackTrace();
		}

		icon = (ImageView) convertView.findViewById(R.id.img);
		title = (TextView) convertView.findViewById(R.id.title);
		get = (ImageButton) convertView.findViewById(R.id.get);
		play = (ImageButton) convertView.findViewById(R.id.play);
		keymapping = (ImageButton) convertView.findViewById(R.id.keymapping);
		delete = (ImageButton) convertView.findViewById(R.id.delete);
		get.setOnClickListener(this);
		keymapping.setOnClickListener(this);
		delete.setOnClickListener(this);
		play.setOnClickListener(this);

		if(game.isExists())
		{	
			get.setBackgroundResource(R.drawable.get_p);
			play.setImageResource(R.drawable.play);
			keymapping.setBackgroundResource(R.drawable.game_mapping);
			get.setOnClickListener(null);
		}
		else
		{
			get.setBackgroundResource(R.drawable.game_get);
			play.setImageResource(R.drawable.game_play_p);
			keymapping.setBackgroundResource(R.drawable.game_mapping_p);
			keymapping.setOnClickListener(null);
			play.setOnClickListener(null);
		}
		icon.setImageDrawable(icon_pic);
		title.setText(lable);

		icon.setTag(game.getPkgname());
		title.setTag(game.getPkgname());
		get.setTag(game.getUrl());
		keymapping.setTag(game.getPkgname());
		delete.setTag(game.getPkgname());
		play.setTag(game.getPkgname());

		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		PackageManager pm = activity.getPackageManager();

		switch(v.getId())
		{
		case R.id.get:
			Uri uri = Uri.parse((String) v.getTag());  
			/*
			Log.d(TAG, "click get of "+ v.getTag());
			Uri uri = Uri.parse("market://details?id="+v.getTag());  
			if(v.getTag().equals("com.androidemu.n64"))
				uri = Uri.parse("http://slideme.org/application/n64oid");
			else if(v.getTag().equals("fr.mydedibox.afba"))
				uri = Uri.parse("http://forum.xda-developers.com/showthread.php?t=1932280");
			else if(v.getTag().equals("com.joyemu.fbaapp"))
				uri = Uri.parse("http://hi.baidu.com/tofro/item/c1dde9d837b2214efb5768c3");
			else if(v.getTag().equals("com.kawaks"))
				uri = Uri.parse("http://www.kawaks.net/");
			else if(v.getTag().equals("com.eamobile.tetris_eu"))
				uri = Uri.parse("http://www.1mobile.com/com-ea-tetrisfree-na-350822.html");
			else if(v.getTag().equals("com.bistudio.at"))
				uri = Uri.parse("http://samsungapps.sina.cn/topApps/topAppsDetail.as?productId=000000684336");
			else if(v.getTag().equals("com.tiger.game.arcade2"))
				uri = Uri.parse("http://slideme.org/application/tiger-arcade");
			else if(v.getTag().equals("com.retrobomb.expendablerearmed"))
				uri = Uri.parse("http://android.mob.org/download/a3303.html");
			 */
			Intent it = new Intent(Intent.ACTION_VIEW, uri);   
			activity.startActivity(it);   
			break;
		case R.id.keymapping:
			Log.d(TAG, "click keymapping of "+ v.getTag());
			String lable="";
			try {
				lable = (String) pm.getApplicationLabel(pm.getApplicationInfo(""+v.getTag(), PackageManager.GET_UNINSTALLED_PACKAGES));
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent mapin = new Intent();
			mapin.setClass(activity, JnsIMEKeyMappingActivity.class);
			mapin.putExtra("filename",  v.getTag()+".keymap");
			mapin.putExtra("lable", lable);
			activity.startActivity(mapin);
			break;
		case R.id.delete:
			Log.d(TAG, "delte keymapping of "+ v.getTag());
			JnsIMECoreService.aph.delete(""+v.getTag());
			File touchmapping = new File(activity.getFilesDir()+"/"+v.getTag());
			touchmapping.delete();
			File keymapping = new File(activity.getFilesDir()+"/"+v.getTag()+".keymap");
			keymapping.delete();
			Cursor cursor = JnsIMECoreService.aph.Qurey(null, "F");
			JnsIMEGameListActivity.gameAdapter.setCursor(cursor);
			JnsIMEGameListActivity.gameAdapter.notifyDataSetChanged();
			break;
		case R.id.play:
			Intent in = new Intent(pm.getLaunchIntentForPackage(""+v.getTag()));
			activity.startActivity(in);
			break;
		}
	}

}
