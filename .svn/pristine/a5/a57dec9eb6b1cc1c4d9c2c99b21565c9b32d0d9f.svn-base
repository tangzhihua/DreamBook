package cn.retech.activity;

import java.util.List;

import cn.retech.activity.BookListFragment.LogonStateEnum;

import cn.retech.domainbean_model.get_book_download_url.GetBookDownloadUrlNetRequestBean;
import cn.retech.domainbean_model.get_book_download_url.GetBookDownloadUrlNetRespondBean;
import cn.retech.domainbean_model.login.LogonNetRespondBean;

import android.widget.AdapterView;
import cn.retech.global_data_cache.GlobalDataCacheForMemorySingleton;

import cn.retech.domainbean_model.local_book_list.LocalBook;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import cn.retech.adapter.BookStoreAdapter;
import cn.retech.domainbean_model.book_search.BookSearchNetRequestBean;
import cn.retech.domainbean_model.booklist_in_bookstores.BookInfo;
import cn.retech.domainbean_model.booklist_in_bookstores.BookListInBookstoresNetRespondBean;
import cn.retech.domainbean_model.local_book_list.LocalBookList;
import cn.retech.my_domainbean_engine.domainbean_network_engine_singleton.DomainBeanNetworkEngineSingleton;
import cn.retech.my_domainbean_engine.domainbean_network_engine_singleton.DomainBeanNetworkEngineSingleton.NetRequestIndex;
import cn.retech.my_domainbean_engine.domainbean_network_engine_singleton.IDomainBeanNetRespondListener;
import cn.retech.my_domainbean_engine.net_error_handle.NetErrorBean;
import cn.retech.toolutils.DebugLog;

public class BookSearchFragment extends Fragment implements IFragmentOptions {
  private final String TAG = this.getClass().getSimpleName();
  private BookStoreAdapter bookStoreAdapter;
  private LocalBookList bookList = new LocalBookList();
  private GridView bookstoreGridView;
  private RelativeLayout netResuqesttingLayout;
  private NetRequestIndex netRequestIndexForBookSearch = new NetRequestIndex();

  private NetRequestIndex netRequestIndexForGetBookDownloadUrl = new NetRequestIndex();

  private LogonNetRespondBean bindAccount = new LogonNetRespondBean();

  @Override
  public void doSearch(String bookName) {
    testSearchBook(bookName);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_book_search, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    netResuqesttingLayout = (RelativeLayout) getView().findViewById(R.id.net_requestting_layout);

    bookstoreGridView = (GridView) getView().findViewById(R.id.book_list_gridView);
    bookstoreGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final LocalBook book = bookList.get(position);
        switch (book.getBookStateEnum()) {
          case kBookStateEnum_Unpaid:// 未付费(只针对收费的书籍, 如果是免费的书籍, 会直接到下一个状态.

            break;
          case kBookStateEnum_Paiding:// 支付中....

            break;
          case kBookStateEnum_Paid: // 已付费(已付费的书籍可以直接下载了)
            book.setBindAccount(bindAccount);
            requestBookDownlaodUrl(book.getBookInfo().getContent_id(), bindAccount);
            break;
          case kBookStateEnum_Downloading:// 正在下载中...
            book.stopDownloadBook();

            break;
          case kBookStateEnum_Pause:// 暂停(也就是未下载完成, 可以进行断点续传)
            requestBookDownlaodUrl(book.getBookInfo().getContent_id(), book.getBindAccount());
            break;
          case kBookStateEnum_NotInstalled:// 未安装(已经下载完成, 还未完成安装)

            break;
          case kBookStateEnum_Unziping:// 解压书籍zip资源包中....

            break;
          case kBookStateEnum_Installed:// 已安装(已经解压开的书籍, 可以正常阅读了)

            break;
          case kBookStateEnum_Update:// 有可以更新的内容

            break;
          default:

            break;
        }
        // 将点击下载的书籍保存到GlobalDataCacheForMemorySingleton中
        GlobalDataCacheForMemorySingleton.getInstance.getLocalBookList().addBook(book);
      }
    });

    Bundle bundle = getArguments();
    String bookName = bundle.getString("bookName");
    if (null != bookName) {
      doSearch(bookName);
    }

    String loginState = bundle.getString("LogonState");
    if (LogonStateEnum.PRIVATE_BOOK_STORE.getState().equals(loginState)) {
      bindAccount = GlobalDataCacheForMemorySingleton.getInstance.getPrivateAccountLogonNetRespondBean();

    } else if (LogonStateEnum.PUBLIC_BOOK_STORE.getState().equals(loginState)) {
      bindAccount.setUsername(GlobalDataCacheForMemorySingleton.getInstance.getPublicUserNameString());
      bindAccount.setPassword(GlobalDataCacheForMemorySingleton.getInstance.getPublicUserPasswordString());
    }
  }

  @Override
  public void refresh() {
    // TODO Auto-generated method stub

  }

  private void requestBookDownlaodUrl(final String contentID, final LogonNetRespondBean bindAccount) {
    GetBookDownloadUrlNetRequestBean netRequestBean = new GetBookDownloadUrlNetRequestBean(contentID, bindAccount);

    DomainBeanNetworkEngineSingleton.getInstance.requestDomainProtocol(netRequestIndexForGetBookDownloadUrl, netRequestBean,
        new IDomainBeanNetRespondListener() {

          @Override
          public void onFailure(NetErrorBean error) {
            DebugLog.e(TAG, error.toString());
          }

          @Override
          public void onSuccess(Object respondDomainBean) {
            GetBookDownloadUrlNetRespondBean getBookDownloadUrlNetRespondBean = (GetBookDownloadUrlNetRespondBean) respondDomainBean;
            LocalBook book = bookList.bookByContentID(contentID);
            book.startDownloadBookWithURLString(getBookDownloadUrlNetRespondBean.getBookDownloadUrl());
          }
        });
  }

  private void testSearchBook(String searchContent) {
    netResuqesttingLayout.setVisibility(View.VISIBLE);

    BookSearchNetRequestBean bookSearchNetRequestBean = new BookSearchNetRequestBean(searchContent);
    DomainBeanNetworkEngineSingleton.getInstance.requestDomainProtocol(netRequestIndexForBookSearch, bookSearchNetRequestBean,
        new IDomainBeanNetRespondListener() {

          @Override
          public void onFailure(NetErrorBean error) {
            DebugLog.e(TAG, "testSearchBook error = " + error.getErrorMessage());

            netResuqesttingLayout.setVisibility(View.INVISIBLE);
          }

          @Override
          public void onSuccess(Object respondDomainBean) {
            DebugLog.e(TAG, "testSearchBook onSuccess = " + respondDomainBean);
            bookList = new LocalBookList();

            netResuqesttingLayout.setVisibility(View.INVISIBLE);

            BookListInBookstoresNetRespondBean netRespondBean = (BookListInBookstoresNetRespondBean) respondDomainBean;
            List<BookInfo> bookInfoList = netRespondBean.getBookInfoList();
            for (BookInfo bookInfo : bookInfoList) {
              LocalBook localBook = new LocalBook(bookInfo);
              bookList.addBook(localBook);
            }

            bookStoreAdapter = new BookStoreAdapter(getActivity(), bookList);
            bookstoreGridView.setAdapter(bookStoreAdapter);
          }
        });

  }
}
