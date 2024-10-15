package com.example.my_project;

import java.sql.*;

public class DB {
    private final String HOST = "localhost"; // Адрес хоста базы данных
    private final String PORT = "3306"; // Порт, на котором работает база данных
    private final String DB_NAME = "project-java";
    private final String LOGIN = "root"; // Логин для доступа к базе данных
    private final String PASS = "root"; // Пароль для доступа к базе данных
    private Connection dbConn = null; // Переменная для хранения подключения к базе данных

    // Метод для получения подключения к базе данных
    private Connection getDBConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME; // Это специальная технология, которая служит для подсоединения к базе данных
        Class.forName("com.mysql.cj.jdbc.Driver"); // Загружаем драйвер MySQL

        // Устанавливаем подключение к базе данных с помощью DriverManager
        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn; // Возвращаем объект подключения
    }

    // Метод для проверки подключения к базе данных
    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDBConnection(); // Получаем подключение к базе данных
        System.out.println(dbConn.isValid(1000)); // Проверяем, является ли подключение действительным в течение 1 секунды
    }

    // Проверка на логин
    public boolean isExistsUser(String login){
        String sql = "SELECT `id` FROM `users` WHERE `login` = ?";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1,login);
            ResultSet res = prSt.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Занос данных в таблицу "users"
    public void regUser(String login, String email, String pass){
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES(?, ?, ?)";

        PreparedStatement prSt = null;
        try {
            prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1,login);
            prSt.setString(2,email);
            prSt.setString(3,pass);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    // Проверку на автоизацию пользователя
    public boolean authUser(String login, String pass) {
        String sql = "SELECT `id` FROM `users` WHERE `login` = ? AND `password` = ?";
        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1,login);
            prSt.setString(2,pass);
            ResultSet res = prSt.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getArticles(){
        String sql = "SELECT `title`, `intro` FROM `articles`";
        try {
            Statement statement = getDBConnection().createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void addArticle(String title, String intro, String full_text) {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`) VALUES(?, ?, ?)";

        try {
            PreparedStatement prSt = getDBConnection().prepareStatement(sql);
            prSt.setString(1,title);
            prSt.setString(2,intro);
            prSt.setString(3,full_text);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}