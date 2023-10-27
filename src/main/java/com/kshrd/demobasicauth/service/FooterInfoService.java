package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.model.FooterInfo;

import java.util.Optional;

public interface FooterInfoService {
    Object getFooterInfo();

    FooterInfo updateFooterInfo(FooterInfo footerInfo);
}
