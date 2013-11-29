package cn.retech.global_data_cache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.retech.toolutils.DebugLog;

import android.os.Environment;

public final class LocalCacheDataPathConstant {
	private static final String TAG = "LocalCacheDataPathConstant";
	public static final String kAppLocalCacheDirectory = "DreamBook";

	// app 本地缓存根目录
	public static String localCacheDirectory() {
		return Environment.getExternalStorageDirectory() + "/" + kAppLocalCacheDirectory;
	}

	// 项目中 "缩略图" 缓存目录 (可以被清除)
	public static String thumbnailCachePath() {
		return Environment.getExternalStorageDirectory() + "/" + kAppLocalCacheDirectory + "/" + "ThumbnailCachePath";
	}

	// 那些需要始终被保存, 不能由用户进行清除的文件
	public static String importantDataCachePath() {
		return Environment.getExternalStorageDirectory() + "/" + kAppLocalCacheDirectory + "/" + "ImportantDataCache";
	}

	// 本地书籍保存目录
	public static String localBookCachePath() {
		return Environment.getExternalStorageDirectory() + "/" + kAppLocalCacheDirectory + "/" + "LocalBookDir";
	}

	// 返回能被用户清空的文件目录数组(可以从这里获取用户可以直接清空的文件夹路径数组)
	public static List<File> directoriesCanBeClearByTheUser() {
		return null;
	}

	// 创建本地数据缓存目录(一次性全部创建, 不会重复创建)
	public static void createLocalCacheDirectories() {
		List<String> directories = new ArrayList<String>();
		directories.add(localCacheDirectory());
		directories.add(thumbnailCachePath());
		directories.add(importantDataCachePath());
		directories.add(localBookCachePath());

		for (String directoryPath : directories) {
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				if (!directory.mkdir()) {
					DebugLog.e(TAG, "创建重要的本地缓存目录失败, 目录路径是-->" + directoryPath);
				}
			}
		}
	}
}
