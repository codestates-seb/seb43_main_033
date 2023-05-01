package main.main.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class DataPageResponseDto<T> {
    private T data;
    private PageInfo pageInfo;
    public DataPageResponseDto(T data, Page page) {
        this.data = data;
        this.pageInfo = new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages());
    }
}
