import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

/**
 * @author Alexandru Mihai
 */
public class LazyBartender {

    private int minimumDrinksRequired;

    private DrinkCombination minimumDrinkCombination;

    private Set<Integer> customers;

    private Map<Integer, Set<Integer>> drinkCustomersMap;

    public LazyBartender(Map<Integer, List<Integer>> preferences) {
        if(isNull(preferences) || preferences.isEmpty()){
            throw new IllegalArgumentException();
        }
        drinkCustomersMap = new HashMap<>();
        preferences.forEach((customerId, drinkPreferences) -> {
            for (Integer drink : drinkPreferences) {
                drinkCustomersMap.compute(drink, (d, cs) -> computeCustomers(cs, customerId));
            }
        });

        customers = preferences.keySet();
        searchMinimumDrinksRequired();
    }

    public void printSolution(){
        System.out.println(minimumDrinkCombination.drinks);
    }

    private Set<Integer> computeCustomers(Set<Integer> customers, Integer customerId) {
        if (isNull(customers)) {
            customers = new HashSet<>();
        }
        customers.add(customerId);
        return customers;
    }

    private void searchMinimumDrinksRequired() {
        //suppose worst case scenario
        this.minimumDrinksRequired = drinkCustomersMap.size();
        //do recursive search
        searchMinimumDrinksRequired(drinkCustomersMap.keySet().toArray(new Integer[0]), -1, new DrinkCombination());
    }

    private void searchMinimumDrinksRequired(Integer[] drinks, int drinkIdx, DrinkCombination combination) {
        if (combination.drinks.size() >= minimumDrinksRequired) {
            return;
        }

        if (combination.satisfiesAllCustomers()) {
            minimumDrinkCombination = combination.copy();
            minimumDrinksRequired = combination.drinks.size();
            return;
        }

        for (int i = drinkIdx + 1; i < drinks.length; i++) {
            combination.addDrink(drinks[i]);
            searchMinimumDrinksRequired(drinks, i, combination);
            combination.removeLast();
        }
    }

    final class DrinkCombination {

        Deque<Integer> drinks;

        DrinkCombination() {
            this.drinks = new LinkedList<>();
        }

        void addDrink(Integer drink) {
            this.drinks.push(drink);
        }

        void removeLast(){
            drinks.pop();
        }

        DrinkCombination copy(){
            DrinkCombination drinkCombination = new DrinkCombination();
            drinkCombination.drinks = new LinkedList<>(this.drinks);
            return drinkCombination;
        }

        boolean satisfiesAllCustomers(){
            if (drinks.isEmpty()) {
               return false;
            }
            return drinks.stream()
                    .map(drinkCustomersMap::get)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet())
                    .equals(customers);
        }
    }

    // -------------------- TEST --------------------------------------

    public static void main(String[] args) {
        new LazyBartender(testA()).printSolution(); //[5, 3]
        new LazyBartender(testB()).printSolution(); //[12, 8, 3, 1, 0]
        new LazyBartender(testC()).printSolution(); //[5, 1]
    }

    private static Map<Integer, List<Integer>> testA() {
        Map<Integer, List<Integer>> preferences = new HashMap<>();
        preferences.put(0, asList(0, 1, 3, 6));
        preferences.put(1, asList(1, 3, 4, 7));
        preferences.put(2, asList(4, 7, 5));
        preferences.put(3, asList(3));
        preferences.put(4, asList(1, 5));
        preferences.put(5, asList(1, 5, 8));
        return preferences;
    }


    private static Map<Integer, List<Integer>> testB() {
        Map<Integer, List<Integer>> preferences = new HashMap<>();
        preferences.put(0, asList(0, 1, 4, 5, 8, 9));
        preferences.put(1, asList(0, 1, 3, 5, 6));
        preferences.put(2, asList(0, 2, 7, 9, 10, 11));
        preferences.put(3, asList(0, 2, 3));
        preferences.put(4, asList(1, 6, 5, 9));
        preferences.put(5, asList(2, 8));
        preferences.put(6, asList(3));
        preferences.put(7, asList(8, 1, 4, 5));
        preferences.put(8, asList(0, 1, 8));
        preferences.put(9, asList(0, 2, 4));
        preferences.put(10, asList(1, 2, 3, 4, 5, 6));
        preferences.put(11, asList(1, 6, 5));
        preferences.put(12, asList(2, 8));
        preferences.put(13, asList(3, 9));
        preferences.put(14, asList(12));
        preferences.put(15, asList(8));
        return preferences;
    }

    private static Map<Integer, List<Integer>> testC() {
        Map<Integer, List<Integer>> preferences = new HashMap<>();
        preferences.put(0, asList(0, 1, 3, 6));
        preferences.put(1, asList(1, 4, 7));
        preferences.put(2, asList(2, 4, 7, 5));
        preferences.put(3, asList(3, 2, 5));
        preferences.put(4, asList(5, 8));
        return preferences;
    }

}

