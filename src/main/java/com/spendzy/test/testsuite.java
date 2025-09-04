// TestSuiteUserSpecificFixed.java
package com.spendzy.test;

import com.spendzy.model.*;
import com.spendzy.service.*;

import java.util.Date;
import java.util.List;

public class testsuite {
    public static void main(String[] args) {

        UserService userService = new UserService();
        ExpenseCategoryService expCatService = new ExpenseCategoryService();
        IncomeCategoryService incCatService = new IncomeCategoryService();
        ExpenseService expenseService = new ExpenseService();
        IncomeService incomeService = new IncomeService();
        BudgetService budgetService = new BudgetService();

        System.out.println("🧪 Starting User-Specific Test Suite...\n");

        // Unique user info per run
        long timestamp = System.currentTimeMillis();
        User user = new User();
        user.setUsername("Lish_" + timestamp);
        user.setPassword("secure123");
        user.setEmail("lish_" + timestamp + "@example.com");
        userService.addUser(user);
        System.out.println("✅ User added.");

        // Fetch user to get userId
        User fetchedUser = userService.getAllUsers()
                .stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst()
                .orElse(null);

        if (fetchedUser == null) {
            System.err.println("❌ User fetch failed.");
            return;
        }
        System.out.println("👤 User fetched: " + fetchedUser.getUsername() + " (ID: " + fetchedUser.getUserId() + ")\n");

        // Add Expense Category
        ExpenseCategory foodCat = new ExpenseCategory();
        foodCat.setName("Food");
        foodCat.setUserId(fetchedUser.getUserId());
        expCatService.addExpenseCategory(foodCat);
        System.out.println("✅ Expense category added: " + foodCat.getName());

        // Add Income Category
        IncomeCategory scholarshipCat = new IncomeCategory();
        scholarshipCat.setName("Scholarship");
        scholarshipCat.setUserId(fetchedUser.getUserId());
        incCatService.addCategory(scholarshipCat);
        System.out.println("✅ Income category added: " + scholarshipCat.getName());

        // Add Expenses
        Expense e1 = new Expense();
        e1.setUserId(fetchedUser.getUserId());
        e1.setCategoryId(foodCat.getExpenseCategoryId());
        e1.setAmount(200);
        e1.setDescription("Snacks from canteen");
        e1.setDate(new Date());
        expenseService.addExpense(e1);

        Expense e2 = new Expense();
        e2.setUserId(fetchedUser.getUserId());
        e2.setCategoryId(foodCat.getExpenseCategoryId());
        e2.setAmount(250);
        e2.setDescription("Lunch");
        e2.setDate(new Date());
        expenseService.addExpense(e2);
        System.out.println("✅ Expenses added.\n");

        // Add Income
        Income inc1 = new Income();
        inc1.setUserId(fetchedUser.getUserId());
        inc1.setCategoryId(scholarshipCat.getIncomeCategoryId());
        inc1.setAmount(5000);
        inc1.setIncomeName("Scholarship");
        inc1.setDate(new Date());
        incomeService.addIncome(inc1);
        System.out.println("✅ Income added.\n");

        // Fetch all Expenses by User
        List<Expense> userExpenses = expenseService.getExpensesByUserId(fetchedUser.getUserId());
        System.out.println("💸 User Expenses:");
        userExpenses.forEach(exp -> System.out.println(exp.getDescription() + " - " + exp.getAmount()));

        // Fetch all Incomes by User
        List<Income> userIncomes = incomeService.getIncomesByUserId(fetchedUser.getUserId());
        System.out.println("\n💰 User Incomes:");
        userIncomes.forEach(inc -> System.out.println(inc.getIncomeName() + " - " + inc.getAmount()));

        // Fetch all Expense Categories by User
        List<ExpenseCategory> userExpCats = expCatService.getExpenseCategoriesByUser(fetchedUser.getUserId());
        System.out.println("\n📂 Expense Categories:");
        userExpCats.forEach(cat -> System.out.println(cat.getName()));

        // Fetch all Income Categories by User
        List<IncomeCategory> userIncCats = incCatService.getAllCategories(fetchedUser.getUserId());
        System.out.println("\n📂 Income Categories:");
        userIncCats.forEach(cat -> System.out.println(cat.getName()));

        // Cleanup
        userIncomes.forEach(inc -> incomeService.deleteIncome(inc.getIncomeId()));
        userExpenses.forEach(exp -> expenseService.deleteExpense(exp.getExpenseId()));
        userExpCats.forEach(cat -> expCatService.deleteExpenseCategory(cat.getExpenseCategoryId()));
        userIncCats.forEach(cat -> incCatService.deleteCategory(cat.getIncomeCategoryId()));
        userService.deleteUser(fetchedUser.getUserId());

        System.out.println("\n🧹 Cleanup complete. Test suite finished!");
    }
}
