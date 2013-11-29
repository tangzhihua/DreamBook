package cn.retech.custom_control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import cn.retech.activity.R;

public class PageTitle extends LinearLayout {
  private ICustomControlDelegate iCustomControlDelegate;// 控制层抽象,即Activity,用以处理按钮的点击事件
  private View backView;
  private ImageButton refreshButton;
  private SearchView searchView;
  private TextView textView;
  private RelativeLayout relativeLayout;

  public PageTitle(final Context context) {
    super(context);

    init(context);
  }

  public PageTitle(Context context, AttributeSet attrs) {
    super(context, attrs);

    init(context);
  }

  /**
   * @param iCustomControlDelegate the iCustomControlDelegate to set
   */
  public void setiCustomControlDelegate(ICustomControlDelegate iCustomControlDelegate) {
    this.iCustomControlDelegate = iCustomControlDelegate;
  }

  public void setTitle(String title) {
    textView.setText(title);
  }

  private void init(Context context) {
    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    layoutInflater.inflate(R.layout.page_title_layout, this);

    relativeLayout = (RelativeLayout) findViewById(R.id.page_title_relativeLayout);
    textView = (TextView) findViewById(R.id.title_TextView);

    // 返回按钮
    backView = findViewById(R.id.back_layout);
    backView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        iCustomControlDelegate.customControlOnAction(v, ControlOnActionEnum.BACK_TO_MYBOOKSHLF);
      }
    });

    // 刷新按钮
    refreshButton = (ImageButton) findViewById(R.id.refresh_ImageButton);
    refreshButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        iCustomControlDelegate.customControlOnAction(v, ControlOnActionEnum.REFRESH);
      }
    });

    // 搜索框
    searchView = (SearchView) findViewById(R.id.search_SearchView);
    final int searchViewIndex = relativeLayout.indexOfChild(searchView);
    searchView.setOnCloseListener(new OnCloseListener() {
      @Override
      public boolean onClose() {
        // 显示刷新按钮
        refreshButton.setVisibility(View.VISIBLE);

        // 只是些无关痛痒的动画,与业务无关
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
          if (i != searchViewIndex) {
            final View view = relativeLayout.getChildAt(i);
            final float y = (Float) view.getTag();

            view.animate().y(y);
          }
        }

        iCustomControlDelegate.customControlOnAction(null, ControlOnActionEnum.CLOSE_SEARCH);

        return false;
      }
    });
    searchView.setOnSearchClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        // 隐藏刷新按钮,避免重叠
        refreshButton.setVisibility(View.INVISIBLE);

        // 只是些无关痛痒的动画,与业务无关
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
          if (i != searchViewIndex) {
            final View view = relativeLayout.getChildAt(i);
            if (null == view.getTag()) {
              final float y = view.getY();
              view.setTag(y);
            }

            view.animate().y(-80);
          }
        }

        iCustomControlDelegate.customControlOnAction(null, ControlOnActionEnum.OPEN_SEARCH);
      }
    });
    searchView.setOnQueryTextListener(new OnQueryTextListener() {
      @Override
      public boolean onQueryTextChange(String newText) {
        return true;
      }

      @Override
      public boolean onQueryTextSubmit(String query) {
        iCustomControlDelegate.customControlOnAction(query, ControlOnActionEnum.SHOW_SEARCH);

        return true;
      }
    });
  }
}
