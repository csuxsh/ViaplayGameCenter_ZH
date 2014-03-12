package com.viaplay.ime;


import java.util.Locale;

import com.viaplay.ime.R;
import com.viaplay.ime.uiadapter.JnsIMEGameListAdapter;
import com.viaplay.ime.uiadapter.JnsIMEPopAddAdapter;
import com.viaplay.ime.util.AppHelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

/**
 * ���������Ϸ�б�activity
 * 
 * @author Steven
 *
 */

public class JnsIMEGameListActivity extends Activity{ 

	private JnsIMEPopAddAdapter popAdapter; 
	public static JnsIMEGameListAdapter gameAdapter;
	private ListView gameList;
	Dialog adddialog;
	private  ImageView defautCb[] = new ImageView[4];
	private  Button defaultD[] = new Button[4];
	private  Button defaultMap[] = new Button[4];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);
		gameList = (ListView) this.findViewById(R.id.gamelist);
		Button add = (Button) this.findViewById(R.id.add_game);
		defautCb[0] = (ImageView) this.findViewById(R.id.dian_c1);
		defautCb[1] = (ImageView) this.findViewById(R.id.dian_c2);
		defautCb[2] = (ImageView) this.findViewById(R.id.dian_c3);
		defautCb[3] = (ImageView) this.findViewById(R.id.dian_c4);
		defaultD[0] = (Button) this.findViewById(R.id.dian1);
		defaultD[1] = (Button) this.findViewById(R.id.dian2);
		defaultD[2] = (Button) this.findViewById(R.id.dian3);
		defaultD[3] = (Button) this.findViewById(R.id.dian4);
		defaultMap[0] = (Button) this.findViewById(R.id.default_keymapping1);
		defaultMap[1] = (Button) this.findViewById(R.id.default_keymapping2);
		defaultMap[2] = (Button) this.findViewById(R.id.default_keymapping3);
		defaultMap[3] = (Button) this.findViewById(R.id.default_keymapping4);

		showGameList(gameList);
		chageDefaultCheckBox(JnsIMECoreService.currentDeaultIndex);
		add.setOnClickListener(ocl);
		defaultD[0].setOnClickListener(ocl);
		defaultD[1].setOnClickListener(ocl);
		defaultD[2].setOnClickListener(ocl);
		defaultD[3].setOnClickListener(ocl);
		defaultMap[0].setOnClickListener(ocl);
		defaultMap[1].setOnClickListener(ocl);
		defaultMap[2].setOnClickListener(ocl);
		defaultMap[3].setOnClickListener(ocl);
		JnsIMECoreService.activitys.add(this);
	}

	private void chageDefaultCheckBox(int index)
	{
		defautCb[0].setVisibility(View.GONE);
		defautCb[1].setVisibility(View.GONE);
		defautCb[2].setVisibility(View.GONE);
		defautCb[3].setVisibility(View.GONE);
		defautCb[index].setVisibility(View.VISIBLE);
	}
	private void showGameList(ListView lv)
	{
		if(JnsIMECoreService.aph== null)
			JnsIMECoreService.aph = new AppHelper(this);
		AppHelper aph = JnsIMECoreService.aph;
		Cursor cursor = aph.Qurey(null,"F");
		gameAdapter = new JnsIMEGameListAdapter(cursor, this);
		lv.setAdapter(gameAdapter);
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ListView listView = (ListView)arg0; 
				Cursor cursor =  (Cursor) listView.getItemAtPosition(arg2); 
				PackageManager pm = JnsIMEGameListActivity.this.getPackageManager();
				try{
					Intent in = new Intent(pm.getLaunchIntentForPackage(cursor.getString(cursor.getColumnIndex("_name"))));
					JnsIMEGameListActivity.this.startActivity(in);
				}
				catch(Exception e)
				{
					Uri uri = null;
					if(Locale.getDefault().getLanguage().startsWith("zh"))
					{	
						if(cursor.getString(cursor.getColumnIndex("_url")) != null ||
								!cursor.getString(cursor.getColumnIndex("_url")).equals(""))
							uri = Uri.parse(cursor.getString(cursor.getColumnIndex("_url")));
						else
							Uri.parse("market://details?id="+cursor.getString(cursor.getColumnIndex("_name")));  
					}
					else
					{	
						uri = Uri.parse("https://play.google.com/store/apps/details?id="+cursor.getString(cursor.getColumnIndex("_name")));  
						if(cursor.getString(cursor.getColumnIndex("_name")).equals("com.androidemu.n64"))
							uri = Uri.parse("http://slideme.org/application/n64oid");
						else if(cursor.getString(cursor.getColumnIndex("_name")).equals("fr.mydedibox.afba"))
							uri = Uri.parse("http://forum.xda-developers.com/showthread.php?t=1932280");
						else if(cursor.getString(cursor.getColumnIndex("_name")).equals("com.joyemu.fbaapp"))
							uri = Uri.parse("http://hi.baidu.com/tofro/item/c1dde9d837b2214efb5768c3");
						else if(cursor.getString(cursor.getColumnIndex("_name")).equals("com.kawaks"))
							uri = Uri.parse("http://www.kawaks.net/");
						else if(cursor.getString(cursor.getColumnIndex("_name")).equals("com.eamobile.tetris_eu"))
							uri = Uri.parse("http://www.1mobile.com/com-ea-tetrisfree-na-350822.html");
						else if(cursor.getString(cursor.getColumnIndex("_name")).equals("com.bistudio.at"))
							uri = Uri.parse("http://samsungapps.sina.cn/topApps/topAppsDetail.as?productId=000000684336");
						else if(cursor.getString(cursor.getColumnIndex("_name")).equals("com.tiger.game.arcade2"))
							uri = Uri.parse("http://slideme.org/application/tiger-arcade");
						else if(cursor.getString(cursor.getColumnIndex("_name")).equals("com.retrobomb.expendablerearmed"))
							uri = Uri.parse("http://android.mob.org/download/a3303.html");
					}
					Intent it = new Intent(Intent.ACTION_VIEW, uri);   
					JnsIMEGameListActivity.this.startActivity(it);   
				}
			}

		});
	}

	OnClickListener ocl = new OnClickListener()
	{
		private void startKeyMapping(String name)
		{
			String lable = name;
			Intent mapin = new Intent();
			mapin.setClass(JnsIMEGameListActivity.this, JnsIMEKeyMappingActivity.class);
			mapin.putExtra("filename", name+".keymap");
			mapin.putExtra("lable", lable);
			JnsIMEGameListActivity.this.startActivity(mapin);
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			case R.id.add_confirm:
				AppHelper aph = JnsIMECoreService.aph;
				for(int i = 0; i < JnsIMEPopAddAdapter.isSelected.size(); i++)
				{
					if(JnsIMEPopAddAdapter.isSelected.get(i))
					{
						ResolveInfo rinfo = (ResolveInfo)((popAdapter.apps.get(i)).get("resolveInfo"));
						aph.Insert(rinfo.activityInfo.packageName, "true");
					}
				}
				Cursor cursor = aph.Qurey(null, "F");
				gameAdapter.setCursor(cursor);
				gameAdapter.notifyDataSetChanged();
				adddialog.dismiss();
				break;
			case R.id.add_cancal:
				adddialog.dismiss();
				break;
			case R.id.default_keymapping1:
				startKeyMapping("default1");
				break;
			case R.id.default_keymapping2:
				startKeyMapping("default2");
				break;
			case R.id.default_keymapping3:
				startKeyMapping("default3");
				break;
			case R.id.default_keymapping4:
				startKeyMapping("default4");
				break;
			case R.id.dian1:
				JnsIMECoreService.currentDeaultIndex = 0;
				chageDefaultCheckBox(JnsIMECoreService.currentDeaultIndex);
				break;
			case R.id.dian2:
				JnsIMECoreService.currentDeaultIndex = 1;
				chageDefaultCheckBox(JnsIMECoreService.currentDeaultIndex);
				break;
			case R.id.dian3:
				JnsIMECoreService.currentDeaultIndex = 2;
				chageDefaultCheckBox(JnsIMECoreService.currentDeaultIndex);
				break;
			case R.id.dian4:
				JnsIMECoreService.currentDeaultIndex = 3;
				chageDefaultCheckBox(JnsIMECoreService.currentDeaultIndex);
				break;
			case R.id.add_game:
				View view = JnsIMEGameListActivity.this.getLayoutInflater().inflate(R.layout.add_game, null);
				ListView lv = (ListView) view.findViewById(R.id.lv);
				Button comfirm = (Button) view.findViewById(R.id.add_confirm);
				Button cancel = (Button) view.findViewById(R.id.add_cancal);
				comfirm.setOnClickListener(ocl);
				cancel.setOnClickListener(ocl);
				popAdapter = new JnsIMEPopAddAdapter(JnsIMEGameListActivity.this);
				lv.setAdapter(popAdapter);
				//	adddialog = new AlertDialog.Builder(JnsIMEGameListActivity.this,R.style.mydialog).setView(view).create();
				adddialog = new Dialog(JnsIMEGameListActivity.this, R.style.mydialog);
				adddialog.setContentView(view);
				adddialog.setCancelable(true);
				WindowManager m = getWindowManager();    
				Display d = m.getDefaultDisplay();  //Ϊ��ȡ��Ļ����     
				android.view.WindowManager.LayoutParams p = adddialog.getWindow().getAttributes();  //��ȡ�Ի���ǰ�Ĳ���ֵ     
				p.height = (int) (d.getHeight() * 0.8);   //�߶�����Ϊ��Ļ��1.0    
				p.width = (int) (d.getWidth() * 0.5);    //�������Ϊ��Ļ��0.8    
				//p.alpha = 1.0f;      //���ñ���͸����   
				//p.dimAmount = 0.0f;      //���úڰ���   

				adddialog.getWindow().setAttributes(p);
				adddialog.show();
				lv.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
						// TODO Auto-generated method stub
						// ViewHolder vHollder = (ViewHolder) view.getTag();    
						//��ÿ�λ�ȡ�����itemʱ�����ڵ�checkbox״̬�ı䣬ͬʱ�޸�map��ֵ��    
						CheckBox cbox = (CheckBox) view.findViewById(R.id.cb); 
						Log.v("check box","select at" + position);
						cbox.toggle();    
						JnsIMEPopAddAdapter.isSelected.put(position, cbox.isChecked());
					}

				});
				break;
			}
		}

	};
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		JnsIMECoreService.activitys.remove(this);
	}
	@Override
	public void onResume()
	{
		super.onResume();
		if(JnsIMECoreService.aph== null)
			JnsIMECoreService.aph = new AppHelper(this);
		AppHelper aph = JnsIMECoreService.aph;
		Cursor cursor = aph.Qurey(null, "F");
		gameAdapter.setCursor(cursor);
		gameAdapter.notifyDataSetChanged();
	}
}
