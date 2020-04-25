package ru.job4j.bank;

import java.util.*;

public class BankService {
	private Map<User, List<Account>> users = new HashMap<>();

	public void addUser(User user) {
		users.putIfAbsent(user, new ArrayList<Account>());
	}

	public void addAccount(String passport, Account account) {
		User user = findByPassport(passport);
		List<Account> accs = users.get(user);
		if (!accs.contains(account)) {
			users.get(user).add(account);
		}
	}

	public User findByPassport(String passport) {
		for (User user : users.keySet()) {
			if (user.getPassport().equals(passport))
				return user;
		}
		return null;
	}

	public Account findByRequisite(String passport, String requisite) {
		User user = findByPassport(passport);
		if (user == null) {
			return null;
		}
		int index = users.get(user).indexOf(new Account(requisite, 0D));
		return users.get(user).get(index);
	}

	public boolean transferMoney(String srcPassport, String srcRequisite,
	                             String destPassport, String destRequisite, double amount) {
		boolean res = false;
		Account srcAcc = findByRequisite(srcPassport, srcRequisite);
		Account destAcc = findByRequisite(destPassport, destRequisite);
		if (srcAcc != null
				&& destAcc != null
				&& srcAcc.getBalance() >= amount) {
			srcAcc.setBalance(srcAcc.getBalance() - amount);
			destAcc.setBalance(destAcc.getBalance() + amount);
			res = true;
		}
		return res;
	}
}
