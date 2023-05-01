package main.main.user.service;

import lombok.RequiredArgsConstructor;
import main.main.exception.BusinessLogicException;
import main.main.exception.ExceptionCode;
import main.main.user.entity.User;
import main.main.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public User updateUser(User user) {
        User findedUser = findVerifiedUser(user.getUserId());
        verifiyExitstResidentNumber(user.getResidentNumber());

        Optional.ofNullable(user.getName())
                .ifPresent(name -> findedUser.setName(user.getName()));
        Optional.ofNullable(user.getEmail())
                .ifPresent(email -> findedUser.setEmail(user.getEmail()));
        Optional.ofNullable(user.getPhoneNumber())
                .ifPresent(phoneNumber -> findedUser.setPhoneNumber(user.getPhoneNumber()));
        Optional.ofNullable(user.getResidentNumber())
                .ifPresent(residentNumber -> findedUser.setResidentNumber(user.getResidentNumber()));
        Optional.ofNullable(user.getAddress())
                .ifPresent(address -> findedUser.setAddress(findedUser.getAddress()));
        Optional.ofNullable(user.getGrade())
                .ifPresent(grade -> findedUser.setGrade(findedUser.getGrade()));

        return userRepository.save(findedUser);
    }


    public User findUser(Long userId) {
        return findVerifiedUser(userId);
    }

    public User createUser(User user) {
        verifiyExitstResidentNumber(user.getResidentNumber());

        return userRepository.save(user);

    }

    private void verifiyExitstResidentNumber(String residentNumber) {
        Optional<User> findedUserByResidentNumber = userRepository.findByResidentNumber(residentNumber);
        if (findedUserByResidentNumber.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_RESIDENTNUMBER_EXISTS);
        }
    }

    public User findVerifiedUser (Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User findedUser = optionalUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return findedUser;
    }

    public void deleteUser(Long userId, long authenticationUserId) {
        checkVerifiedId(authenticationUserId);

        User findUser = findVerifiedUser(userId);
        deletePermission(findUser, authenticationUserId);
        userRepository.delete(findUser);
    }

    private void checkVerifiedId(long authenticationUserId) {
        if (authenticationUserId == -1) throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
    }

    public void deletePermission(User user, long authenticationUserId) {
        if (!user.getUserId().equals(authenticationUserId) && !user.getEmail().equals("admin@gmail.com")) {
            throw new BusinessLogicException(ExceptionCode.ONLY_AUTHOR);
        }
    }
}
