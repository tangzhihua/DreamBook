package cn.retech.custom_control;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.retech.activity.R;
import cn.retech.domainbean_model.book_categories.BookCategory;

public class TabNavigation extends HorizontalScrollView implements OnClickListener {
  public interface OnTabChangeListener {
    /**
     * 当导航标签切换时调用
     * 
     * @param v The view that was clicked.
     */
    void onTabChange(int postion);
  }

  private Context context;
  private OnTabChangeListener onTabChangeListener;

  private TextView tabCursor;
  private LinearLayout tabNavigationLayout;
  private int eachPageNum = 2;// 每页显示的按钮数
  private int currentPageNum;// 当前显示页的索引
  private int itemWidth;// 每个按钮宽度
  private int currentItemIndex = 0;// 当前选中按钮的索引

  public TabNavigation(Context context) {
    super(context);

    this.context = context;

    init();
  }

  public TabNavigation(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.context = context;

    init();
  }

  // 导航按钮的点击事件
  @Override
  public void onClick(View view) {
    changeTab(view);

    if (null != onTabChangeListener) {
      onTabChangeListener.onTabChange(tabNavigationLayout.indexOfChild(view));
    }
  }

  public void setCurrentItem(int postion) {
    View view = tabNavigationLayout.getChildAt(postion);

    if (null != view) {
      changeTab(view);
    }
  }

  /**
   * @param onTabChangeListener the onTabChangeListener to set
   */
  public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
    this.onTabChangeListener = onTabChangeListener;
  }

  public void showCategory(List<BookCategory> list) {
    if (null == list) {
      return;
    }

    tabNavigationLayout.removeAllViews();

    // 根据"屏幕宽度"/"每页显示的按钮数"得到每个按钮宽度
    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    itemWidth = displayMetrics.widthPixels / eachPageNum;

    // 根据图书类别个数,添加导航按钮
    LayoutParams layoutParams = new LayoutParams(itemWidth, LayoutParams.MATCH_PARENT);
    for (BookCategory bookCategory : list) {
      TextView tab = new TextView(context);
      tab.setTag(bookCategory);
      tab.setText(bookCategory.getName());
      tab.setGravity(Gravity.CENTER);
      tab.setOnClickListener(this);

      tabNavigationLayout.addView(tab, layoutParams);
    }

    // 重置索引条位置
    tabCursor.setX(0);
    tabCursor.setWidth(itemWidth);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);

    View view = tabNavigationLayout.getChildAt(currentItemIndex);
    if (null != view) {
      tabCursor.setX(view.getX());
    }
  }

  /**
   * 页数滑动算法:根据导航按钮(即TextView)在Layout里的index值算出其所在页的索引,将此索引与屏幕显示的当前页的索引对比,
   * 判断是将HorizontalScrollView进行左滑动还是右滑动操作
   */
  private void changeTab(View view) {
    currentItemIndex = tabNavigationLayout.indexOfChild(view);
    tabCursor.animate().x(view.getX());

    // 获得导航按钮所在页的索引:(index+1)/每页显示的按钮数
    int index = tabNavigationLayout.indexOfChild(view) + 1;
    int itemPageNum = index % eachPageNum == 0 ? index / eachPageNum : index / eachPageNum + 1;

    // 所在页的索引>当前页的索引,则右滑动并且当前页数++
    if (itemPageNum > currentPageNum) {
      while (itemPageNum > currentPageNum) {
        this.pageScroll(View.FOCUS_RIGHT);
        currentPageNum++;
      }
    }
    // 所在页的索引<当前页的索引,则左滑动并且当前页数--
    else if (itemPageNum < currentPageNum) {
      while (itemPageNum < currentPageNum) {
        this.pageScroll(View.FOCUS_LEFT);
        currentPageNum--;
      }
    }
  }

  private void init() {
    this.setHorizontalScrollBarEnabled(false);

    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    layoutInflater.inflate(R.layout.tab_navigation_layout, this);

    currentPageNum = 1;

    tabNavigationLayout = (LinearLayout) findViewById(R.id.tab_navigation_radioGroup);

    tabCursor = (TextView) findViewById(R.id.tab_cursor_textView);
  }
}
