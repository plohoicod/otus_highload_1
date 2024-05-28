package ru.otus.highload.homework.first.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.highload.homework.first.dto.AuthorizeDto;
import ru.otus.highload.homework.first.dto.RegisterDto;
import ru.otus.highload.homework.first.dto.UserDto;
import ru.otus.highload.homework.first.enums.Gender;

import java.sql.*;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;

    private final PasswordEncoder pwdEncoder;

    private final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?;";

    private final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login like ?;";

    private final String REGISTER_USER =
            "INSERT INTO users (login, password, name, surname, birthday, gender, interests, city) VALUES (?,?, ?, ?, ?, ?, ?, ?);";


    public AuthorizeDto getUserByLogin(String login) {
        AuthorizeDto authorizeDto = null;

        try (Connection connection =
                     DriverManager.getConnection(url, user, password);) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                authorizeDto = new AuthorizeDto(
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return authorizeDto;
    }

    public UserDto getUserById(Long id) {
        UserDto userDto = null;
        try (Connection connection =
                     DriverManager.getConnection(url, user, password);) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(resultSet.getTimestamp("birthday").getTime());
                userDto = new UserDto(
                 resultSet.getString("name"),
                        resultSet.getString("surname"),
                        calendar,
                        Gender.valueOf(resultSet.getString("gender")),
                        resultSet.getString("interests"),
                        resultSet.getString("city")
                );
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return userDto;
    }

    public Integer registerUser(RegisterDto registerDto) {
        try (Connection connection =
                     DriverManager.getConnection(url, user, password);) {
             String password = pwdEncoder.encode(registerDto.password());
             PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_USER);
             preparedStatement.setString(1, registerDto.login());
             preparedStatement.setString(2, password);
             preparedStatement.setString(3, registerDto.name());
             preparedStatement.setString(4, registerDto.surname());
             preparedStatement.setTimestamp(5, new Timestamp(registerDto.birthday().getTimeInMillis()));
             preparedStatement.setString(6, registerDto.gender().toString());
             preparedStatement.setString(7, registerDto.interests());
             preparedStatement.setString(8, registerDto.city());
             return preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
