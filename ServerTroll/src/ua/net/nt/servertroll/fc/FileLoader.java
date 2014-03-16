/*
 * Copyright (C) 2013 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua.net.nt.servertroll.fc;

import android.content.Context;
import android.os.FileObserver;
import android.support.v4.content.AsyncTaskLoader;

//import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileLoader extends AsyncTaskLoader<List<File>> {

	private List<File> _data;
	private String mPath;

	public FileLoader(Context context, String path) {
		super(context);
		this.mPath = path;
	}

	@Override
	public List<File> loadInBackground() {

        ArrayList<File> list = new ArrayList<File>();
        //ls -l --group-directories-first | awk '{print substr($1,0,1) " " $9}'
//		String a[] = str.split(" ");

        // Current directory File instance
        final File pathDir = new File(mPath);

        // List file in this directory with the directory filter
        final File[] dirs = pathDir.listFiles(FileUtils.sDirFilter);
        if (dirs != null) {
            // Sort the folders alphabetically
            Arrays.sort(dirs, FileUtils.sComparator);
            // Add each folder to the File list for the list adapter
            for (File dir : dirs)
                list.add(dir);
        }

        // List file in this directory with the file filter
        final File[] files = pathDir.listFiles(FileUtils.sFileFilter);
        if (files != null) {
            // Sort the files alphabetically
            Arrays.sort(files, File.sComparator);
            // Add each file to the File list for the list adapter
            for (File file : files)
                list.add(file);
        }

        return list;
	}

	@Override
	public void deliverResult(List<File> data) {
		if (isReset()) {
			onReleaseResources(data);
			return;
		}

		List<File> oldData = _data;
		_data = data;

		if (isStarted())
			super.deliverResult(data);

		if (oldData != null && oldData != data)
			onReleaseResources(oldData);
	}

	@Override
	protected void onStartLoading() {
		if (_data != null)
			deliverResult(_data);

		if (takeContentChanged() || _data == null)
			forceLoad();
	}

	@Override
	protected void onStopLoading() {
		cancelLoad();
	}

	@Override
	protected void onReset() {
		onStopLoading();

		if (_data != null) {
			onReleaseResources(_data);
			_data = null;
		}
	}

	@Override
	public void onCanceled(List<File> data) {
		super.onCanceled(data);

		onReleaseResources(data);
	}

	protected void onReleaseResources(List<File> data) {

	}
}