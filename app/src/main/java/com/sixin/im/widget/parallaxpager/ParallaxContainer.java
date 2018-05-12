package com.sixin.im.widget.parallaxpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sixin.im.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 視差滾動
 */
public class ParallaxContainer extends FrameLayout {
	private List<View> parallaxViews = new ArrayList<>();
	private ViewPager viewPager;
	private int pageCount = 0;
	private int containerWidth;
	private final ParallaxPagerAdapter adapter;

	Context context;
	public ViewPager.OnPageChangeListener mCommonPageChangeListener;
	public int currentPosition = 0;

	public ParallaxContainer(Context context) {
		super(context);
		this.context = context;
		adapter = new ParallaxPagerAdapter(context);
	}

	public ParallaxContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		adapter = new ParallaxPagerAdapter(context);
	}

	public ParallaxContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		adapter = new ParallaxPagerAdapter(context);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		containerWidth = getMeasuredWidth();
		if (viewPager != null) {
			mCommonPageChangeListener.onPageScrolled(viewPager.getCurrentItem(), 0, 0);
		}
		super.onWindowFocusChanged(hasFocus);
	}

	private void updateAdapterCount() {
		adapter.setCount(pageCount);
	}

	public void setupChildren(LayoutInflater inflater, int... childIds) {
		if (getChildCount() > 0) {
			throw new RuntimeException("setupChildren should only be called once when ParallaxContainer is empty");
		}

		ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(
				inflater, getContext());

		for (int childId : childIds) {
			parallaxLayoutInflater.inflate(childId, this);
		}

		pageCount = getChildCount();
		for (int i = 0; i < pageCount; i++) {
			View view = getChildAt(i);
			addParallaxView(view, i);
		}

		updateAdapterCount();

		viewPager = new ViewPager(getContext());
		viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		viewPager.setId(R.id.parallax_pager);
		attachOnPageChangeListener();
		viewPager.setAdapter(adapter);
		addView(viewPager, 0);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		viewPager.removeOnPageChangeListener(mCommonPageChangeListener);
	}

	protected void attachOnPageChangeListener() {
		mCommonPageChangeListener = new ViewPager.OnPageChangeListener() {
			boolean isLeft = false;

			@Override
			public void onPageScrollStateChanged(int state) {
			}



			@Override
			public void onPageScrolled(int pageIndex, float offset, int offsetPixels) {
//				Log.v(TAG, "onPageScrolled" + pageIndex + "  offset" + offset + "   offsetPixels" + offsetPixels);

				if (offsetPixels < 10) {
					isLeft = false;
				}

				if (pageCount > 0) {
					pageIndex = pageIndex % pageCount;
				}

				ParallaxViewTag tag;
				for (View view : parallaxViews) {
					tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
					if (tag == null) {
						continue;
					}

					if ((pageIndex == tag.index - 1 && containerWidth != 0)) {
						// make visible
						view.setVisibility(VISIBLE);

						// slide in from right
						view.setTranslationX((containerWidth - offsetPixels) * tag.xIn);

						// slide in from top
						view.setTranslationY(0 - (containerWidth - offsetPixels) * tag.yIn);

						// fade in
						view.setAlpha(1.0f - (containerWidth - offsetPixels) * tag.alphaIn / containerWidth);

					} else if (pageIndex == tag.index) {
						// make visible
						view.setVisibility(VISIBLE);

						// slide out to left
						view.setTranslationX(0 - offsetPixels * tag.xOut);

						// slide out to top
						view.setTranslationY(0 - offsetPixels * tag.yOut);

						// fade out
						view.setAlpha(1.0f - offsetPixels * tag.alphaOut / containerWidth);

					} else {
						view.setVisibility(GONE);
					}
				}
			}

			@Override
			public void onPageSelected(int position) {
				currentPosition = position;
			}
		};
		viewPager.addOnPageChangeListener(mCommonPageChangeListener);
	}

	private void addParallaxView(View view, int pageIndex) {
		if (view instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup) view;
			for (int i = 0, childCount = viewGroup.getChildCount(); i < childCount; i++) {
				addParallaxView(viewGroup.getChildAt(i), pageIndex);
			}
		}

		ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
		if (tag != null) {
			tag.index = pageIndex;
			parallaxViews.add(view);
		}
	}
}
