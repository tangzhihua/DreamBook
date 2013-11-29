package cn.retech.activity;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import cn.retech.adapter.MyFragmentPagerAdapter;
import cn.retech.custom_control.ControlOnActionEnum;
import cn.retech.custom_control.ICustomControlDelegate;
import cn.retech.custom_control.PageTitle;
import cn.retech.custom_control.TabNavigation;
import cn.retech.custom_control.TabNavigation.OnTabChangeListener;
import cn.retech.domainbean_model.book_categories.BookCategoriesNetRequestBean;
import cn.retech.domainbean_model.book_categories.BookCategoriesNetRespondBean;
import cn.retech.domainbean_model.book_categories.BookCategory;
import cn.retech.domainbean_model.login.LogonNetRequestBean;
import cn.retech.global_data_cache.GlobalDataCacheForMemorySingleton;
import cn.retech.my_domainbean_engine.domainbean_network_engine_singleton.DomainBeanNetworkEngineSingleton;
import cn.retech.my_domainbean_engine.domainbean_network_engine_singleton.DomainBeanNetworkEngineSingleton.NetRequestIndex;
import cn.retech.my_domainbean_engine.domainbean_network_engine_singleton.IDomainBeanNetRespondListener;
import cn.retech.my_domainbean_engine.net_error_handle.NetErrorBean;
import cn.retech.toolutils.DebugLog;

public class PubliceBookStoreActivity extends FragmentActivity implements ICustomControlDelegate {
  private final String TAG = this.getClass().getSimpleName();

  private FragmentManager mFragmentManager = getFragmentManager();
  private final static String SEARCH_FRAGMENT_TAG = "SearchBook";

  private NetRequestIndex netRequestIndexForLogin = new NetRequestIndex();
  private NetRequestIndex netRequestIndexForBookCategories = new NetRequestIndex();

  private TabNavigation mTabNavigation;
  private List<BookCategory> mbookCategories;
  private MyFragmentPagerAdapter mAdapter;
  private ViewPager mViewPager;
  int viewPageCurrentIndex;

  private RelativeLayout mbookSearchLayout;

  @Override
  public void customControlOnAction(Object contorl, Object actionTypeEnum) {
    if (actionTypeEnum instanceof ControlOnActionEnum) {
      switch ((ControlOnActionEnum) actionTypeEnum) {
        case BACK_TO_MYBOOKSHLF:
          this.finish();

          break;
        case REFRESH:
          Fragment currentFragment = null;

          if (mAdapter != null) {
            currentFragment = mAdapter.getItem(mViewPager.getCurrentItem());
          }

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
          android.app.Fragment searchFragment = mFragmentManager.findFragmentByTag(SEARCH_FRAGMENT_TAG);

          if (null == searchFragment) {
            mbookSearchLayout.setAlpha((float) 1.0);

            Bundle bundle = new Bundle();
            bundle.putString("bookName", (String) contorl);
            bundle.putString("LogonState", BookListFragment.LogonStateEnum.PUBLIC_BOOK_STORE.getState());
            searchFragment = android.app.Fragment.instantiate(this, BookSearchFragment.class.getName(), bundle);

            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.disallowAddToBackStack();
            fragmentTransaction.add(R.id.book_search_layout, searchFragment, SEARCH_FRAGMENT_TAG);
            fragmentTransaction.commit();

            searchFragment = mFragmentManager.findFragmentByTag(SEARCH_FRAGMENT_TAG);
          }

          if (searchFragment instanceof IFragmentOptions) {
            ((IFragmentOptions) searchFragment).doSearch((String) contorl);
          }

          break;
        case OPEN_SEARCH:
          mbookSearchLayout.animate().y(0).setDuration(700);

          break;
        case CLOSE_SEARCH:
          mbookSearchLayout.animate().setDuration(450).y(-mbookSearchLayout.getHeight()).setListener(new AnimatorListener() {
            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
              mbookSearchLayout.setAlpha((float) 0.6);

              if (null != mFragmentManager.findFragmentByTag(SEARCH_FRAGMENT_TAG)) {
                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                fragmentTransaction.remove(mFragmentManager.findFragmentByTag(SEARCH_FRAGMENT_TAG));
                fragmentTransaction.commit();
              }

              mbookSearchLayout.animate().setListener(null);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
          });

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    ActionBar actionBar = getActionBar();
    PageTitle pageTitle = new PageTitle(this);
    pageTitle.setiCustomControlDelegate(this);
    pageTitle.setTitle(getResources().getString(R.string.public_bookstore));
    actionBar.setCustomView(pageTitle);
    actionBar.setDisplayShowCustomEnabled(true);
    actionBar.setDisplayShowHomeEnabled(false);

    setContentView(R.layout.activity_public_book_store);

    mbookSearchLayout = (RelativeLayout) findViewById(R.id.book_search_layout);
    mbookSearchLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        // 拦截点击事件
      }
    });
    mbookSearchLayout.setY(-2000);

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
        viewPageCurrentIndex = arg0;
      }
    });

    testLogon();
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

  private void testBookCategories() {
    BookCategoriesNetRequestBean netRequestBean = new BookCategoriesNetRequestBean();
    DomainBeanNetworkEngineSingleton.getInstance.requestDomainProtocol(netRequestIndexForBookCategories, netRequestBean,
        new IDomainBeanNetRespondListener() {

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
              bundle.putString("LogonState", BookListFragment.LogonStateEnum.PUBLIC_BOOK_STORE.getState());

              fragments.add(Fragment.instantiate(PubliceBookStoreActivity.this, BookListFragment.class.getName(), bundle));
            }

            mAdapter = new MyFragmentPagerAdapter(PubliceBookStoreActivity.this.getSupportFragmentManager(), fragments);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(0);
          }
        });
  }

  private void testLogon() {

    LogonNetRequestBean netRequestBean =
        new LogonNetRequestBean.Builder(GlobalDataCacheForMemorySingleton.getInstance.getPublicUserNameString(),
            GlobalDataCacheForMemorySingleton.getInstance.getPublicUserPasswordString()).builder();
    DomainBeanNetworkEngineSingleton.getInstance.requestDomainProtocol(netRequestIndexForLogin, netRequestBean,
        new IDomainBeanNetRespondListener() {
          @Override
          public void onFailure(NetErrorBean error) {
            DebugLog.e(TAG, "testLogon = " + error.getErrorMessage());
          }

          @Override
          public void onSuccess(Object respondDomainBean) {
            testBookCategories();
          }

        });
  }
}
