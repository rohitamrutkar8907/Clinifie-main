package com.org.customer.fragment.interfaces;

import com.org.customer.fragment.model.Date;

public interface WorkDoneViewClickInterface {
    void onLocClick(int pos, Date plan);
    void onGalleryClick(int pos, Date plan);
}
