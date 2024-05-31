package com.elice.ustory.domain.diaryUser.repository;

import com.elice.ustory.domain.diary.dto.DiaryList;
import com.elice.ustory.domain.diary.entity.DiaryCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiaryUserRepositoryCustom {
    List<DiaryList> searchDiaryList(Long userId);

    Long countUserByDiary(Long diaryId);

    List<String> findUserByDiary(Long diaryId);

    Page<DiaryList> searchDiary(Long userId, Pageable pageable, DiaryCategory diaryCategory);

}
