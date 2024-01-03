package org.gachon.checkmate.domain.checkList.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.checkList.dto.request.CreateCheckListRequestDto;
import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.checkList.repository.CheckListRepository;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.global.error.exception.ConflictException;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.gachon.checkmate.global.error.ErrorCode.DUPLICATE_CHECK_LIST;
import static org.gachon.checkmate.global.error.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;

    public void createCheckList(Long userId, CreateCheckListRequestDto createCheckListRequestDto) {
        User user = findByIdOrThrow(userId);
        checkIfCheckListExists(user);
        CheckList checkList = CheckList.createCheckList(user, createCheckListRequestDto);
        checkListRepository.save(checkList);
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


}
