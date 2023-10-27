package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.model.FooterInfo;
import com.kshrd.demobasicauth.repository.FooterInfoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FooterInfoServiceImpl implements FooterInfoService{
    private final FooterInfoRepository footerInfoRepository;
    private final ModelMapper mapper;

    @Override
    public Object getFooterInfo() {
        Long id = 1L;
        return footerInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionClass("Can't find Object footer"));
    }

    @Override
    public FooterInfo updateFooterInfo(FooterInfo footerInfo) {
        FooterInfo existInfo = footerInfoRepository.findById(1L)
                .orElseThrow(() -> new NotFoundExceptionClass("Can't find Object footer"));
        existInfo.setInstaUrl(footerInfo.getInstaUrl());
        existInfo.setWaUrl(footerInfo.getWaUrl());
        existInfo.setFbUrl(footerInfo.getFbUrl());
        existInfo.setTeleUrl(footerInfo.getTeleUrl());
        existInfo.setEmail(footerInfo.getEmail());
        existInfo.setPhoneNumber(footerInfo.getPhoneNumber());
        existInfo.setLineNumber(footerInfo.getLineNumber());
        existInfo.setWhatAppNumber(footerInfo.getWhatAppNumber());

        return footerInfoRepository.save(existInfo);
    }
}
