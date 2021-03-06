package com.sixin.im.widget.parallaxpager;

import android.content.Context;
import android.view.LayoutInflater;

public class ParallaxLayoutInflater extends LayoutInflater {

  ParallaxLayoutInflater(LayoutInflater original, Context newContext) {
    super(original, newContext);
    setUpLayoutFactory();
  }

  private void setUpLayoutFactory() {
    if (!(getFactory() instanceof ParallaxFactory)) {
      setFactory(new ParallaxFactory(this, getFactory()));
    }
  }

  @Override
  public LayoutInflater cloneInContext(Context newContext) {
    return new ParallaxLayoutInflater(this, newContext);
  }
}