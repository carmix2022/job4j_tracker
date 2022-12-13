package ru.job4j.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankService {
    private final Map<User, List<Account>> users = new HashMap<>();

    public void addUser(User user) {
            users.putIfAbsent(user, new ArrayList<Account>());
    }

    public boolean deleteUser(String passport) {
        User user = null;
        for (User u : users.keySet()) {
            if (passport.equals(u.getPassport())) {
                user = u;
                break;
            }
        }
        return users.remove(user, users.get(user));
    }

    public void addAccount(String passport, Account account) {
        List<Account> list = users.get(findByPassport(passport));
        if (list != null && !list.contains(account)) {
            list.add(account);
        }
    }

    public User findByPassport(String passport) {
        User user = null;
        for (User u : users.keySet()) {
            if (passport.equals(u.getPassport())) {
                user = u;
                break;
            }
        }
        return user;
    }

    public Account findByRequisite(String passport, String requisite) {
        List<Account> list = users.get(findByPassport(passport));
        Account account = null;
        if (list == null) {
            return null;
        }
        for (Account a : list) {
            if (requisite.equals(a.getRequisite())) {
                account = a;
                break;
            }
        }
        return account;
    }

    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String destRequisite, double amount) {
        Account account1 = findByRequisite(srcPassport, srcRequisite);
        Account account2 = findByRequisite(destPassport, destRequisite);
        if (account1 == null || account2 == null || account1.getBalance() < amount) {
            return false;
        }
        account2.setBalance(account2.getBalance() + amount);
        return true;
    }

    public List<Account> getAccounts(User user) {
        return users.get(user);
    }
}