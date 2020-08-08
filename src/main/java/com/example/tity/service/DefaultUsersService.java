package com.example.tity.service;


import com.example.tity.models.Users;
import com.example.tity.models.UsersDto;
import com.example.tity.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class DefaultUsersService implements UsersService {
    public DefaultUsersService(UsersRepository usersRepository, UsersConverter usersConverter) {
        this.usersRepository = usersRepository;
        this.usersConverter = usersConverter;
    }

    private void validateUserDto(UsersDto usersDto) throws ValidationException {
        if (isNull(usersDto)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(usersDto.getLogin()) || usersDto.getLogin().isEmpty()) {
            throw new ValidationException("Login is empty");
        }
    } //Валидация на отсутствие пустых строк в логине

    private final UsersRepository usersRepository;
    private final UsersConverter usersConverter;

    @Override
    public UsersDto saveUser(UsersDto usersDto) throws ValidationException{
        validateUserDto(usersDto); //проверяем валидность
        Users saveUser = usersRepository.save(usersConverter.fromUserDtoToUser(usersDto)); //сохраняем в репозиторий СКОНВЕРТИРОВАННЫЙ обьект
        return usersConverter.fromUserToUserDto(saveUser);
    } //сохранение пользователя
    @Override
    public void deleteUser(Integer userId){
        usersRepository.deleteById(userId);
    }//удаление пользователя
    @Override
    public UsersDto findByLogin(String login){
        Users users = usersRepository.findByLogin(login);
        if(users !=null){
            return usersConverter.fromUserToUserDto(users);
        }
        return null;
    }//поиск по логину

    @Override
    public List<UsersDto> findAll() {
        return usersRepository.findAll()
                .stream()
                .map(usersConverter::fromUserToUserDto)
                .collect(Collectors.toList());

    }//вывод всех пользователей

}