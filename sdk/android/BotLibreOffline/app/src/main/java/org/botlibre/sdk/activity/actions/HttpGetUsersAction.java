/******************************************************************************
 *
 *  Copyright 2014 Paphus Solutions Inc.
 *
 *  Licensed under the Eclipse Public License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package org.botlibre.sdk.activity.actions;

import java.util.List;

import android.app.Activity;

import org.botlibre.sdk.activity.MainActivity;
import org.botlibre.sdk.activity.WebMediumUsersActivity;
import org.botlibre.sdk.config.WebMediumConfig;

public class HttpGetUsersAction extends HttpUIAction {

	WebMediumConfig config;
	List<String> users;
	
	public HttpGetUsersAction(Activity activity, WebMediumConfig config) {
		super(activity);
		this.config = config;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			this.users = MainActivity.connection.getUsers(this.config);
		} catch (Exception exception) {
			this.exception = exception;
		}
		return "";
	}

	@Override
	public void onPostExecute(String xml) {
		super.onPostExecute(xml);
		if (this.exception != null) {
			return;
		}
		try {
			((WebMediumUsersActivity)this.activity).users = this.users;
			((WebMediumUsersActivity)this.activity).resetView();
		} catch (Exception error) {
			this.exception = error;
			MainActivity.error(this.exception.getMessage(), this.exception, this.activity);
			return;			
		}
	}
}