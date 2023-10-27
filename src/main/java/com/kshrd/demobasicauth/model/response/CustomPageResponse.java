package com.kshrd.demobasicauth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomPageResponse<T> {
    private List<T> content;
    private int offset;
    private int pageSize;
    private int pageNumber;

    public CustomPageResponse(Page<T> page) {
        this.content = page.getContent();
        this.offset = page.getNumber() * page.getSize();
        this.pageSize = page.getSize();
        this.pageNumber = page.getNumber();
    }
}
