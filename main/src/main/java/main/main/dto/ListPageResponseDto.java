package main.main.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ListPageResponseDto<T> {
    private List<T> data;
    private PageInfo pageInfo;

    public ListPageResponseDto(List<T> data, Page page) {
        this.data = data;
        this.pageInfo = new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages());
    }
}
