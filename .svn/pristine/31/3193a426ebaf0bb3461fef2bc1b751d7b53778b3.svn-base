package cn.retech.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import cn.retech.adapter.MyFragmentPagerAdapter;
import cn.retech.custom_control.ControlOnActionEnum;
import cn.retech.custom_control.ICustomControlDelegate;
import cn.retech.custom_control.PageTitle;
import cn.retech.custom_control.TabNavigation;
import cn.retech.custom_control.TabNavigation.OnTabChangeListener;
import cn.retech.domainbean_model.book_categories.BookCategoriesNetRequestBean;
import cn.retech.domainbean_model.book_categories.BookCategoriesNetRespondBean;
import cn.retech.domainbean_model.book_categories.BookCategory;
import cn.retech.domainbean_model.local_book_list.LocalBookList;
import cn.retech.domainbean_model.login.LogonNetRequestBean;
import cn.retech.domainbean_model.login.LogonNetRespondBean;
import cn.retech.global_data_cache.GlobalDataCacheForMemorySingleton;
import cn.retech.my_domainbean_engine.domainbean_network_engine_singleton.DomainBeanNetworkEngineSingleton;
import cn.retech.my_domainbean_engine.domainbean_network_engine_singleton.DomainBeanNetworkEngineSingleton.NetRequestIndex;
import cn.retech.my_domainbean_engine.domainbean_network_engine_singleton.IDomainBeanNetRespondListener;
import cn.retech.my_domainbean_engine.net_error_handle.NetErrorBean;
import cn.retech.toolutils.DebugLog;

public class PrivateBookStoreActivity extends FragmentActivity implements ICustomControlDelegate {
	private final String TAG = this.getClass().getSimpleName();
	private NetRequestIndex netRequestIndexForLogin = new NetRequestIndex();
	private NetRequestIndex netRequestIndexForBookCategories = new NetRequestIndex();

	private TabNavigation mTabNavigation;
	private List<BookCategory> mbookCategories;
	private ViewPager mViewPager;
	private MyFragmentPagerAdapter mAdapter;

	// 书城图书列表(完成本地图书列表和从服务器新获取的图书列表进行了数据同步)
	private LocalBookList bookList = new LocalBookList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		ActionBar actionBar = getActionBar();
		PageTitle pageTitle = new PageTitle(this);
		pageTitle.setiCustomControlDelegate(this);
		pageTitle.setTitle(getResources().getString(R.string.private_bookstore));
		actionBar.setCustomView(pageTitle);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);

		setContentView(R.layout.activity_private_book_store);
		// 从缓存中获取用户信息判断是否已经登录
		LogonNetRespondBean logonNetRespondBean = GlobalDataCacheForMemorySingleton.getInstance.getPrivateAccountLogonNetRespondBean();
		if (logonNetRespondBean == null) {
			showDialogLogin();
		} else {
			logon(logonNetRespondBean.getUsername(), logonNetRespondBean.getPassword());
		}

		mTabNavigation = (TabNavigation) findViewById(R.id.tabNavigation);
		mTabNavigation.setOnTabChangeListener(new OnTabChangeListener() {
			@Override
			public void onTabChange(int postion) {
				mViewPager.setCurrentItem(postion);
			}
		});

		mViewPager = (ViewPager) findViewById(R.id.book_list_viewPager);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageSelected(int arg0) {
				mTabNavigation.setCurrentItem(arg0);
			}
		});

	}

	private void showDialogLogin() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.login_dialog, null);

		final EditText userNameEditText = (EditText) v.findViewById(R.id.user_name_editText);
		final EditText passwordEditText = (EditText) v.findViewById(R.id.password_editText);
		final CheckBox autoLoginCheckBox = (CheckBox) v.findViewById(R.id.auto_login_checkBox);

		userNameEditText.setText("appletest");
		passwordEditText.setText("appletest");

		AlertDialog.Builder builder = new AlertDialog.Builder(PrivateBookStoreActivity.this);
		builder.setView(v);
		builder.create();
		builder.setTitle("用户登录");
		builder.setCancelable(false);// 这里是屏蔽用户点击back按键
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				logon(userNameEditText.getText().toString(), passwordEditText.getText().toString());

			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				PrivateBookStoreActivity.this.finish();
			}
		});
		builder.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	private void logon(String userID, String userPassWord) {
		//
		GlobalDataCacheForMemorySingleton.getInstance.setUsernameForLastSuccessfulLogon(userID);
		GlobalDataCacheForMemorySingleton.getInstance.setPasswordForLastSuccessfulLogon(userPassWord);

		LogonNetRequestBean netRequestBean = new LogonNetRequestBean.Builder(userID, userPassWord).builder();
		DomainBeanNetworkEngineSingleton.getInstance.requestDomainProtocol(netRequestIndexForLogin, netRequestBean, new IDomainBeanNetRespondListener() {
			@Override
			public void onFailure(NetErrorBean error) {
				Toast.makeText(PrivateBookStoreActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
				Toast.makeText(PrivateBookStoreActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(Object respondDomainBean) {
				// 如果成功登录需要将返回的业务bean存储到全局缓存中，当用户切换界面时直接读取数据，判断用户是否已经登录
				LogonNetRespondBean privateAccountLogonNetRespondBean = (LogonNetRespondBean) respondDomainBean;
				privateAccountLogonNetRespondBean.setUsername(GlobalDataCacheForMemorySingleton.getInstance.getUsernameForLastSuccessfulLogon());
				privateAccountLogonNetRespondBean.setPassword(GlobalDataCacheForMemorySingleton.getInstance.getPasswordForLastSuccessfulLogon());
				GlobalDataCacheForMemorySingleton.getInstance.setPrivateAccountLogonNetRespondBean(privateAccountLogonNetRespondBean);
				// 请求标签
				testBookCategories();
			}

		});

		// DomainBeanNetworkEngineSingleton.getInstance.cancelNetRequestByRequestIndex(netRequestIndexForLogin);
	}

	private void testBookCategories() {
		BookCategoriesNetRequestBean netRequestBean = new BookCategoriesNetRequestBean();
		DomainBeanNetworkEngineSingleton.getInstance.requestDomainProtocol(netRequestIndexForBookCategories, netRequestBean, new IDomainBeanNetRespondListener() {

			@Override
			public void onFailure(NetErrorBean error) {
				DebugLog.e(TAG, "testBookCategories = " + error.getErrorMessage());
			}

			@Override
			public void onSuccess(Object respondDomainBean) {
				BookCategoriesNetRespondBean respondBean = (BookCategoriesNetRespondBean) respondDomainBean;

				mbookCategories = respondBean.getCategories();

				mTabNavigation.showCategory(mbookCategories);

				List<Fragment> fragments = new ArrayList<Fragment>();
				for (BookCategory bookCategory : mbookCategories) {
					Bundle bundle = new Bundle();
					bundle.putString("identifier", bookCategory.getIdentifier());
					bundle.putString("LogonState", BookListFragment.LogonStateEnum.PRIVATE_BOOK_STORE.getState());

					fragments.add(Fragment.instantiate(PrivateBookStoreActivity.this, BookListFragment.class.getName(), bundle));
				}

				mAdapter = new MyFragmentPagerAdapter(PrivateBookStoreActivity.this.getSupportFragmentManager(), fragments);
				mViewPager.setAdapter(mAdapter);
				mViewPager.setCurrentItem(0);
			}
		});
	}

	@Override
	public void customControlOnAction(Object contorl, Object actionTypeEnum) {
		if (actionTypeEnum instanceof ControlOnActionEnum) {
			Fragment currentFragment = null;
			if (mAdapter != null) {
				currentFragment = mAdapter.getItem(mViewPager.getCurrentItem());
			}

			switch ((ControlOnActionEnum) actionTypeEnum) {
			case BACK_TO_MYBOOKSHLF:
				this.finish();

				break;
			case REFRESH:
				do {
					if (currentFragment == null) {
						break;
					}
					if (!(currentFragment instanceof IFragmentOptions)) {
						break;
					}
					((IFragmentOptions) currentFragment).refresh();

				} while (false);

				break;
			case SHOW_SEARCH:

				do {
					if (currentFragment == null) {
						break;
					}
					if (!(currentFragment instanceof IFragmentOptions)) {
						break;
					}
					((IFragmentOptions) currentFragment).doSearch((String) contorl);

				} while (false);
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (null != mTabNavigation) {
			mTabNavigation.showCategory(mbookCategories);
		}
	}

}
