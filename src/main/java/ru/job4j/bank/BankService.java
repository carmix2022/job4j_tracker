package ru.job4j.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс описывает работу банковского сервиса,
 * оперирующего пользователями и их счетами в формате CRUD
 * @author ILYA
 * @version 1.0
 */
public class    BankService {
    /**
     * Хранение пользователя банка и его счетов осуществляется в коллекции типа Map
     */
    private final Map<User, List<Account>> users = new HashMap<>();

    /**
     * Метод принимает на вход пользователя банка и добавляет в базу, если его еще там нет
     * @param user пользователь банка
     */
    public void addUser(User user) {
            users.putIfAbsent(user, new ArrayList<Account>());
    }

    /**
     * Метод принимает на вход номер паспорта, пытается найти по нему в базе пользователя
     * и удалить его
     * @param passport номер паспорта пользователя
     * @return {@code true} если пользователь найден и удален
     */
    public boolean deleteUser(String passport) {
        return users.remove(new User(passport, "")) != null;
    }

    /**
     * Метод принимает на вход номер паспорта пользователя и новый счёт,
     * который добавляется в список счетов данного пользователя
     * @param passport номер паспорта пользователя, к которому добавляем счет
     * @param account добавляемый счет
     */
    public void addAccount(String passport, Account account) {
        User user = findByPassport(passport);
        if (user != null) {
            List<Account> list = users.get(user);
            if (!list.contains(account)) {
                list.add(account);
            }
        }
    }

    /**
     * Метод принимает на вход номер паспорта и ищет по нему в базе пользователя
     * @param passport номер паспорта пользователя, которого ищем в базе
     * @return пользователь банка или {@code null} если пользователь не найден
     */
    public User findByPassport(String passport) {
        return users.keySet()
                .stream()
                .filter(u -> u.getPassport().equals(passport))
                .findFirst()
                .orElse(null);
    }

    /**
     * Метод принимает на вход номер паспорта пользователя и реквизиты его счета
     * и ищет данный счет у данного пользователя в базе
     * @param passport номер паспорта пользователя
     * @param requisite реквизиты искомого счета
     * @return конкретный счет пользователя или {@code null} если счет не найден
     */
    public Account findByRequisite(String passport, String requisite) {
        User user = findByPassport(passport);
        return user != null ? users.get(user)
                .stream().
                filter(a -> a.getRequisite().equals(requisite))
                .findFirst()
                .orElse(null)
                : null;
    }

    /**
     * Метод перечисляет средства с одного банковского счета на другой
     * @param srcPassport номер паспорта пользователя-отправителя средств
     * @param srcRequisite номер реквизитов счета, с которого списываются средства
     * @param destPassport номер паспорта пользователя-получателя средств
     * @param destRequisite номер реквизитов счета, на который начисляются средства
     * @param amount размер передаваемых средств
     * @return {@code true} если средства списаны и переведены успешно или
     * {@code false} если счет(а) не найдены или на счете-отправителе недостаточно средств
     */
    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String destRequisite, double amount) {
        Account srcAccount = findByRequisite(srcPassport, srcRequisite);
        Account destAccount = findByRequisite(destPassport, destRequisite);
        if (srcAccount == null || destAccount == null || srcAccount.getBalance() < amount) {
            return false;
        }
        srcAccount.setBalance(srcAccount.getBalance() - amount);
        destAccount.setBalance(destAccount.getBalance() + amount);
        return true;
    }

    /**
     * Метод принимает на вход пользователя банка и выдает его список счетов
     * @param user пользователь банка
     * @return список {@code List} счетов пользователя
     */
    public List<Account> getAccounts(User user) {
        return users.get(user);
    }
}
