package cn.retech.custom_control;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class BookCell extends LinearLayout {
	// private ICustomControlDelegate iCustomControlDelegate;
	// private TextView bookNameTextView;
	// private TextView bookSizeTextView;
	// private ImageView bookImageView;
	//
	// public static enum bookCellAction {
	// BOOK_CELL_ACTION;
	// }

	public BookCell(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public BookCell(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public BookCell(Context context) {
		super(context);
	}

	@SuppressWarnings("unused")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// For simple implementation, or internal size is always 0.
		// We depend on the container to specify the layout size of
		// our view. We can't really know what it is since we will be
		// adding and removing different arbitrary views and do not
		// want the layout to change as this happens.
		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

		// Children are just made to fill our space.
		int childWidthSize = getMeasuredWidth();
		int childHeightSize = getMeasuredHeight();
		// // 高度和宽度一样
		heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
		// heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize,
		// MeasureSpec.EXACTLY);
		// widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize,
		// MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	//
	// public BookCell(Context context) {
	// super(context);
	// LayoutInflater mInflater = (LayoutInflater)
	// getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// mInflater.inflate(R.layout.bookcell_layout, this, true);
	//
	// bookNameTextView = (TextView) findViewById(R.id.bookname_textView);
	// bookImageView = (ImageView) findViewById(R.id.book_imageView);
	// bookSizeTextView = (TextView) findViewById(R.id.booksize_textView);
	// bookImageView.setBackgroundResource(R.drawable.bookcell_test);
	// bookImageView.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// if (iCustomControlDelegate != null) {
	// iCustomControlDelegate.customControlOnAction(BookCell.this,
	// bookCellAction.BOOK_CELL_ACTION);
	// }
	//
	// }
	// });
	// }
	//
	// public void setBookIcon(Bitmap bitmap) {
	// bookImageView.setImageBitmap(bitmap);
	// }
	//
	// public void setBookName(String bookName) {
	// if (!TextUtils.isEmpty(bookName)) {
	// bookNameTextView.setText(bookName);
	// }
	// }
	//
	// public void setBookSize(final long bytesWritten, final long totalSize) {
	// if (bytesWritten > 0 && totalSize > 0) {
	// bookSizeTextView.setVisibility(View.VISIBLE);
	// bookSizeTextView.setText(bytesToKbOrMb(bytesWritten) + "/" +
	// bytesToKbOrMb(totalSize));
	// }
	// }
	//
	// public void setCustomControlDelegate(ICustomControlDelegate
	// iCustomControlDelegate) {
	// this.iCustomControlDelegate = iCustomControlDelegate;
	// }
	//
	// /**
	// * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
	// *
	// * @param bytes
	// * @return
	// */
	// public static String bytesToKbOrMb(long bytes) {
	// BigDecimal filesize = new BigDecimal(bytes);
	// BigDecimal megabyte = new BigDecimal(1024 * 1024);
	// float returnValue = filesize.divide(megabyte, 2,
	// BigDecimal.ROUND_UP).floatValue();
	// if (returnValue > 1)
	// return (returnValue + "MB");
	// BigDecimal kilobyte = new BigDecimal(1024);
	// returnValue = filesize.divide(kilobyte, 2,
	// BigDecimal.ROUND_UP).floatValue();
	// return (returnValue + "KB");
	// }

}
