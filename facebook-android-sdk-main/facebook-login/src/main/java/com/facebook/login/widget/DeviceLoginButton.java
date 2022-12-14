/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.facebook.login.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.internal.instrument.crashshield.AutoHandleExceptions;
import com.facebook.login.DeviceLoginManager;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;

/**
 * A Log In/Log Out button that maintains login state and logs in/out for the app.
 *
 * <p>This control requires the app ID and client token to be specified in the AndroidManifest.xml.
 */
public class DeviceLoginButton extends LoginButton {

  private Uri deviceRedirectUri;

  /**
   * Create the LoginButton by inflating from XML
   *
   * @see View#View(Context, AttributeSet)
   */
  public DeviceLoginButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  /**
   * Create the LoginButton by inflating from XML
   *
   * @see View#View(Context, AttributeSet)
   */
  public DeviceLoginButton(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * Create the LoginButton by inflating from XML and applying a style.
   *
   * @see View#View(Context, AttributeSet, int)
   */
  public DeviceLoginButton(Context context) {
    super(context);
  }

  /**
   * Set uri to redirect the user to after they complete the device login flow on the external
   * device.
   *
   * <p>The Uri must be configured in your App Settings -> Advanced -> OAuth Redirect URIs.
   *
   * @param uri The URI to set.
   */
  public void setDeviceRedirectUri(Uri uri) {
    this.deviceRedirectUri = uri;
  }

  /**
   * Get the previously set uri that will be used to redirect the user to after they complete the
   * device login flow on the external device.
   *
   * <p>The Uri must be configured in your App Settings -> Advanced -> OAuth Redirect URIs.
   *
   * @return The current device redirect uri set.
   */
  public Uri getDeviceRedirectUri() {
    return this.deviceRedirectUri;
  }

  @Override
  protected LoginClickListener getNewLoginClickListener() {
    return new DeviceLoginClickListener();
  }

  @AutoHandleExceptions
  private class DeviceLoginClickListener extends LoginClickListener {
    @Override
    protected LoginManager getLoginManager() {
      DeviceLoginManager manager = DeviceLoginManager.getInstance();
      manager.setDefaultAudience(getDefaultAudience());
      manager.setLoginBehavior(LoginBehavior.DEVICE_AUTH);
      manager.setDeviceRedirectUri(getDeviceRedirectUri());
      return manager;
    }
  }
}
