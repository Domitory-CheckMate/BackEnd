package org.gachon.checkmate.domain.checkList.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.checkList.dto.request.CheckListRequestDto;
import org.gachon.checkmate.domain.checkList.dto.response.CheckListResponseDto;
import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.checkList.repository.CheckListRepository;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.global.error.exception.ConflictException;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.gachon.checkmate.global.error.ErrorCode.*;

@RequiredArgsConstructor
@Transactional
@Service
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;

    public void createCheckList(Long userId, CheckListRequestDto checkListRequestDto) {
        User user = findByIdOrThrow(userId);
        checkIfCheckListExists(user);
        CheckList checkList = CheckList.createCheckList(user, checkListRequestDto);
        checkListRepository.save(checkList);
    }

    public void updateCheckList(Long userId, CheckListRequestDto checkListRequestDto) {
        User user = findByIdOrThrow(userId);
        checkIfCheckListNotExists(user);
        CheckList checkList = user.getCheckList();
        checkList.updateCheckList(checkListRequestDto);
        checkListRepository.save(checkList);
    }

    @Transactional(readOnly = true)
    public CheckListResponseDto getCheckList(Long userId) {
        User user = findByIdOrThrow(userId);
        CheckList checkList = user.getCheckList();
        return CheckListResponseDto.of(checkList.getCleanType().getDesc(),
                checkList.getDrinkType().getDesc(),
                checkList.getHomeType().getDesc(),
                checkList.getLifePatternType().getDesc(),
                checkList.getNoiseType().getDesc(),
                checkList.getSleepType().getDesc(),
                checkList.getSmokeType().getDesc());
    }

    private User findByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    public void checkIfCheckListExists(User user) {
        if (checkListRepository.existsByUser(user)) {
            throw new ConflictException(DUPLICATE_CHECK_LIST);
        }
    }

    public void checkIfCheckListNotExists(User user) {
        if (!checkListRepository.existsByUser(user)) {
            throw new EntityNotFoundException(CHECK_LIST_NOT_FOUND);
        }
    }


}
