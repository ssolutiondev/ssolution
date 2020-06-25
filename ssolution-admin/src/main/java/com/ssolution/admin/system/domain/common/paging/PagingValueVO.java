package com.ssolution.admin.system.domain.common.paging;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
public class PagingValueVO {

    private String lng; //언어코드
    private String start; //시작값
    private String end; //마지막값
    private String sidx; //정렬컬럼
    private String sort; //정렬방법

    private Boolean isTablesDrawed; //그리드 데이터 출력 여부 체크
}
